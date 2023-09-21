package models;

/**
 * Represents a Player in the game.
 * Manages player-related functionalities.
 * This class is used in relationship with the GameController class to manage player actions.
 *  @author Yusuke
 */
public class Player {
    // Data Members
    private String d_playerName;

    /**
     * Default constructor for Player class.
     */
    public Player() {
        // Implementation here
    }

    /**
     * Constructor with parameters for Player class.
     * @param p_playerName The name of the player.
     */
    public Player(String p_playerName) {
        this.d_playerName = p_playerName;
    }

    /**
     * Issues an order.
     */
    public void issueOrder() {
        // Implementation here
    }

    /**
     * Moves to the next order.
     */
    public void nextOrder() {
        // Implementation here
    }

    /**
     * Gets the player name.
     * @return The player name.
     */
    public String getPlayerName() {
        return d_playerName;
    }

    /**
     * Sets the player name.
     * @param p_playerName The new player name.
     */
    public void setPlayerName(String p_playerName) {
        this.d_playerName = p_playerName;
    }
}
