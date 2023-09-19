package models;

/**
 * Represents a Continent in the game.
 * Manages continent-related functionalities.
 * This class is related to the Country class, as each Continent can contain multiple Countries.
 *  @author
 */
public class Continent {
    // Data Members
    private int d_continentID;
    private int d_continentValue;

    /**
     * Default constructor for Continent class.
     */
    public Continent() {
        // Implementation here
    }

    /**
     * Constructor with parameters for Continent class.
     * @param p_continentID The ID of the continent.
     * @param p_continentValue The value of the continent.
     */
    public Continent(int p_continentID, int p_continentValue) {
        this.d_continentID = p_continentID;
        this.d_continentValue = p_continentValue;
    }

    /**
     * Edits the continent.
     */
    public void editCountry() {
        // Implementation here
    }

    /**
     * Edits the neighboring continents.
     */
    public void editNeighbor() {
        // Implementation here
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
}
