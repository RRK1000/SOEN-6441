package models;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private List<Integer> d_skipTurnList;
    private String d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;

    /**
     * Constructs a new GameState with the specified parameters.
     *
     * @param p_skipTurnList       The list of skipped turns.
     * @param p_gamePhase          The current game phase.
     * @param p_playerList         The list of players.
     * @param p_currentPlayerTurn  The index of the current player's turn.
     * @param p_map                The game map.
     */
    public GameState(List<Integer> p_skipTurnList, String p_gamePhase, List<Player> p_playerList, int p_currentPlayerTurn, Map p_map) {
        this.d_skipTurnList = p_skipTurnList;
        this.d_gamePhase = p_gamePhase;
        this.d_playerList = p_playerList;
        this.d_currentPlayerTurn = p_currentPlayerTurn;
        this.d_map = p_map;
    }

    /**
     * Gets the list of skipped turns.
     *
     * @return The list of skipped turns.
     */
    public List<Integer> getD_skipTurnList() {
        return d_skipTurnList;
    }

    /**
     * Sets the list of skipped turns.
     *
     * @param p_skipTurnList The new list of skipped turns.
     */
    public void setD_skipTurnList(List<Integer> p_skipTurnList) {
        this.d_skipTurnList = p_skipTurnList;
    }

    /**
     * Gets the current game phase.
     *
     * @return The current game phase.
     */
    public String getD_gamePhase() {
        return d_gamePhase;
    }

    /**
     * Sets the current game phase.
     *
     * @param p_gamePhase The new game phase.
     */
    public void setD_gamePhase(String p_gamePhase) {
        this.d_gamePhase = p_gamePhase;
    }

   /**
     * Gets the list of players.
     *
     * @return The list of players.
     */
    public List<Player> getD_playerList() {
        return d_playerList;
    }

    /**
     * Sets the list of players.
     *
     * @param p_playerList The new list of players.
     */
    public void setD_playerList(List<Player> p_playerList) {
        this.d_playerList = p_playerList;
    }

    /**
     * Gets the index of the current player's turn.
     *
     * @return The index of the current player's turn.
     */
    public int getD_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * Sets the index of the current player's turn.
     *
     * @param p_currentPlayerTurn The new index of the current player's turn.
     */
    public void setD_currentPlayerTurn(int p_currentPlayerTurn) {
        this.d_currentPlayerTurn = p_currentPlayerTurn;
    }

    /**
     * Gets the game map.
     *
     * @return The game map.
     */
    public Map getD_map() {
        return d_map;
    }

    /**
     * Sets the game map.
     *
     * @param p_map The new game map.
     */
    public void setD_map(Map p_map) {
        this.d_map = p_map;
    }
}
