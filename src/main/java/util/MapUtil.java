package util;

import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MapUtil implements IMapUtil {
    /**
     * Loads the map from a given file, and stores it into {@link models.Map}
     *
     * @param p_filename The name of the file to load the map from
     * @return {@link models.Map}
     * @author Rishi Ravikumar
     */
    @Override
    public Map loadMap(String p_filename) {
        Map l_map = new Map();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        System.out.println("Loading the map from " + p_filename + "...");

        try (BufferedReader reader = new BufferedReader(new FileReader(p_filename))) {
            String line;
            line = reader.readLine();

            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                if (line.startsWith("[continents]")) {
                    int l_continentID = 1;
                    line = reader.readLine();
                    while (!line.isEmpty()) {
                        String[] continentData = line.split(" ");
                        Continent l_continent = new Continent();
                        l_continent.setD_continentID(l_continentID++);
                        l_continent.setD_continentName(continentData[0]);
                        l_continent.setD_continentValue(Integer.parseInt(continentData[1]));
                        l_continentMapGraph.addVertex(l_continent);
                        line = reader.readLine();
                    }
                    l_map.setD_continentMapGraph(l_continentMapGraph);
                    continue;
                }

                if (line.startsWith("[countries]")) {
                    line = reader.readLine();

                    while (!line.isEmpty()) {
                        String[] countryData = line.split(" ");
                        Country l_country = new Country();
                        Continent l_continent = l_map.getD_continentByID(Integer.parseInt(countryData[2]));
                        l_country.setD_countryID(Integer.parseInt(countryData[0]));
                        l_country.setD_continentID(Integer.parseInt(countryData[2]));
                        l_countryMapGraph.addVertex(l_country);
                        l_continent.addCountry(l_country);
                        line = reader.readLine();
                    }
                    l_map.setD_countryMapGraph(l_countryMapGraph);
                    continue;
                }

                if (line.startsWith("[borders]")) {
                    line = reader.readLine();

                    while (line != null) {
                        String[] borderData = line.split(" ");
                        Country l_currentCountry = l_map.getD_countryByID(Integer.parseInt(borderData[0]));

                        List<Integer> l_neighbourhoodCountryIDList = new ArrayList<>();
                        for (int l_id = 1; l_id < borderData.length; l_id++) {
                            l_neighbourhoodCountryIDList.add(Integer.valueOf(borderData[l_id]));
                            Country l_neighbour = l_map.getD_countryByID(Integer.parseInt(borderData[l_id]));
                            l_countryMapGraph.addEdge(l_currentCountry, l_neighbour);
                        }
                        l_currentCountry.setD_neighbourCountryIDList(l_neighbourhoodCountryIDList);
                        line = reader.readLine();
                    }
                }


            }
            l_map.setD_continentMapGraph(l_continentMapGraph);
            l_map.setD_countryMapGraph(l_countryMapGraph);
            System.out.println("Map loaded successfully!");
        } catch (Exception e) {
            System.out.println("Failed to load the file: " + e.getMessage());
        }
        return l_map;
    }

    /**
     * Loads a {@link models.Map} from an existing “domination” map file, or create a new {@link models.Map}
     * if the file does not exist
     *
     * @param p_filename The name of the file to load the map from
     * @return {@link models.Map}
     * @author Rishi Ravikumar
     */
    @Override
    public Map editMap(String p_filename) {
        Map l_map;
        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filename))) {
            l_map = loadMap(p_filename);
            System.out.println("Map Loaded Successfully");
        } catch (Exception l_e) {
            System.out.println("File not found");
            l_map = new Map();
            return l_map;
        }
        return l_map;
    }

    /**
     * Saves the contents {@link models.Map} to a "domination" map file
     *
     * @param p_map The {@link models.Map} from which a file would be generated
     * @return true if the file was saved successfully, false in case the {@link models.Map} is invalid
     * @author Rishi Ravikumar
     */
    @Override
    public Boolean saveMap(Map p_map) {
        if (!isValidMap(p_map)) {
            return false;
        }
        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter("src/main/resources/saveMap.txt"))) {
            l_writer.write("[continents]\n");
            for (Continent l_continent : p_map.getD_continentMapGraph().vertexSet()) {
                l_writer.write(l_continent.getD_continentID() + " " + l_continent.getD_continentValue() + " blue\n");
            }
            l_writer.write("\n");

            l_writer.write("[countries]\n");
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                l_writer.write(l_country.getD_countryID()
                        + " " + l_country.getD_countryName() + " " + l_country.getD_continentID() + " 100 100\n");
            }
            l_writer.write("\n");

            l_writer.write("[borders]\n");
            for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
                StringBuilder l_borderData = new StringBuilder();
                l_borderData.append(l_country.getD_countryID());

                for (int l_neighbourID : l_country.getD_neighbourCountryIDList()) {
                    l_borderData.append(" ");
                    l_borderData.append(l_neighbourID);
                }
                l_writer.write(String.valueOf(l_borderData));
                l_writer.write("\n");
            }
            l_writer.write("\n");
            System.out.println("Map saved successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error saving the map: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method checks whether the map is valid or not.
     * It checks conditions like if graph is empty, if it has any self loops/multiple edges, if every continent has at least 1 country, etc.
     *
     * @param p_graphMap The Map object
     * @return A boolean value - True if map is valid, otherwise false
     * @author Anuja-Somthankar
     */
    @Override
    public Boolean isValidMap(Map p_graphMap) {
        if (p_graphMap == null) {
            System.out.println("Graph is Empty");
            return false;
        }

        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = p_graphMap.getD_continentMapGraph();
        if (l_continentMapGraph.vertexSet().isEmpty()) {
            System.out.println("Continent Graph is Empty");
            return false;
        }

        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_graphMap.getD_countryMapGraph();
        if (GraphTests.isEmpty(l_countryMapGraph)) {
            System.out.println("Country Graph is Empty");
            return false;
        }

        for (Continent l_continent : l_continentMapGraph.vertexSet()) {
            if (l_continent.getD_countryList() == null) {
                System.out.println("Continent " + l_continent.getD_continentID() + " doesnt have countries");
                return false;
            }
        }

        for (Country l_country : l_countryMapGraph.vertexSet()) {
            if (l_country.getD_neighbourCountryIDList() == null) {
                System.out.println("Country " + l_country.getD_countryID() + " doesnt have neighbours");
                return false;
            }
        }

        for (Country l_i : l_countryMapGraph.vertexSet()) {
            List<Integer> l_neighbourList = l_i.getD_neighbourCountryIDList();
            for (int l_j : l_neighbourList) {
                Country l_country = p_graphMap.getD_countryByID(l_j);
                if (!l_countryMapGraph.containsEdge(l_country, l_i)) {
                    System.out.println(l_i.getD_countryID() + " is a neighbour to " + l_j +
                            " but " + l_j + " is not specified as a neighbour to " + l_i.getD_countryID());
                    return false;
                }
            }
        }

        if (!GraphTests.isSimple(l_continentMapGraph)) {
            System.out.println("Continent Graph is not weakly connected or it has self loops/multiple edges.");
            System.out.println("Reasons for the following could be either a duplicate continent or some continent is not connected with others.");
            System.out.println("Also possible that some continent has itself as a neighbour or some neighbour is stated more than once for a continent.");
            return false;
        }

        if (!GraphTests.isWeaklyConnected(l_countryMapGraph) || !GraphTests.isSimple(l_countryMapGraph)) {
            System.out.println("Country Graph is not weakly connected  or it has self loops/multiple edges.");
            System.out.println("Reasons for the following could be either a duplicate country or some country is not connected with others.");
            System.out.println("Also possible that some country has itself as a neighbour or some neighbour is stated more than once for a country.");
            return false;
        }

        System.out.println("Map is Valid!");
        return true;
    }

    /**
     * This method displays the map, i.e., it shows all continents and countries and their respective neighbors
     *
     * @param p_graphMap Object of the Map graph
     * @author Anuja-Somthankar
     */
    @Override
    public void showMap(Map p_graphMap) {
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = p_graphMap.getD_continentMapGraph();
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_graphMap.getD_countryMapGraph();

        System.out.println("List of continents: ");
        for (Continent l_continent : l_continentMapGraph.vertexSet()) {
            System.out.println(l_continent.getD_continentID() + " " + l_continent.getD_continentValue());
        }

        System.out.println("List of countries and their neighbours: ");
        for (Country l_country : l_countryMapGraph.vertexSet()) {
            System.out.println(l_country.getD_countryID() + " " + l_country.getD_neighbourCountryIDList());
        }
    }

    /**
     * @param p_map            {@link models.Map}
     * @param p_continentID    Continent ID of the new continent
     * @param p_continentValue Continent Value of the new continent
     */
    @Override
    public void addContinent(Map p_map, int p_continentID, int p_continentValue) {
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = p_map.getD_continentMapGraph();
        Continent l_continent = new Continent();
        l_continent.setD_continentID(p_continentID);
        l_continent.setD_continentValue(p_continentValue);
        l_continentMapGraph.addVertex(l_continent);
    }

    /**
     * @param p_map         {@link models.Map}
     * @param p_continentID Continent ID of the continent to be deleted
     */
    @Override
    public void removeContinent(Map p_map, int p_continentID) {
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentMapGraph = p_map.getD_continentMapGraph();
        Continent l_continent = p_map.getD_continentByID(p_continentID);
        l_continentMapGraph.removeVertex(l_continent);
    }

    /**
     * @param p_map         {@link models.Map}
     * @param p_countryID   Country ID of the new country
     * @param p_continentID Continent ID of the continent of which the country belongs to
     */
    @Override
    public void addCountry(Map p_map, int p_countryID, int p_continentID) {
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_map.getD_countryMapGraph();
        Continent l_continent = p_map.getD_continentByID(p_continentID);

        Country l_country = new Country();
        l_country.setD_countryID(p_countryID);
        l_country.setD_continentID(p_continentID);
        l_countryMapGraph.addVertex(l_country);
        l_continent.addCountry(l_country);
    }

    /**
     * @param p_map       {@link models.Map}
     * @param p_countryID Country ID of the country to be deleted
     */
    @Override
    public void removeCountry(Map p_map, int p_countryID) {
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_map.getD_countryMapGraph();
        Country l_country = p_map.getD_countryByID(p_countryID);
        l_countryMapGraph.removeVertex(l_country);
    }

    /**
     * @param p_map                {@link models.Map}
     * @param p_countryID          Country ID of the source country vertex
     * @param p_neighbourCountryID Country ID of the neighbouring country vertex
     */
    @Override
    public void addNeighbour(Map p_map, int p_countryID, int p_neighbourCountryID) {
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_map.getD_countryMapGraph();
        Country l_country = p_map.getD_countryByID(p_countryID);
        Country l_neighbourCountry = p_map.getD_countryByID(p_neighbourCountryID);
        l_countryMapGraph.addEdge(l_country, l_neighbourCountry);
    }

    /**
     * @param p_map                {@link models.Map}
     * @param p_countryID          Country ID of the source country vertex
     * @param p_neighbourCountryID Country ID of the neighbouring country vertex
     */
    @Override
    public void removeNeighbour(Map p_map, int p_countryID, int p_neighbourCountryID) {
        DefaultDirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_map.getD_countryMapGraph();
        Country l_country = p_map.getD_countryByID(p_countryID);
        Country l_neighbourCountry = p_map.getD_countryByID(p_neighbourCountryID);
        l_countryMapGraph.removeEdge(l_country, l_neighbourCountry);
    }
}
