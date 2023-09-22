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

/**
 * Responsible for map utility functions (loading/saving/editing a map)
 *
 * This class implements the IMapUtil interface
 *
 * @author Rishi Ravikumar
 */
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
     *
     */
    @Override
    public void validateMap() {

    }

    /**
     *
     */
    @Override
    public void showMap() {

    }
}
