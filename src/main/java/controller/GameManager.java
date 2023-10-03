package controller;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the Game Manager
 * Manages game state, and the player actions involved during the game play
 *
 * @author Rishi Ravikumar
 */
public class GameManager {
    private GamePhase d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;

    /**
     * Constructor that initializes the GameManager with a map.
     *
     * @param p_map The map to be used for the game.
     */
    public GameManager(Map p_map) {
        this.d_gamePhase = GamePhase.Map_Init;
        this.d_playerList = new ArrayList<>();
        this.d_map = p_map;
    }

    /**
     * Default constructor for GameManager.
     */

    public GameManager() {
        this.d_gamePhase = GamePhase.Map_Init;
        this.d_playerList = new ArrayList<>();
    }

    /**
     * Used in the Game_Startup game phase to assign countries to the players in the game
     *
     * @author Nimisha Jadav
     */
    public void assignCountries() {
        if(d_playerList.size() < 2){
            System.out.println("Too few players added. Minimum players required is 2");
            return;
        }
        int l_playerIndex = 0;
        for (Country l_country : d_map.getD_countryMapGraph().vertexSet()) {
            Player l_player = d_playerList.get(l_playerIndex);
            l_player.addCountry(l_country);
            l_country.setD_owner(l_player);
            l_playerIndex = (++l_playerIndex) % d_playerList.size();
        }
        System.out.println("Assigned countries to the players");
        setD_gamePhase(GamePhase.IssueOrder);
        System.out.println("Game has Started!");
        assignReinforcements();

        d_currentPlayerTurn = 0;
        System.out.println("Player " + d_playerList.get(d_currentPlayerTurn).getD_playerName() + "'s turn");
        System.out.println("Available Reinforcement armies: " + d_playerList.get(d_currentPlayerTurn).getD_numArmies());
    }

    public void updatePlayerTurn() {
        d_currentPlayerTurn = (++d_currentPlayerTurn) % d_playerList.size();
    }

    /**
     * Retrieves the current map.
     *
     * @return The current map.
     */
    public Map getD_map() {
        return this.d_map;
    }

    public void setD_map(Map d_map) {
        this.d_map = d_map;
    }


    /**
     * check if enough armies are
     * left to be assigned for the player
     *
     * @return true if all players have assigned all their armies, false otherwise.
     * @author Abhigyan
     */
    public boolean check_armies() {
        for (Player l_player : d_playerList) {
            if (l_player.getD_numArmies() != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * Assigns to each player the number of reinforcement armies according to the Warzone rules.
     */
    public void assignReinforcements() {
        int l_numArmies = (int) Math.min((double) (d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
        for (Player l_player : d_playerList) {
            l_player.setD_numArmies(l_player.getD_numArmies() + l_numArmies);
            for (Continent l_c : l_player.getD_continentList()) {
                l_player.setD_numArmies(l_player.getD_numArmies() + l_c.getD_continentValue());
            }
        }
        System.out.println("Reinforcement armies have been assigned to each player");
    }

    /**
     * Adds a new {@link models.Player} to the game
     *
     * @param p_playerName name of the player to be added
     * @author Nimisha Jadav
     */
    public void addPlayer(String p_playerName) {
        if (d_playerList.size() < 6) {
            for (Player l_p : d_playerList) {
                if (Objects.equals(l_p.getD_playerName(), p_playerName)) {
                    System.out.println("Player " + p_playerName + " already exists");
                    return;
                }
            }
            Player l_player = new Player(p_playerName);
            d_playerList.add(l_player);
            System.out.println("Added " + p_playerName + " to the game!");
        } else {
            System.out.println("You have reached the limit to add players");
        }

    }

    /**
     * Removes a {@link models.Player} from the game
     *
     * @param p_playerName name of the player to be removed
     * @author Nimisha Jadav
     */
    public void removePlayer(String p_playerName) {
        for (Player l_p : d_playerList) {
            if (Objects.equals(l_p.getD_playerName(), p_playerName)) {
                System.out.println("Player " + p_playerName + " removed from the game");
                d_playerList.remove(l_p);
                return;
            }
        }
        System.out.println("Player " + p_playerName + " does not exist");
    }

    /**
     * Adds an order to the current player’s list of orders
     *
     * @param p_countryID The country to which the order pertains.
     * @param p_num         The number associated with the order.
     * @author Rishi Ravikumar
     * @author Abhigyan
     */
    public void issueOrder(Country p_countryID, int p_num) {
        Player l_currentPlayer = d_playerList.get(d_currentPlayerTurn);
        if (l_currentPlayer != null) {
            if(l_currentPlayer.getD_numArmies() < p_num) {
                System.out.println("Cannot issue order");
                return;
            }

            // Create an order using the provided parameters (p_countryID and num)
            Order order = new Order(p_countryID, p_num);
            l_currentPlayer.setD_currentOrder(order);
            // Call the issue_order() method of the current player to add the order
            l_currentPlayer.issueOrder();
            System.out.println("Issued Order");
        } else {
            // Handle the case where there is no current player or it's not their turn
            System.out.println("No current player or it's not their turn to issue orders.");
        }
    }

    /**
     * Executes all the orders from all the players for the current turn, updating the game state
     *
     * @author Rishi Ravikumar
     * @author Nimisha Jadav
     */
    public void executeOrder() {
        Player l_currentPlayer = d_playerList.get(d_currentPlayerTurn);

        for (int i = 0; i <= d_playerList.size(); i++) {
            Order l_order = l_currentPlayer.nextOrder();
            l_order.execute();
        }
    }

    /**
     * Displays the {@link models.Map}, and the current game state
     * It shows all continents, countries, armies on each country, ownership, and connectivity.
     *
     * @author Rishi Ravikumar
     * @author Yusuke Ishii
     */


    public void showMap() {

        Map l_map = this.getD_map();
        System.out.println("---- MAP DISPLAY ----");

        System.out.println("List of Continents:");
        for (Continent l_continent : l_map.getD_continentMapGraph().vertexSet()) {
            System.out.println("Continent ID: " + l_continent.getD_continentID() + ", Name: " + l_continent.getD_continentName() + ", Value: " + l_continent.getD_continentValue());
        }

        System.out.println("\nList of Countries, their Owners, Armies, and Neighbours:");
        for (Country l_country : l_map.getD_countryMapGraph().vertexSet()) {
            Player l_owner = l_country.getD_owner();
            String l_ownerName = (l_owner != null) ? l_owner.getD_playerName() : "Unowned";
            System.out.println("Country ID: " + l_country.getD_countryID() + ", Name: " + l_country.getD_countryName() + ", Owner: " + l_ownerName + ", Armies: " + l_country.getD_numArmies());
            System.out.print("Neighbours: ");
            for (int l_neighbourID : l_country.getD_neighbourCountryIDList()) {
                Country l_neighbour = l_map.getD_countryByID(l_neighbourID);
                System.out.print(l_neighbour.getD_countryID() + ", ");
            }
            System.out.println("\n");
        }
    }


    /**
     * Gets the list of players in the current active game
     *
     * @return List of players.
     */
    public List<Player> getD_playerList() {
        return d_playerList;
    }

    /**
     * Sets the list of players in the current active game
     *
     * @param d_playerList List of players.
     */
    public void setD_playerList(List<Player> d_playerList) {
        this.d_playerList = d_playerList;
    }

    /**
     * Gets the game's current turn it is to perform actions
     *
     * @return The current player index.
     */
    public int getD_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * Gets the game's current phase.
     * <p>
     * Possible values defined by {@link GamePhase}
     *
     * @return The current game phase.
     */
    public GamePhase getD_gamePhase() {
        return d_gamePhase;
    }

    /**
     * Gets the game's current phase.
     * <p>
     * Possible values defined by {@link GamePhase}
     */
    public void setD_gamePhase(GamePhase d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }


}