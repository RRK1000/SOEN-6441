package models;

import java.util.List;

/**
 * Represents a Player in the game.
 * Manages player-related functionalities.
 * This class is used in relationship with the GameController class to manage player actions.
 *
 * @author Yusuke, Anuja-Somthankar
 */
public class Player {
    public int d_armiesForNextCountry;
    // Data Members
    private String d_playerName;
    private int d_numArmies;
    private List<Country> d_countryList;
    private List<Continent> d_continentList;
    private List<Order> d_orderList;


    /**
     * Default constructor for Player class.
     */
    public Player(String p_playerName) {
        // Implementation here
        this.d_playerName = p_playerName;
    }

    public Player(String p_playerName, int p_numArmies, List<Country> p_countryList, List<Order> p_orderList) {
        this.d_playerName = p_playerName;
        this.d_numArmies = p_numArmies;
        this.d_countryList = p_countryList;
        this.d_orderList = p_orderList;
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
     *
     * @return The player name.
     */
    public String getD_playerName() {
        return d_playerName;
    }

    /**
     * Sets the player name.
     *
     * @param p_playerName The new player name.
     */
    public void setD_playerName(String p_playerName) {
        this.d_playerName = p_playerName;
    }

    /**
     * Gets the number of armies a player holds
     * @return d_numArmies Number of armies of a player
     */
    public int getD_numArmies() {
        return d_numArmies;
    }

    /**
     * Sets the number of armies a player holds
     * @param p_numArmies Number of armies of a player
     */
    public void setD_numArmies(int p_numArmies) {
        this.d_numArmies = p_numArmies;
    }

    /**
     * Gets the list of countries held by the player
     * @return d_countryList List of Country objects under player's ownership
     */
    public List<Country> getD_countryList() {
        return d_countryList;
    }

    /**
     * Sets the list of countries held by the player
     * @param p_countryList List of Country objects under player's ownership
     */
    public void setD_countryList(List<Country> p_countryList) {
        this.d_countryList = p_countryList;
    }

    /**
     * Gets the list of orders issued by the player
     * @return d_orderList List of orders issued by the player
     */
    public List<Order> getD_orderList() {
        return d_orderList;
    }

    /**
     * Sets the list of orders issued by the player
     * @param p_orderList List of orders issued by the player
     */
    public void setD_orderList(List<Order> p_orderList) {
        this.d_orderList = p_orderList;
    }

    /**
     * Gets the list of continents held by the player
     */
    public List<Continent> getD_continentList() {
        return d_continentList;
    }

    /**
     * Sets the list of continents held by the player
     * @param p_continentList List of Continent objects under player's ownership
     */
    public void setD_continentList(List<Continent> p_continentList) {
        this.d_continentList = p_continentList;
    }
}