package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import models.Continent;
import models.Country;
import models.Map;

/**
 * Class for reading and writing maps in Domination file format
 * @author yusuke ishii
 */
public class DominationMapFileReader implements MapFileReader {

    /**
     * Loads a map from a Domination formatted file
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
                        loadContinents(l_reader, l_continentMapGraph, l_map);
                        break;
                    case "[countries]":
                        loadCountries(l_reader, l_countryMapGraph, l_map);
                        break;
                    case "[borders]":
                        loadBorders(l_reader, l_countryMapGraph, l_continentMapGraph, l_map);
                        break;
                }
            }
        }
        l_map.setD_continentMapGraph(l_continentMapGraph);
        l_map.setD_countryMapGraph(l_countryMapGraph);
        return l_map;
    }

    private void loadContinents(BufferedReader p_reader, DefaultDirectedGraph<Continent, DefaultEdge> p_continentMapGraph, Map p_map) throws IOException {
        String l_line;
        while ((l_line = p_reader.readLine()) != null && !l_line.isEmpty()) {
            String[] l_continentData = l_line.split(" ");
            Continent l_continent = new Continent();
            l_continent.setD_continentName(l_continentData[0]);
            l_continent.setD_continentValue(Integer.parseInt(l_continentData[1]));
            p_continentMapGraph.addVertex(l_continent);
        }
    }

    private void loadCountries(BufferedReader p_reader, DefaultDirectedGraph<Country, DefaultEdge> p_countryMapGraph, Map p_map) throws IOException {
        String l_line;
        while ((l_line = p_reader.readLine()) != null && !l_line.isEmpty()) {
            String[] l_countryData = l_line.split(" ");
            Country l_country = new Country();
            l_country.setD_countryID(Integer.parseInt(l_countryData[0]));
            l_country.setD_countryName(l_countryData[1]);
            l_country.setD_continentID(Integer.parseInt(l_countryData[2]));
            p_countryMapGraph.addVertex(l_country);
        }
    }

    private void loadBorders(BufferedReader p_reader, DefaultDirectedGraph<Country, DefaultEdge> p_countryMapGraph, DefaultDirectedGraph<Continent, DefaultEdge> p_continentMapGraph, Map p_map) throws IOException {
        String l_line;
        while ((l_line = p_reader.readLine()) != null) {
            String[] l_borderData = l_line.split(" ");
            Country l_currentCountry = p_map.getD_countryByID(Integer.parseInt(l_borderData[0]));
            List<Integer> l_neighbourCountryIDList = new ArrayList<>();

            for (int l_id = 1; l_id < l_borderData.length; l_id++) {
                int l_neighbourID = Integer.parseInt(l_borderData[l_id]);
                l_neighbourCountryIDList.add(l_neighbourID);
                Country l_neighbour = p_map.getD_countryByID(l_neighbourID);
                p_countryMapGraph.addEdge(l_currentCountry, l_neighbour);
            }
            l_currentCountry.setD_neighbourCountryIDList(l_neighbourCountryIDList);
        }
    }

    /**
     * Saves a map to a file in Domination format
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
            // Write continents
            l_writer.write("[continents]\n");
            for (Continent l_continent : p_map.getD_continentMapGraph().vertexSet()) {
                l_writer.write(l_continent.getD_continentName() + " " + l_continent.getD_continentValue() + "\n");
            }
            l_writer.write("\n");

            // Write countries
            l_writer.write("[countries]\n");
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                l_writer.write(l_country.getD_countryID() + " " + l_country.getD_countryName() + " " + l_country.getD_continentID() + "\n");
            }
            l_writer.write("\n");

            // Write borders
            l_writer.write("[borders]\n");
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                StringBuilder l_borderData = new StringBuilder();
                l_borderData.append(l_country.getD_countryID());
                for (int l_neighbourID : l_country.getD_neighbourCountryIDList()) {
                    l_borderData.append(" ").append(l_neighbourID);
                }
                l_writer.write(l_borderData.toString() + "\n");
            }
        }
        return true;
    }
}

