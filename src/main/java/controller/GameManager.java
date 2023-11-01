package controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
import models.Continent;
import models.Country;
import models.Map;
import models.Order;
import models.Player;
import phases.InitMapPhase;
import phases.Phase;
/**
 * Represents the Game Manager
 * Manages game state, and the player actions involved during the game play
 *
 * @author Rishi Ravikumar
 * @author Abhigyan Singh
 * @author Nimisha Jadav
 * @author Yusuke
 */
public class GameManager {
    /**
     * GamePhase instance
     */
    private Phase d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;


    private LogEntryBuffer d_logBuffer;
    private LogFileWriter d_logWriter;

    /**
     * Default constructor for GameManager.
     */
    public GameManager() {
        this.d_gamePhase = new InitMapPhase();
        this.d_playerList = new ArrayList<>();
        
        Path logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        this.d_logBuffer = new LogEntryBuffer();
        this.d_logWriter = new LogFileWriter(logPath);
        this.d_logBuffer.addObserver(d_logWriter);

    }
    
    private void logAction(String action) {
        d_logBuffer.setActionInfo(action);
    }

    /**
     * Used in the Game_Startup game phase to assign countries to the players in the game
     */
    public void assignCountries() {
        //Checks the condition where the armies cannot be assigned if the number of players is less than 2.
        if (d_playerList.size() < 2) {
            System.out.println("Too few players added. Minimum players required is 2");
            return;
        }

        //Assigning countries to the player
        int l_playerIndex = 0;
        for (Country l_country : d_map.getD_countryMapGraph().vertexSet()) {
            Player l_player = d_playerList.get(l_playerIndex);
            l_player.addCountry(l_country);
            l_country.setD_owner(l_player);
            l_playerIndex = ((l_playerIndex + 1) % d_playerList.size());
        }
        //Setting the game phase to Issue Order and assign armies to the players
        System.out.println("Assigned countries to the players");
        System.out.println("Game has Started!");
        assignReinforcements();

        //Displaying the current player's name and the armies
        d_currentPlayerTurn = 0;
        System.out.println("Player " + d_playerList.get(d_currentPlayerTurn).getD_playerName() + "'s turn");
        System.out.println("Available Reinforcement Armies: " + d_playerList.get(d_currentPlayerTurn).getD_numArmies());
        
        logAction("Assigned countries to the players. Game has Started!");

    }

    /**
     * Method to update the turn of the player.
     */
    public void updatePlayerTurn() {
        d_currentPlayerTurn = (d_currentPlayerTurn + 1) % d_playerList.size();
        logAction("Player turn updated to " + d_playerList.get(d_currentPlayerTurn).getD_playerName());

        
    }

    /**
     * Retrieves the current map.
     *
     * @return The current map.
     */
    public Map getD_map() {
        return this.d_map;
    }

    /**
     * Sets the current map.
     *
     * @param p_map Sets the current map.
     */
    public void setD_map(Map p_map) {
        this.d_map = p_map;
    }

    /**
     * Assigns to each player the number of reinforcement armies according to the Warzone rules.
     */
    public void assignReinforcements() {
        int l_numArmies = Math.max((d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
        for (Player l_player : d_playerList) {
            l_player.setD_numArmies(l_player.getD_numArmies() + l_numArmies);
            for (Continent l_c : l_player.getD_continentList()) {
                l_player.setD_numArmies(l_player.getD_numArmies() + l_c.getD_continentValue());
            }
        }
        System.out.println("Reinforcement armies have been assigned to each player\n");
        logAction("Reinforcement armies have been assigned to each player");

    }

    /**
     * Adds a new {@link models.Player} to the game
     *
     * @param p_playerName name of the player to be added
     */
    public void addPlayer(String p_playerName) {
        //Adding a new player only if the number of players is less than 6
        if (d_playerList.size() < 6) {
            for (Player l_p : d_playerList) {
                //Checks if the player already exist
                if (Objects.equals(l_p.getD_playerName(), p_playerName)) {
                    System.out.println("Player " + p_playerName + " already exists");
                    return;
                }
            }
            //Adding new player to the game
            Player l_player = new Player(p_playerName);
            d_playerList.add(l_player);
            System.out.println("Added " + p_playerName + " to the game!");
            System.out.println("Added " + p_playerName + " to the game!");
            logAction("Added " + p_playerName + " to the game!");

            

        } else {
            System.out.println("You have reached the limit to add players");
        }
    }

    /**
     * Removes a {@link models.Player} from the game
     *
     * @param p_playerName name of the player to be removed
     */
    public void removePlayer(String p_playerName) {
        for (Player l_p : d_playerList) {
            if (Objects.equals(l_p.getD_playerName(), p_playerName)) {
                System.out.println("Player " + p_playerName + " removed from the game");
                d_playerList.remove(l_p);
                logAction("Player " + p_playerName + " removed from the game");
                return;
            }
        }
        System.out.println("Player " + p_playerName + " does not exist");
    }


    /**
     * Executes all the orders from all the players for the current turn, updating the game state
     */
    public void executeOrder() {
        for (Player l_player : d_playerList) {
            Order l_order = l_player.nextOrder();
            l_order.execute();
        }
        System.out.println("Orders have been executed for this round.");
        logAction("Orders have been executed for this round.");

    }

    /**
     * Displays the {@link models.Map}, and the current game state
     * It shows all continents, countries, armies on each country, ownership, and connectivity.
     */
    public void showMap() {
    	
        System.out.printf("------------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-8s | %-8s | %-30s | %10s | %8s |%n", "Country", "Continent", "Neighbors", "Owner", "# of Armies");
        System.out.printf("------------------------------------------------------------------------------------------------%n");
        for (Country l_country : d_map.getD_countryMapGraph().vertexSet()) {
            Player l_owner = l_country.getD_owner();
            StringBuilder l_neighbors = new StringBuilder();
            for (int l_neighbourID : l_country.getD_neighbourCountryIDList()) {
                l_neighbors.append(l_neighbourID);
                l_neighbors.append(" ");
            }
            System.out.printf("| %-8s | %-8s | %30s | %10s | %8s |%n",
                    l_country.getD_countryID(), l_country.getD_continentID(), l_neighbors, l_owner.getD_playerName(), l_country.getD_numArmies());
            logAction("Displayed the current game map and state.");

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
     * Possible values defined by {@link phases}
     *
     * @return The current game phase.
     */
    public Phase getD_gamePhase() {
        return d_gamePhase;
    }

    /**
     * Gets the game's current phase.
     *
     * @param p_gamePhase Possible values defined by {@link phases}
     */
    public void setD_gamePhase(Phase p_gamePhase) {
        this.d_gamePhase = p_gamePhase;
    }
}