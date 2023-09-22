package util;

import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Responsible for map utility functions (loading/saving/editing a map)
 *
 * This class implements the IMapUtil interface
 *
 * @author Rishi Ravikumar, Anuja-Somthankar
 */
import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultEdge;

public class MapUtil implements IMapUtil{
    public Map d_map;

    /**
     * Loads the map from a given file.
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
                if (line.startsWith("[Continent]")) {
                    line = reader.readLine();
                    while(!line.startsWith("[")){
                        String[] continentData = line.split(" ");
                        Continent l_continent = new Continent();
                        l_continent.setContinentID(Integer.parseInt(continentData[0]));
                        l_continent.setContinentValue(Integer.parseInt(continentData[1]));
                        l_continentMapGraph.addVertex(l_continent);
                        line = reader.readLine();
                    }
                }

                if (line.startsWith("[Country]")) {
                    line = reader.readLine();

                    // adding country vertices
                    while(line != null) {
                        String[] countryData = line.split(" ");
                        Country l_country = new Country();
                        l_country.setCountryID(Integer.parseInt(countryData[0]));
                        l_countryMapGraph.addVertex(l_country);

                        List<Integer> l_neighbourhoodCountryList = new ArrayList<>();
                        for (int l_i = 1; l_i < countryData.length; l_i++)
                            l_neighbourhoodCountryList.add(Integer.valueOf(countryData[l_i]));

                        l_country.setD_neighbourCountryIDList(l_neighbourhoodCountryList);
                        line = reader.readLine();
                    }
                    d_map.setD_countryMapGraph(l_countryMapGraph);

                    // adding graph edges
                    for (Country c: l_countryMapGraph.vertexSet()) {
                        List<Integer> d_neighbourCountryIDList = c.getD_neighbourCountryIDList();
                        for (Integer l_id: d_neighbourCountryIDList) {
                            l_countryMapGraph.addEdge(c, d_map.getD_countryByID(l_id));
                        }
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

        Set l_vertices = l_continentMapGraph.vertexSet();
        for (Object l_vertex : l_vertices) {
            Continent l_vertexInt = (Continent)l_vertex;
            if(l_vertexInt.getCountries().isEmpty()){
                System.out.println("Continent doesnt have countries");
                return false;
            }
        }

        if(GraphTests.isStronglyConnected(l_continentMapGraph) || GraphTests.isSimple(l_continentMapGraph)){
            System.out.println("Continent Graph is not strongly connected or it has self loops");
            return false;
        }

        if(GraphTests.isStronglyConnected(l_countryMapGraph) || GraphTests.isSimple(l_countryMapGraph)){
            System.out.println("Continent Graph is not strongly connected  or it has self loops");
            return false;
        }

        Set l_countries = l_countryMapGraph.vertexSet();
        for (Object l_vertex : l_vertices) {
            Country l_vertexInt = (Country)l_vertex;
            if(l_vertexInt.getD_neighbourCountryIDList().isEmpty()){
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
