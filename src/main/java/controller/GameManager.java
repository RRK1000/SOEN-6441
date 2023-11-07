package controller;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
import gamelog.LogManager;
import models.*;
import phases.InitMapPhase;
import phases.Phase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    private final List<Integer> d_skipTurnList;
    private final LogEntryBuffer d_logBuffer;
    private final LogFileWriter d_logWriter;
    private Phase d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;


    /**
     * Default constructor for GameManager.
     */
    public GameManager() {
        this.d_gamePhase = new InitMapPhase();
        this.d_playerList = new ArrayList<>();
        this.d_skipTurnList = new ArrayList<>();

        Path logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        this.d_logBuffer = new LogEntryBuffer();
        this.d_logWriter = new LogFileWriter(logPath);
        this.d_logBuffer.addObserver(d_logWriter);

    }

    /**
     * Sets the action in the log buffer
     * @param action The action string by player
     */
    public void logAction(String action) {
        d_logBuffer.setActionInfo(action);
    }

    /**
     * Method to update the turn of the player.
     */
    public void updatePlayerTurn() {
        String l_currentPlayerName = this.getD_playerList().get(this.getD_currentPlayerTurn()).getD_playerName();
        System.out.println("Player " + l_currentPlayerName + " turn over. ");
        System.out.println();

        if (d_skipTurnList.toArray().length == d_playerList.toArray().length) {
            this.updateNeutralCountriesOnRoundEnd();
            this.updatePlayerDiplomacyOnRoundEnd();

            this.getD_gamePhase().executeOrder(this);
            this.assignReinforcements();
            d_skipTurnList.clear();
            this.d_currentPlayerTurn = 0;
        } else {
            do {
                d_currentPlayerTurn = (d_currentPlayerTurn + 1) % d_playerList.size();
            } while (d_skipTurnList.contains(d_currentPlayerTurn));
        }

        Player l_currentPlayer = this.getD_playerList().get(this.getD_currentPlayerTurn());
        LogManager.logAction("Player turn updated to " + l_currentPlayer.getD_playerName());
        System.out.println("Player " + l_currentPlayer.getD_playerName() + "'s turn ");
        System.out.println("available reinforcement armies: " + l_currentPlayer.getD_numArmies());
        if (!l_currentPlayer.getD_playerCardList().isEmpty()) {
            System.out.println("Cards available for Player " + l_currentPlayer.getD_playerName() + ": " + l_currentPlayer.getD_playerCardList());
        } else {
            System.out.println("No cards are available to Player " + l_currentPlayer.getD_playerName());
        }
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
        LogManager.logAction("Reinforcement armies have been assigned to each player");

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
            LogManager.logAction("Added " + p_playerName + " to the game!");


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
                LogManager.logAction("Player " + p_playerName + " removed from the game");
                return;
            }
        }
        System.out.println("Player " + p_playerName + " does not exist");
    }

    /**
     * Finds a {@link models.Player} using the playerName/PlayerID
     *
     * @param p_playerName PLayer if exists, else null
     * @return {@link models.Player} if found, else null
     */
    public Player findPlayerByName(String p_playerName) {
        for (Player l_p : d_playerList) {
            if (l_p.getD_playerName().equals(p_playerName)) {
                return l_p;
            }
        }
        return null;
    }



    /**
     * Checks if any player does not own any countries, ie, has lost, and removes them from player list.
     */
    public void updatePlayerList() {
        List<Player> l_playerlist = d_playerList;
        for (Player l_player : l_playerlist) {
            if (l_player.getD_countryList().isEmpty()) {
                d_playerList.remove(l_player);
            }
        }
    }

    /**
     * Updates neutral countries from the previous round
     */
    private void updateNeutralCountriesOnRoundEnd() {
        for (Country l_country : d_map.getD_countryMapGraph().vertexSet()) {
            if (l_country.isD_isNeutral()) {
                l_country.setD_isNeutral(false);
            }
        }
    }

    /**
     * Clears the negotiation between players after end of round
     */
    private void updatePlayerDiplomacyOnRoundEnd() {
        for (Player l_player : d_playerList) {
            l_player.clearPlayerNegotiation();
        }
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
                    l_country.getD_countryID(), l_country.getD_continentID(), l_neighbors, l_country.isD_isNeutral()?"neutral":l_owner.getD_playerName(), l_country.getD_numArmies());
            LogManager.logAction("Displayed the current game map and state.");

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
     * @param p_playerList List of players.
     */
    public void setD_playerList(List<Player> p_playerList) {
        this.d_playerList = p_playerList;
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
     * Sets the current player's turn number
     * @param d_currentPlayerTurn current player turn
     */
    public void setD_currentPlayerTurn(int d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
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

    /**
     * Adds player to the list of players skipping a round for the turn
     *
     * @param p_pIndex player index on d_playerList
     */
    protected void addPlayerToSkipList(Integer p_pIndex) {
        d_skipTurnList.add(p_pIndex);
    }
}