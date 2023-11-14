package models;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.Serializable;

/**
 * Represents a Map in the game. Responsible for showing and saving the map.
 * <p>
 * This class is related to the MapEditor and MapValidator classes in the
 * Utility package, as it utilizes their functionalities for editing and
 * validating the map.
 * <p>
 * This class is also related to the Country class, as each Map can contain
 * multiple Countries.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 * @author Yusuke
 */
public class Map implements Serializable {
    private DefaultDirectedGraph<Country, DefaultEdge> d_countryMapGraph;
    private DefaultDirectedGraph<Continent, DefaultEdge> d_continentMapGraph;

    /**
     * Default constructor for the Map class. Initializes the country and continent
     * directed graphs.
     */
    public Map() {
        d_countryMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        d_continentMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Retrieves a country by its ID.
     *
     * @param p_countryID The ID of the country to retrieve.
     * @return The country with the specified ID, or null if not found.
     */
    public Country getD_countryByID(int p_countryID) {
        for (Country l_c : d_countryMapGraph.vertexSet()) {
            if (l_c.getD_countryID() == p_countryID)
                return l_c;
        }
        return null;
    }

    /**
     * Retrieves a continent by its ID.
     *
     * @param p_continentID The ID of the continent to retrieve.
     * @return The continent with the specified ID, or null if not found.
     */
    public Continent getD_continentByID(int p_continentID) {
        for (Continent l_c : d_continentMapGraph.vertexSet()) {
            if (l_c.getD_continentID() == p_continentID)
                return l_c;
        }
        return null;
    }

    /**
     * Gets the directed graph representing the countries in the map.
     *
     * @return The directed graph of countries.
     */
    public DefaultDirectedGraph<Country, DefaultEdge> getD_countryMapGraph() {
        return d_countryMapGraph;
    }

    /**
     * Sets the directed graph representing the countries in the map.
     *
     * @param p_countryMapGraph The new directed graph of countries.
     */
    public void setD_countryMapGraph(DefaultDirectedGraph<Country, DefaultEdge> p_countryMapGraph) {
        this.d_countryMapGraph = p_countryMapGraph;
    }

    /**
     * Gets the directed graph representing the continents in the map.
     *
     * @return The directed graph of continents.
     */
    public DefaultDirectedGraph<Continent, DefaultEdge> getD_continentMapGraph() {
        return d_continentMapGraph;
    }

    /**
     * Sets the directed graph representing the continents in the map.
     *
     * @param p_continentMapGraph The new directed graph of continents.
     */
    public void setD_continentMapGraph(DefaultDirectedGraph<Continent, DefaultEdge> p_continentMapGraph) {
        this.d_continentMapGraph = p_continentMapGraph;
    }
}
