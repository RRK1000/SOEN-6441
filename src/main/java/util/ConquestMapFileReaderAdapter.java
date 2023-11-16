package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import models.Continent;
import models.Country;
import models.Map;

/**
 * Adapter class for reading and writing maps in Conquest file format
 *  * @author Yusuke
 */
public class ConquestMapFileReaderAdapter implements MapFileReader {

    /**
     * Loads a map from a Conquest formatted file
     *
     * @param p_fileName Name of the file to be read
     * @return Loaded Map object
     * @throws IOException if there is an issue in file reading
     */
    @Override
    public Map loadMap(String p_fileName) throws IOException {
        Map l_map = new Map();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_fileName))) {
            String l_line;
            while ((l_line = l_reader.readLine()) != null) {
                if (l_line.isEmpty()) continue;
                
                switch (l_line) {
                    case "[continents]":
                        MapUtil.loadContinents(l_reader, l_continentMapGraph, l_map);
                        break;
                    case "[countries]":
                        MapUtil.loadCountries(l_reader, l_countryMapGraph, l_map);
                        break;
                    case "[borders]":
                        MapUtil.loadBorders(l_reader, l_countryMapGraph, l_continentMapGraph, l_map);
                        break;
                }
            }
        }
        l_map.setD_continentMapGraph(l_continentMapGraph);
        l_map.setD_countryMapGraph(l_countryMapGraph);
        return l_map;
    }

    /**
     * Saves a map to a file in Conquest format
     *
     * @param p_map The Map object to be saved
     * @param p_fileName The file name to save the map to
     * @return true if map is saved successfully, false otherwise
     * @throws IOException if there is an issue in file writing
     */
    @Override
    public boolean saveMap(Map p_map, String p_fileName) throws IOException {
        if (!MapUtil.isValidMap(p_map)) {
            return false;
        }

        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_fileName))) {
            l_writer.write("[continents]\n");
            for (Continent l_continent : p_map.getD_continentMapGraph().vertexSet()) {
                l_writer.write(l_continent.getD_continentName() + "=" + l_continent.getD_continentValue() + "\n");
            }
            l_writer.write("\n");

            l_writer.write("[countries]\n");
            HashMap<Integer, Continent> l_continentMap = new HashMap<>();
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                Continent l_continent = p_map.getD_continentByID(l_country.getD_continentID());
                l_continentMap.put(l_country.getD_continentID(), l_continent);
                l_writer.write(l_country.getD_countryID() + "," + l_country.getD_countryName() + "," + l_continent.getD_continentName() + "\n");
            }
            l_writer.write("\n");

            l_writer.write("[borders]\n");
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                StringBuilder l_borderData = new StringBuilder();
                l_borderData.append(l_country.getD_countryID());
                for (int l_neighborID : l_country.getD_neighbourCountryIDList()) {
                    l_borderData.append(",").append(l_neighborID);
                }
                l_writer.write(l_borderData.toString() + "\n");
            }
        }
        return true;
    }
}
