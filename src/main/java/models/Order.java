package models;

/**
 * Represents an Order in the game.
 * Manages order-executing functionalities.
 * This class is used in relationship with the GameController to manage actions of an order given by a player.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 * @author Nimisha Jadav
 */
public interface Order {
    /**
     * Executes an Order
     */
    public void execute();

    /**
     * Function to validate the command against the current player
     *
     * @return true if the command is valid, else returns false
     */
    public boolean isValid();
}