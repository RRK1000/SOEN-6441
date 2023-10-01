package controller;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the Game Manager
 * Manages game state, and the player actions involved during the game play
 *
 * @author Rishi Ravikumar
 */
public class GameManager {
    private static Player d_player;
    public GamePhase d_gamePhase;
    private List<Player> d_playerList;
    private Player d_currentPlayerTurn;
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
     * Constructor that initializes the GameManager with a player.
     *
     * @param p_player The player to be managed.
     */

    public GameManager(Player p_player) {
        d_player = p_player;
    }

    /**
     * Used in the Game_Startup game phase to assign countries to the players in the game
     *
     * @author Nimisha Jadav
     */
    public void assignCountries() {
        Random l_random = new Random();

        for (Country l_country : d_map.getD_countryMapGraph().vertexSet()) {
            int l_playerIndex = l_random.nextInt(d_playerList.size());
            Player l_player = d_playerList.get(l_playerIndex);
            l_player.addCountry(l_country);
            l_country.setD_owner(l_player);
        }
        System.out.println("Assigned countries to the players");
        assignReinforcements();

        d_currentPlayerTurn = d_playerList.get(0);
        System.out.println("Player " + d_currentPlayerTurn.getD_playerName() + "'s turn");
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
     *
     */
    public void assignReinforcements() {
        int l_numArmies = (int) Math.min((double) (d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
        for (Player l_player : d_playerList) {
            l_player.setD_numArmies(l_player.getD_numArmies()+l_numArmies);
            for (Continent l_c: l_player.getD_continentList()) {
                l_player.setD_numArmies(l_player.getD_numArmies()+l_c.getD_continentValue());
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
     * @param num         The number associated with the order.
     * @author Rishi Ravikumar
     * @author Abhigyan
     */
    public void issueOrder(Country p_countryID, int num) {
        // implementation here
        // must call the d_currentPlayerTurn.issue_order()

        // Check if it's the current player's turn
        if (d_currentPlayerTurn != null) {
            // Create an order using the provided parameters (p_countryID and num)
            Order order = new Order(p_countryID, num);
            d_currentPlayerTurn.setD_currentOrder(order);
            // Call the issue_order() method of the current player to add the order
            d_currentPlayerTurn.issueOrder();
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
        // implementation here
        // iterates through each player and does the following for each order:
        //      fetch the next order object using d_currentPlayerTurn.next_order() and then run Order->execute()
        for (int i = 0; i <= d_playerList.size(); i++) {
            Order l_order = d_currentPlayerTurn.nextOrder();
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
                System.out.print(l_neighbour.getD_countryName() + ", ");
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
     * @return The current player.
     */
    public Player getD_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * Sets the game's current turn
     *
     * @param d_currentPlayerTurn The current player.
     */
    public void setD_currentPlayerTurn(Player d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
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

    /**
     * Represents the different phases of the game.
     */
    public enum GamePhase {
        Map_Init,
        Game_Startup,
        AssignReinforcements,
        IssueOrder,
        ExecuteOrder
    }
}