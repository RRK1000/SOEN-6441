package models;

import java.util.List;

/**
 * Represents a Country in the game.
 * Manages country-related functionalities.
 * 
 * This class is related to the Continent class.
 * This class is also related to the Map class.
 *
 * @author Yusuke
 * 
 */
public class Country {
    // Data Members
    private int d_countryID;
    private List<Integer> d_neighborhoodCountryID;
    private int d_numArmies;
    private Map d_map;  // Relationship to Map
    private Continent d_continent;  // Relationship to Continent
    private String d_name;  // Added name data member

    /**
     * Gets the name of the country.
     * @return The name of the country.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Sets the name of the country.
     * @param p_name The new name of the country.  // Adjusted parameter name to follow convention
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Default constructor for Country class.
     */
    public Country() {
        // Implementation here
    }

    /**
     * Constructor with parameters for Country class.
     * @param p_countryID The ID of the country.
     * @param p_neighborhoodCountryID The list of neighboring country IDs.
     * @param p_numArmies The number of armies in the country.
     */
    public Country(int p_countryID, List<Integer> p_neighborhoodCountryID, int p_numArmies) {
        this.d_countryID = p_countryID;
        this.d_neighborhoodCountryID = p_neighborhoodCountryID;
        this.d_numArmies = p_numArmies;
    }

    /**
     * Edits the country.
     */
    public void editCountry() {
        // Implementation here
    }

    /**
     * Edits the neighboring countries.
     */
    public void editNeighbor() {
        // Implementation here
    }

    /**
     * Gets the country ID.
     * @return The country ID.
     */
    public int getCountryID() {
        return d_countryID;
    }

    /**
     * Sets the country ID.
     * @param p_countryID The new country ID.
     */
    public void setCountryID(int p_countryID) {
        this.d_countryID = p_countryID;
    }

    /**
     * Gets the list of neighboring country IDs.
     * @return The list of neighboring country IDs.
     */
    public List<Integer> getNeighborhoodCountryID() {
        return d_neighborhoodCountryID;
    }

    /**
     * Sets the list of neighboring country IDs.
     * @param p_neighborhoodCountryID The new list of neighboring country IDs.
     */
    public void setNeighborhoodCountryID(List<Integer> p_neighborhoodCountryID) {
        this.d_neighborhoodCountryID = p_neighborhoodCountryID;
    }

    /**
     * Gets the number of armies in the country.
     * @return The number of armies.
     */
    public int getNumArmies() {
        return d_numArmies;
    }

    /**
     * Sets the number of armies in the country.
     * @param p_numArmies The new number of armies.
     */
    public void setNumArmies(int p_numArmies) {
        this.d_numArmies = p_numArmies;
    }
}