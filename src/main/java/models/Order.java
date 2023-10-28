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
public abstract class Order {
    private Country d_country;
    private int d_num;
    private String d_Type;

    /**
     * Constructor for the Order class.
     *
     * @param p_country The country on which the order is to be executed.
     * @param p_num     The number of armies to be deployed.
     */
    /**public Order(Country p_country, int p_num) {
        this.d_country = p_country;
        this.d_num = p_num;
    }*/

    /**
     * Gets the number of armies deployed
     *
     * @return d_num Number of armies deployed on the country ID
     */
    public int getD_num() {
        return d_num;
    }

    /**
     * Sets the type of Order
     * @param p_Type Indicates the type of order
     */
    public void setD_type(String p_Type){
        this.d_Type = p_Type;
    }

    /**
     * Gets the type of order
     * @return type of order
     */
    public String getD_Type(){
        return d_Type;
    }
    /**
     * Executes an Order
     */
    public abstract void execute();

    /**
     * Function to validate the command
     * @return true if the command is valid, else returns false
     */
    public abstract boolean validate();

    /**
     * Print an order
     */
    public abstract void printOrder();
}