package controller;

import models.Player;

import java.util.List;

/**
 * Represents the GameManager
 * Manages game state, and the player actions involved during the game play
 *
 * @author Rishi Ravikumar
 */
public class GameManager {
    private List<Player> d_playerList;
    private Player d_currentPlayerTurn;
    private GamePhase d_gamePhase;

    /**
     * Used in the Game_Startup game phase to assign countries to the players in the game
     *
     * @author Rishi Ravikumar
     */
    public static void assignCountries() {
        // implementation here
        // must iterate through d_playerList and assign countries to each player according to the warzone rules
        // Must update the d_countryList data member of each Player
    }

    /**
     * Assigns to each player the number of reinforcement armies according to the Warzone rules
     *
     * @author Rishi Ravikumar
     */
    public void assignReinforcements() {
        // implementation here
        // must iterate through d_playerList and assign d_numArmies value to each player according to the warzone rules
        //      Must use the d_continentList from the Player object to assign the d_numArmies.
        //      Must use the d_continentValue from each continent conquered by the player
    }

    /**
     * Adds a new player to the game
     *
     * @param p_playerName name of the player to be added
     * @author Rishi Ravikumar
     */
    public void addPlayer(String p_playerName) {
        // implementation here
        // must add a new player object to GameManager->d_playerList
    }

    /**
     * Removes a player from the game
     *
     * @param p_playerName name of the player to be removed
     * @author Rishi Ravikumar
     */
    public void removePlayer(String p_playerName) {
        // implementation here
        // must remove a player object from GameManager->d_playerList
    }

    /**
     * Adds an order to the current player’s list of orders
     *
     * @author Rishi Ravikumar
     */
    public void issueOrder(int p_countryID, int num) {
        // implementation here
        // must call the d_currentPlayerTurn.issue_order()
    }

    /**
     * Executes all the orders from all the players for the current turn, updating the game state
     *
     * @author Rishi Ravikumar
     */
    public void executeOrder(String p_playerName) {
        // implementation here
        // iterates through each player and does the following for each order:
        //      fetch the next order object using d_currentPlayerTurn.next_order() and then run Order->execute()
    }

    /**
     * Displays the map, and the current game state
     *
     * @author Rishi Ravikumar
     */
    public void showMap() {
        // implementation here
        // show all countries and continents,
        // armies on each country, ownership, and connectivity in a way that enables efficient game play
    }

    public List<Player> getD_playerList() {
        return d_playerList;
    }

    public void setD_playerList(List<Player> d_playerList) {
        this.d_playerList = d_playerList;
    }

    public Player getD_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    public void setD_currentPlayerTurn(Player d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
    }

    public GamePhase getD_gamePhase() {
        return d_gamePhase;
    }

    public void setD_gamePhase(GamePhase d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }

    public enum GamePhase {
        Game_Startup,
        AssignReinforcements,
        IssueOrder,
        ExecuteOrder
    }
}
