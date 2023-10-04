package models;

/**
 * Represents an Order in the game.
 * Manages order-executing functionalities.
 * This class is used in relationship with the GameController to manage actions of an order given by a player.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 * @author Anuja Somthankar
 */
public class Order {
    private Country d_country;
    private int d_num;

    /**
     * Constructor for the Order class.
     *
     * @param p_country The country on which the order is to be executed.
     * @param p_num     The number of armies to be deployed.
     */
    public Order(Country p_country, int p_num) {
        this.d_country = p_country;
        this.d_num = p_num;
    }

    /**
     * Gets the country specified in the order
     *
     * @return d_num country on which armies on deployed
     */
    public Country getD_country() {
        return d_country;
    }

    /**
     * Sets the country specified in the order.
     *
     * @param p_country The country on which armies are to be deployed.
     */
    public void setD_country(Country p_country) {
        this.d_country = p_country;
    }

    /**
     * Gets the number of armies deployed
     *
     * @return d_num Number of armies deployed on the country ID
     */
    public int getD_num() {
        return d_num;
    }

    /**
     * Sets the number of armies deployed
     *
     * @param p_num Number of armies deployed on the country ID
     */
    public void setD_num(int p_num) {
        this.d_num = p_num;
    }

    /**
     * Executes an Order
     */
    public void execute() {
        d_country.setD_numArmies(d_country.getD_numArmies() + d_num);
    }
}
