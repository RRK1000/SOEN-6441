package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Country in the game.
 * Manages country-related functionalities.
 * <p>
 * This class is related to the Continent class.
 * This class is also related to the Map class.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 * @author Yusuke Ishii
 */
public class Country implements Serializable {
    private int d_countryID;
    private String d_countryName;
    private int d_continentID;
    private List<Integer> d_neighbourCountryIDList;
    private int d_numArmies;
    private Player d_owner;
    private boolean d_isNeutral;
    private List<String> d_neighbourCountryNames;
    private int d_xCoordinate;
    private int d_yCoordinate;


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
        this.d_isNeutral = false;
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

    /**
     * Gets the country Name.
     *
     * @return the country Name.
     */
    public String getD_countryName() {
        return d_countryName;
    }

    /**
     * Sets the Country Name
     *
     * @param p_countryName The new countryName
     */
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }
    
    /**
     * Gets the ID of the continent to which the country belongs.
     *
     * @return The continent ID.
     */
    public int getD_continentID() {
        return d_continentID;
    }

    /**
     * Sets the ID of the continent to which the country belongs.
     *
     * @param p_continentID The new continent ID.
     */
    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
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

    /**
     * Gets the owner of the country.
     *
     * @return The owner of the country.
     */
    public Player getD_owner() {
        return d_owner;
    }

    /**
     * Sets the owner of the country.
     *
     * @param p_owner The new owner of the country.
     */
    public void setD_owner(Player p_owner) {
        this.d_owner = p_owner;
    }

    /**
     * Checks if the country is a neutral country
     *
     * @return true if country is neutral, false otherwise
     */
    public boolean isD_isNeutral() {
        return d_isNeutral;
    }

    /**
     * Marks a country as neutral
     *
     * @param p_isNeutral boolean that indicates if country is neutral or not
     */
    public void setD_isNeutral(boolean p_isNeutral) {
        this.d_isNeutral = p_isNeutral;
    }
    /**
     * Gets the names of neighboring countries.
     * 
     * @return A list of names of neighboring countries.
     */
    public List<String> getD_neighbourCountryNames() {
        if (d_neighbourCountryNames == null) {
            d_neighbourCountryNames = new ArrayList<>();
        }
        return d_neighbourCountryNames;
    }

    /**
     * Sets the names of neighboring countries
     * 
     * @param p_neighbourCountryNames A list of names of neighboring countries
     */
    public void setD_neighbourCountryNames(List<String> p_neighbourCountryNames) {
        this.d_neighbourCountryNames = p_neighbourCountryNames;
    }

    /**
     * Gets the x-coordinate of the country
     * 
     * @return The x-coordinate
     */
    public int getD_xCoordinate() {
        return d_xCoordinate;
    }

    /**
     * Sets the x-coordinate of the country
     * 
     * @param p_xCoordinate The x-coordinate to set
     */
    public void setD_xCoordinate(int p_xCoordinate) {
        this.d_xCoordinate = p_xCoordinate;
    }

    /**
     * Gets the y-coordinate of the country
     * 
     * @return The y-coordinate
     */
    public int getD_yCoordinate() {
        return d_yCoordinate;
    }

    /**
     * Sets the y-coordinate of the country
     * 
     * @param p_yCoordinate The y-coordinate to set
     */
    public void setD_yCoordinate(int p_yCoordinate) {
        this.d_yCoordinate = p_yCoordinate;
    }
    
}