package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Continent in the game.
 * Manages continent-related functionalities.
 * This class is related to the Country class, as each Continent can contain multiple Countries.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 * @author Yusuke
 */
public class Continent implements Serializable {
    private int d_continentID;
    private String d_continentName;
    private int d_continentValue;
    private final List<Country> d_countryList;

    /**
     * Default constructor for Continent class.
     */
    public Continent() {
        d_countryList = new ArrayList<>();
    }

    /**
     * Adds a country to the continent.
     *
     * @param p_country The country to be added.
     */
    public void addCountry(Country p_country) {
        d_countryList.add(p_country);
    }

    /**
     * Removes a country from the continent.
     *
     * @param p_country The country to be removed.
     */
    public void removeCountry(Country p_country) {
        d_countryList.remove(p_country);
    }

    /**
     * Gets the continent ID.
     *
     * @return The continent ID.
     */
    public int getD_continentID() {
        return d_continentID;
    }

    /**
     * Sets the continent ID.
     *
     * @param p_continentID Sets the continent ID.
     */
    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * Sets the continent name
     *
     * @param p_continentName Sets the continent name
     */
    public void setD_continentName(String p_continentName) {
        this.d_continentName = p_continentName;
    }

    
    /**
     * Gets the continent Name
     *
     * @return The continent Name
     */
    
    public String getD_continentName() {
        return d_continentName;
    }
    
    /**
     * Gets the continent value.
     *
     * @return The continent value.
     */
    public int getD_continentValue() {
        return d_continentValue;
    }

    /**
     * Sets the continent value.
     *
     * @param p_continentValue The new continent value.
     */
    public void setD_continentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }

    /**
     * Gets the list of countries in the continent.
     *
     * @return The list of countries.
     */
    public List<Country> getD_countryList() {
        return d_countryList;
    }
}
