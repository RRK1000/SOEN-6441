package util;

import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapUtil implements IMapUtil{
    public Map d_map;

    /**
     * Loads the map from a given file.
     * @author Rishi Ravikumar
     * @param p_filename The name of the file to load the map from.
     */
    @Override
    public void loadMap(String p_filename) {
        DirectedGraph<Continent, DefaultEdge>  l_continentMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);;
        DirectedGraph<Country, DefaultEdge>  l_countryMapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);;

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
                    while(!line.isEmpty()){
                        String[] continentData = line.split(" ");
                        Continent l_continent = new Continent();
                        l_continent.setContinentID(l_continentID++);
                        l_continent.setContinentValue(Integer.parseInt(continentData[1]));
                        l_continentMapGraph.addVertex(l_continent);
                        line = reader.readLine();
                    }
                    continue;
                }

                if (line.startsWith("[countries]")) {
                    line = reader.readLine();

                    while (!line.isEmpty()) {
                        String[] countryData = line.split(" ");
                        Country l_country = new Country();
                        Continent l_continent = d_map.getD_continentByID(Integer.parseInt(countryData[2]));
                        l_country.setCountryID(Integer.parseInt(countryData[0]));
                        l_countryMapGraph.addVertex(l_country);
                        l_continent.addCountry(l_country);
                        line = reader.readLine();
                    }
                    d_map.setD_countryMapGraph(l_countryMapGraph);
                    continue;
                }

                if (line.startsWith("[borders]")) {
                    line = reader.readLine();

                    while(line != null) {
                        String[] borderData = line.split(" ");
                        Country l_currentCountry = d_map.getD_countryByID(Integer.parseInt(borderData[0]));

                        List<Integer> l_neighbourhoodCountryIDList = new ArrayList<>();
                        for (int l_id = 1; l_id < borderData.length; l_id++) {
                            l_neighbourhoodCountryIDList.add(Integer.valueOf(borderData[l_id]));
                            l_countryMapGraph.addEdge(l_currentCountry, d_map.getD_countryByID(l_id));
                        }
                        l_currentCountry.setD_neighbourCountryIDList(l_neighbourhoodCountryIDList);
                        line = reader.readLine();
                    }
                }
            }
            d_map.setD_continentMapGraph(l_continentMapGraph);
            d_map.setD_countryMapGraph(l_countryMapGraph);
            System.out.println("Map loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading the map: " + e.getMessage());
        }
    }

    /**
     *
     */
    @Override
    public void editMap() {

    }

    /**
     *
     */
    @Override
    public void saveMap() {
    }

    /**
     *This method checks whether the map is valid or not.
     * @param p_graphMap The Map object
     * @return A boolean value - True if map is valid, otherwise false
     * @author Anuja-Somthankar
     */
    @Override
    public Boolean validateMap(Map p_graphMap) {
        if(p_graphMap == null){
            System.out.println("Graph is Empty");
            return false;
        }

        DirectedGraph<Continent, DefaultEdge> l_continentMapGraph = p_graphMap.getD_continentMapGraph();
        if(GraphTests.isEmpty(l_continentMapGraph)){
            System.out.println("Continent Graph is Empty");
            return false;
        }

        DirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_graphMap.getD_countryMapGraph();
        if(GraphTests.isEmpty(l_countryMapGraph)){
            System.out.println("Country Graph is Empty");
            return false;
        }

        for (Continent l_continent : l_continentMapGraph.vertexSet()) {
            if(l_continent.getCountries().isEmpty()){
                System.out.println("Continent doesnt have countries");
                return false;
            }
        }

        if(GraphTests.isStronglyConnected(l_continentMapGraph) && GraphTests.isSimple(l_continentMapGraph)){
            System.out.println("Continent Graph is not strongly connected or it has self loops");
            return false;
        }

        if(GraphTests.isStronglyConnected(l_countryMapGraph) && GraphTests.isSimple(l_countryMapGraph)){
            System.out.println("Continent Graph is not strongly connected  or it has self loops");
            return false;
        }

        for (Country l_country : l_countryMapGraph.vertexSet()) {
            if(l_country.getD_neighbourCountryIDList().isEmpty()){
                System.out.println("Country doesnt have neighbours");
                return false;
            }
        }

        return true;
    }

    /**
     * This method displays the map, i.e., it shows all continents and countries and their respective neighbors
     * @param p_graphMap Object of the Map graph
     * @author Anuja-Somthankar
     */
    @Override
    public void showMap(Map p_graphMap) {
        DirectedGraph<Continent, DefaultEdge> l_continentMapGraph = p_graphMap.getD_continentMapGraph();
        DirectedGraph<Country, DefaultEdge> l_countryMapGraph = p_graphMap.getD_countryMapGraph();

        System.out.println("List of continents: ");
        for (Continent l_continent : l_continentMapGraph.vertexSet()){
            System.out.println(l_continent.getContinentID() + " " + l_continent.getContinentValue());
        }

        System.out.println("List of countries and their neighbours: ");
        for (Country l_country : l_countryMapGraph.vertexSet()){
            System.out.println(l_country.getCountryID() + " " + l_country.getD_neighbourCountryIDList());
        }

    }
}
