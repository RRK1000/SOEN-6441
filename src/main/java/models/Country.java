package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Country in the game.
 * Manages country-related functionalities.
 * <p>
 * This class is related to the Continent class.
 * This class is also related to the Map class.
 *
 * @author Yusuke
 */
public class Country {
    private int d_countryID;
    private String d_countryName;
    private int d_continentID;
    private List<Integer> d_neighbourCountryIDList;
    private int d_numArmies;

    /**
     * Default constructor for Country class.
     */
    public Country() {
        d_neighbourCountryIDList = new ArrayList<>();
    }

    /**
     * Constructor with parameters for Country class.
     *
     * @param p_countryID              The ID of the country.
     * @param p_neighbourCountryIDList The list of neighboring country IDs
     * @param p_numArmies              The number of armies in the country.
     */
    public Country(int p_countryID, List<Integer> p_neighbourCountryIDList, int p_numArmies) {
        this.d_countryID = p_countryID;
        this.d_neighbourCountryIDList = p_neighbourCountryIDList;
        this.d_numArmies = p_numArmies;
    }

    /**
     * Gets the country ID.
     *
     * @return The country ID.
     */
    public int getD_countryID() {
        return d_countryID;
    }

    /**
     * Sets the country ID.
     *
     * @param p_countryID The new country ID.
     */
    public void setD_countryID(int p_countryID) {
        this.d_countryID = p_countryID;
    }

    public String getD_countryName() {
        return d_countryName;
    }

    public void setD_countryName(String d_countryName) {
        this.d_countryName = d_countryName;
    }

    public int getD_continentID() {
        return d_continentID;
    }

    public void setD_continentID(int d_continentID) {
        this.d_continentID = d_continentID;
    }

    /**
     * Gets the list of neighboring country IDS
     *
     * @return The list of neighboring country IDS
     */
    public List<Integer> getD_neighbourCountryIDList() {
        return d_neighbourCountryIDList;
    }

    /**
     * Sets the list of neighboring country IDs.
     *
     * @param p_neighbourCountryIDList The new list of neighboring country IDs.
     */
    public void setD_neighbourCountryIDList(List<Integer> p_neighbourCountryIDList) {
        this.d_neighbourCountryIDList = p_neighbourCountryIDList;
    }

    /**
     * Gets the number of armies in the country.
     *
     * @return The number of armies.
     */
    public int getD_numArmies() {
        return d_numArmies;
    }

    /**
     * Sets the number of armies in the country.
     *
     * @param p_numArmies The new number of armies.
     */
    public void setD_numArmies(int p_numArmies) {
        this.d_numArmies = p_numArmies;
    }
}
