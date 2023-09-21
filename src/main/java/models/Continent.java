package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Continent in the game.
 * Manages continent-related functionalities.
 * This class is related to the Country class, as each Continent can contain multiple Countries.
 * @author Yusuke
 */
public class Continent {
    // Data Members
    private int d_continentID;
    private int d_continentValue;
    private List<Country> d_countries;
    private String d_name;  // Added name data member 


    /**
     * Default constructor for Continent class.
     */
    public Continent() {
        d_countries = new ArrayList<>();
    }

    /**
     * Constructor with parameters for Continent class.
     * @param p_continentID The ID of the continent.
     * @param p_continentValue The value of the continent.
     */
    public Continent(int p_continentID, int p_continentValue) {
        this.d_continentID = p_continentID;
        this.d_continentValue = p_continentValue;
        d_countries = new ArrayList<>();
    }
    
    /**
     * Gets the name of the continent.
     * @return The name of the continent.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Sets the name of the continent.
     * @param p_name The new name of the continent.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }
    
    /**
     * Adds a country to the continent.
     * @param p_country The country to be added.
     */
    public void addCountry(Country p_country) {
        d_countries.add(p_country);
    }

    /**
     * Removes a country from the continent.
     * @param p_country The country to be removed.
     */
    public void removeCountry(Country p_country) {
        d_countries.remove(p_country);
    }

    /**
     * Gets the continent ID.
     * @return The continent ID.
     */
    public int getContinentID() {
        return d_continentID;
    }

    /**
     * Sets the continent ID.
     * @param p_continentID The new continent ID.
     */
    public void setContinentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * Gets the continent value.
     * @return The continent value.
     */
    public int getContinentValue() {
        return d_continentValue;
    }

    /**
     * Sets the continent value.
     * @param p_continentValue The new continent value.
     */
    public void setContinentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }

    /**
     * Gets the list of countries in the continent.
     * @return The list of countries.
     */
    public List<Country> getCountries() {
        return d_countries;
    }
}