package models;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Map in the game.
 * Responsible for showing and saving the map.
 * 
 * This class is related to the MapEditor and MapValidator classes in the Utility package,
 * as it utilizes their functionalities for editing and validating the map.
 * 
 * This class is also related to the Country class, as each Map can contain multiple Countries.
 * 
 * @author Yusuke
 */
public class Map {

    private DirectedGraph<Country, DefaultEdge> d_countryMapGraph;
    private DirectedGraph<Continent, DefaultEdge> d_continentMapGraph;
    private List<Country> d_countries;
    private List<Continent> d_continents;

    public Map() {
        d_countryMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        d_continentMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        d_countries = new ArrayList<>();
        d_continents = new ArrayList<>();
    }

    public Country getD_countryByID(int p_countryID) {
        for (Country c: d_countryMapGraph.vertexSet()) {
            if(c.getCountryID() == p_countryID)
                return c;
        }
        return null;
    }

    public Continent getD_continentByID(int p_continentID) {
        for (Continent c: d_continentMapGraph.vertexSet()) {
            if(c.getContinentID() == p_continentID)
                return c;
        }
        return null;
    }

    public DirectedGraph<Country, DefaultEdge> getD_countryMapGraph() {
        return d_countryMapGraph;
    }

    public void setD_countryMapGraph(DirectedGraph<Country, DefaultEdge> d_countryMapGraph) {
        this.d_countryMapGraph = d_countryMapGraph;
    }

    public DirectedGraph<Continent, DefaultEdge> getD_continentMapGraph() {
        return d_continentMapGraph;
    }

    public void setD_continentMapGraph(DirectedGraph<Continent, DefaultEdge> d_continentMapGraph) {
        this.d_continentMapGraph = d_continentMapGraph;
    }

    /**
     * Shows the map.
     */
    public void showMap() {
        System.out.println("Showing the map...");
        for (Continent continent : d_continents) {
            System.out.println("Continent: " + continent.getContinentID());
            for (Country country : continent.getCountries()) {
                System.out.println("  Country: " + country.getCountryID());
            }
        }
    }

    /**
     * Saves the map to a file.
     */
    
    public void saveMap() {
        System.out.println("Saving the map...");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("map_data.txt"))) {
            for (Continent continent : d_continents) {
                writer.write("Continent: " + continent.getContinentID() + "\n");
                for (Country country : continent.getCountries()) {
                    writer.write("  Country: " + country.getCountryID() + "\n");
                }
            }
            System.out.println("Map saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the map: " + e.getMessage());
        }
    }
}
