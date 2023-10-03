package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player in the game.
 * Manages player-related functionalities.
 * This class is used in relationship with the GameController class to manage player actions.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 */
public class Player {
    // Data Members
    private String d_playerName;
    private int d_numArmies;
    private List<Country> d_countryList;
    private List<Continent> d_continentList;
    private List<Order> d_orderList;
    private Order d_currentOrder;

    /**
     * Default constructor for Player class
     *
     * @param p_playerName Name of the player
     */
    public Player(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_countryList = new ArrayList<>();
        this.d_continentList = new ArrayList<>();
        this.d_orderList = new ArrayList<>();
    }

    /**
     * Constructor with parameters for Player class
     *
     * @param p_playerName   Name of the player
     * @param p_numArmies    Number of armies the player has
     * @param p_countryList  List of countries the player owns
     * @param p_orderList    List of orders the player has issued
     * @param p_currentOrder Current Order issued by the player
     */
    public Player(String p_playerName, int p_numArmies, List<Country> p_countryList, List<Order> p_orderList, Order p_currentOrder) {
        this.d_playerName = p_playerName;
        this.d_numArmies = p_numArmies;
        this.d_countryList = p_countryList;
        this.d_orderList = p_orderList;
        this.d_currentOrder = p_currentOrder;
    }

    /**
     * Adds a country to the player's list of countries
     *
     * @param p_country The country to be added
     */
    public void addCountry(Country p_country) {
        this.d_countryList.add(p_country);
    }

    /**
     * Issues an order. Adds the current order issued by the player to their order list
     */
    public void issueOrder() {
        d_orderList.add(d_currentOrder);
        d_numArmies -= d_currentOrder.getD_num();
    }

    /**
     * Moves to the next order
     *
     * @return The next order, or null if no orders are left
     */
    public Order nextOrder() {
        Order l_order = null;
        if (!d_orderList.isEmpty()) {
            l_order = d_orderList.get(0);
            d_orderList.remove(0);
        }
        return l_order;
    }

    /**
     * Gets the player name
     *
     * @return The player name
     */
    public String getD_playerName() {
        return d_playerName;
    }

    /**
     * Sets the player name
     *
     * @param p_playerName The new player name
     */
    public void setD_playerName(String p_playerName) {
        this.d_playerName = p_playerName;
    }

    /**
     * Gets the number of armies a player holds
     *
     * @return d_numArmies Number of armies of a player
     */
    public int getD_numArmies() {
        return d_numArmies;
    }

    /**
     * Sets the number of armies a player holds
     *
     * @param p_numArmies Number of armies of a player
     */
    public void setD_numArmies(int p_numArmies) {
        this.d_numArmies = p_numArmies;
    }

    /**
     * Gets the list of countries held by the player
     *
     * @return d_countryList List of Country objects under player's ownership
     */
    public List<Country> getD_countryList() {
        return d_countryList;
    }

    /**
     * Sets the list of countries held by the player
     *
     * @param p_countryList List of Country objects under player's ownership
     */
    public void setD_countryList(List<Country> p_countryList) {
        this.d_countryList = p_countryList;
    }

    /**
     * Gets the list of orders issued by the player
     *
     * @return d_orderList List of orders issued by the player
     */
    public List<Order> getD_orderList() {
        return d_orderList;
    }

    /**
     * Sets the list of orders issued by the player
     *
     * @param p_orderList List of orders issued by the player
     */
    public void setD_orderList(List<Order> p_orderList) {
        this.d_orderList = p_orderList;
    }

    /**
     * Gets the current Order of the current player that is being issued
     *
     * @return current Order of the current player that is being issued
     */
    public Order getD_currentOrder() {
        return d_currentOrder;
    }

    /**
     * Sets the current Order of the current player that is being issued
     *
     * @param d_currentOrder current Order of the current player that is being issued
     */
    public void setD_currentOrder(Order d_currentOrder) {
        this.d_currentOrder = d_currentOrder;
    }

    /**
     * Gets the list of continents held by the player
     *
     * @return List of continents the player owns
     */

    public List<Continent> getD_continentList() {
        return d_continentList;
    }

    /**
     * Sets the list of continents held by the player
     *
     * @param p_continentList List of Continent objects under player's ownership
     */
    public void setD_continentList(List<Continent> p_continentList) {
        this.d_continentList = p_continentList;
    }
}