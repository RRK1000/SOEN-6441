package models;

/**
 * Represents an Order in the game.
 * Manages order-executing functionalities.
 * This class is used in relationship with the GameController to manage actions of an order given by a player.
 *
 * @author Anuja-Somthankar
 */
public class Order {
    private int d_countryID;
    private int d_num;

    /**
     * Gets the country ID specified in the order
     * @return d_countryID The country ID on which order of attack is given
     */
    public int getD_countryID() {
        return d_countryID;
    }

    /**
     * Sets the country ID specified in the order
     * @param p_countryID The country ID on which order of attack is given
     */
    public void setD_countryID(int p_countryID) {
        this.d_countryID = p_countryID;
    }

    /**
     * Gets the number of armies deployed
     * @return d_num Number of armies deployed on the country ID
     */
    public int getD_num() {
        return d_num;
    }

    /**
     * Sets the number of armies deployed
     * @param p_num Number of armies deployed on the country ID
     */
    public void setD_num(int p_num) {
        this.d_num = p_num;
    }

    /**
     * Executes an Order
     */
    public void execute(){
        //Implementation here
    }
}
