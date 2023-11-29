package util;

import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;
import static util.ConquestMapFileReaderAdapter.convertToDominationFormat;

public class ConquestMapFileReader {
    /**
     * Loads a map from a Conquest formatted file
     *
     * @param p_fileName Name of the file to be read
     * @return Loaded Map object
     * @throws IOException if there is an issue in file reading
     */
    public Map loadMap(String p_fileName) throws IOException {

        Map l_conquestMap = loadConquestMap(p_fileName);
        if (l_conquestMap == null) {
            throw new IOException("Failed to load Conquest map format.");
        }

        Map l_dominationMap = convertToDominationFormat(l_conquestMap);

        return l_dominationMap;
    }

    /**
     * Saves a domination map to a file in Conquest format
     *
     * @param p_map      The Map object to be saved
     * @param p_fileName The file name to save the map to
     * @return true if map is saved successfully, false otherwise
     * @throws IOException if there is an issue in file writing
     */
    public boolean saveMap(Map p_map, String p_fileName) throws IOException {

        if (!MapUtil.isValidMap(p_map)) {
            out.println("Map is invalid, not saving to file.");
            return false;
        }

        String l_modifiedFileName = p_fileName;

        String l_fullPath = "src/main/resources/" + p_fileName;

        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_fullPath))) {
            l_writer.write("[Map]\n");
            l_writer.write("author=SOEN6441-U2\n\n");
            l_writer.write("[Continents]\n");
            for (Continent l_continent : p_map.getD_continentMapGraph().vertexSet()) {
                l_writer.write(l_continent.getD_continentName() + "=" + l_continent.getD_continentValue() + "\n");
            }
            l_writer.write("\n");

            l_writer.write("[Territories]\n");
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                Continent l_continent = p_map.getD_continentByID(l_country.getD_continentID());
                l_writer.write(l_country.getD_countryName() + "," + l_country.getD_xCoordinate() + ","
                        + l_country.getD_yCoordinate() + "," + l_continent.getD_continentName());
                for (Integer l_neighborID : l_country.getD_neighbourCountryIDList()) {
                    Country l_neighborCountry = p_map.getD_countryByID(l_neighborID);
                    l_writer.write("," + l_neighborCountry.getD_countryName());
                }
                l_writer.write("\n");
            }
        } catch (IOException e) {
            out.println("Error saving the map: " + e.getMessage());
            return false;
        }

        out.println("Map has been saved to: " + l_modifiedFileName);
        return true;
    }

    /**
     * Loads a map from a file in Conquest format
     *
     * @param p_fileName Name of the file to be read
     * @return Map object loaded from the file
     * @throws IOException if there is an issue in file reading
     */
    public static Map loadConquestMap(String p_fileName) throws IOException {
        Map l_conquestMap = new Map();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = new DefaultDirectedGraph<>(
                DefaultEdge.class);
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        HashMap<String, Continent> l_continentMap = new HashMap<>();
        HashMap<String, Integer> l_continentIDMap = new HashMap<>();
        HashMap<String, Country> l_countryMap = new HashMap<>();
        HashMap<String, Integer> l_countryNameIdMap = new HashMap<>();

        int l_continentID = 1;
        int l_countryID = 1;

        try (BufferedReader l_reader = new BufferedReader(new FileReader("src/main/resources/" + p_fileName))) {
            String l_line;
            boolean l_isContinentsSection = false, l_isTerritoriesSection = false;

            while ((l_line = l_reader.readLine()) != null) {
                if (l_line.trim().equals("[Continents]")) {
                    l_isContinentsSection = true;
                    l_isTerritoriesSection = false;
                    continue;
                }
                if (l_line.trim().equals("[Territories]")) {
                    l_isContinentsSection = false;
                    l_isTerritoriesSection = true;
                    continue;
                }
                if (l_isContinentsSection && !l_line.isEmpty()) {
                    String[] l_parts = l_line.split("=");
                    Continent l_continent = new Continent();
                    l_continent.setD_continentID(l_continentID);
                    l_continent.setD_continentName(l_parts[0].trim());
                    l_continent.setD_continentValue(Integer.parseInt(l_parts[1].trim()));
                    l_continentMapGraph.addVertex(l_continent);
                    l_continentMap.put(l_parts[0].trim(), l_continent);
                    l_continentIDMap.put(l_parts[0].trim(), l_continentID++);
                }
            }
        }

        try (BufferedReader l_reader2 = new BufferedReader(new FileReader("src/main/resources/" + p_fileName))) {
            String l_line;
            boolean l_isTerritoriesSection = false;

            while ((l_line = l_reader2.readLine()) != null) {
                if (l_line.trim().equals("[Territories]")) {
                    l_isTerritoriesSection = true;
                    continue;
                }

                if (l_isTerritoriesSection && !l_line.isEmpty()) {
                    String[] l_parts = l_line.split(",");
                    String l_continentName = l_parts[3].trim();
                    Integer l_continentIDForCountry = l_continentIDMap.get(l_continentName);
                    if (l_continentIDForCountry == null) {
                        throw new IOException("Continent not found for the territory: " + l_parts[0].trim());
                    }

                    Country l_country = new Country();
                    l_country.setD_countryID(l_countryID++);
                    l_country.setD_countryName(l_parts[0].trim());
                    l_country.setD_continentID(l_continentIDForCountry);
                    l_country.setD_xCoordinate(Integer.parseInt(l_parts[1].trim()));
                    l_country.setD_yCoordinate(Integer.parseInt(l_parts[2].trim()));
                    l_countryMapGraph.addVertex(l_country);
                    l_countryMap.put(l_parts[0].trim(), l_country);
                    l_countryNameIdMap.put(l_parts[0].trim(), l_countryID - 1);
                }
            }
        }

        // Load neighboring relationships
        try (BufferedReader l_reader3 = new BufferedReader(new FileReader("src/main/resources/" + p_fileName))) {
            String l_line;
            boolean l_isTerritoriesSection = false;

            while ((l_line = l_reader3.readLine()) != null) {
                if (l_line.trim().equals("[Territories]")) {
                    l_isTerritoriesSection = true;
                    continue;
                }

                if (l_isTerritoriesSection && !l_line.isEmpty()) {
                    String[] l_parts = l_line.split(",");
                    Country l_country = l_countryMap.get(l_parts[0].trim());
                    if (l_country != null) {
                        List<Integer> l_neighbors = new ArrayList<>();
                        for (int i = 4; i < l_parts.length; i++) {
                            String l_neighborName = l_parts[i].trim();
                            Integer l_neighborId = l_countryNameIdMap.get(l_neighborName);
                            if (l_neighborId != null) {
                                l_neighbors.add(l_neighborId);
                            }
                        }
                        l_country.setD_neighbourCountryIDList(l_neighbors);
                    }
                }
            }
        }

        l_conquestMap.setD_continentMapGraph(l_continentMapGraph);
        l_conquestMap.setD_countryMapGraph(l_countryMapGraph);
        return l_conquestMap;
    }
}
