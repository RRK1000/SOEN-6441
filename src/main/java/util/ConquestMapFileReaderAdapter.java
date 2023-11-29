package util;

import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adapter class for reading and writing maps in Conquest file format
 *
 * @author Yusuke Ishii
 */
public class ConquestMapFileReaderAdapter implements MapFileReader {
    ConquestMapFileReader d_conquestMapFileReader;
    public ConquestMapFileReaderAdapter(ConquestMapFileReader p_conquestMapFileReader){
        this.d_conquestMapFileReader = p_conquestMapFileReader;
    }

    /**
     * Converts a map from Conquest format to Domination format
     *
     * @param p_map Map object in Conquest format
     * @return Map object in Domination format
     */
    public static Map convertToDominationFormat(Map p_map) {
        HashMap<Integer, Continent> l_tempContinents = new HashMap<>();
        HashMap<Integer, Country> l_tempCountries = new HashMap<>();
        HashMap<Integer, List<Integer>> l_tempBorders = new HashMap<>();

        // Continents and countries copying
        for (Continent l_conquestContinent : p_map.getD_continentMapGraph().vertexSet()) {
            Continent l_dominationContinent = new Continent();
            l_dominationContinent.setD_continentID(l_conquestContinent.getD_continentID());
            l_dominationContinent.setD_continentName(l_conquestContinent.getD_continentName());
            l_dominationContinent.setD_continentValue(l_conquestContinent.getD_continentValue());
            l_tempContinents.put(l_conquestContinent.getD_continentID(), l_dominationContinent);
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                if (l_country.getD_continentID() == l_dominationContinent.getD_continentID()) {
                    System.out.println(" - " + l_country.getD_countryName());
                }
            }
        }

        for (Country l_conquestCountry : p_map.getD_countryMapGraph().vertexSet()) {
            Country l_dominationCountry = new Country();
            l_dominationCountry.setD_countryID(l_conquestCountry.getD_countryID());
            l_dominationCountry.setD_countryName(l_conquestCountry.getD_countryName());
            l_dominationCountry.setD_continentID(l_conquestCountry.getD_continentID());
            l_dominationCountry.setD_xCoordinate(l_conquestCountry.getD_xCoordinate());
            l_dominationCountry.setD_yCoordinate(l_conquestCountry.getD_yCoordinate());
            l_tempCountries.put(l_conquestCountry.getD_countryID(), l_dominationCountry);
            l_tempBorders.put(l_dominationCountry.getD_countryID(), l_conquestCountry.getD_neighbourCountryIDList());
            Continent l_continent = l_tempContinents.get(l_conquestCountry.getD_continentID());
        }

        // Neighbors copying
        for (Country l_conquestCountry : p_map.getD_countryMapGraph().vertexSet()) {
            List<Integer> l_neighbors = getNeighborsForCountry(l_conquestCountry, p_map, l_tempBorders);
            Country l_dominationCountry = l_tempCountries.get(l_conquestCountry.getD_countryID());
            System.out.println("Setting neighbors for " + l_dominationCountry.getD_countryName() + " (ID: " + l_dominationCountry.getD_countryID() + ")");
            for (Integer l_neighborID : l_neighbors) {
                System.out.print(l_neighborID + ", ");
            }
            System.out.println();
            l_dominationCountry.setD_neighbourCountryIDList(l_neighbors);
        }

        // Create a new Map object and set relationships
        Map l_newMap = new Map();
        l_newMap.setD_continentMapGraph(new DefaultDirectedGraph<>(DefaultEdge.class));
        l_newMap.setD_countryMapGraph(new DefaultDirectedGraph<>(DefaultEdge.class));

        // Add vertices to the continent and country graphs
        for (Continent l_continent : l_tempContinents.values()) {
            l_newMap.getD_continentMapGraph().addVertex(l_continent);
        }
        for (Country l_country : l_tempCountries.values()) {
            l_newMap.getD_countryMapGraph().addVertex(l_country);
        }

        // Adding edges between countries based on neighboring relationships
        for (java.util.Map.Entry<Integer, List<Integer>> entry : l_tempBorders.entrySet()) {
            Country l_country = l_tempCountries.get(entry.getKey());
            for (Integer l_neighborID : entry.getValue()) {
                Country l_neighborCountry = l_tempCountries.get(l_neighborID);
                l_newMap.getD_countryMapGraph().addEdge(l_country, l_neighborCountry);
            }
        }

        // Adding edges between continents if their countries are neighbors
        for (Country l_country : l_tempCountries.values()) {
            Continent l_continent = l_tempContinents.get(l_country.getD_continentID());
            for (Integer l_neighborID : l_country.getD_neighbourCountryIDList()) {
                Country l_neighborCountry = l_tempCountries.get(l_neighborID);
                Continent l_neighborContinent = l_tempContinents.get(l_neighborCountry.getD_continentID());
                if (!l_continent.equals(l_neighborContinent)) {
                    l_newMap.getD_continentMapGraph().addEdge(l_continent, l_neighborContinent);
                }
            }
        }

        return l_newMap;
    }

    /**
     * Gets neighbors for a country object
     *
     * @param p_map     The Map object to be saved
     * @param p_country The Country data to be loaded
     * @return a list of neighbors to convert to Domination format
     */
    private static List<Integer> getNeighborsForCountry(Country p_country, Map p_map,
														HashMap<Integer, List<Integer>> l_tempBorders) {
        List<Integer> l_neighbors = l_tempBorders.getOrDefault(p_country.getD_countryID(), new ArrayList<>());

        return l_neighbors;
    }

    /**
     * Loads a map from a specified file
     *
     * @param p_fileName The name of the file from which the map will be loaded
     * @return A Map object loaded from the file
     * @throws IOException If there is an issue in reading the file
     */
    @Override
    public Map loadMap(String p_fileName) throws IOException {
        return d_conquestMapFileReader.loadMap(p_fileName);
    }

	/**
	 * Saves a map to a specified file
	 *
	 * @param p_map      The Map object to save
	 * @param p_fileName The name of the file where the map will be saved
	 * @return true if the map is saved successfully, false otherwise
	 * @throws IOException If there is an issue in writing to the file
	 */
	@Override
	public boolean saveMap(Map p_map, String p_fileName) throws IOException {
		return d_conquestMapFileReader.saveMap(p_map, p_fileName);
	}

}