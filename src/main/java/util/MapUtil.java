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
    public Map map;

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
                    while(line != null) {
                        String[] countryData = line.split(" ");
                        Country l_country = new Country();
                        l_country.setCountryID(Integer.parseInt(countryData[0]));

                        List<Integer> l_neighbourhoodCountryList = new ArrayList<>();
                        for (int l_i = 1; l_i < countryData.length; l_i++)
                            l_neighbourhoodCountryList.add(Integer.valueOf(countryData[l_i]));
                        l_country.setD_neighborhoodCountryIDList(l_neighbourhoodCountryList);
                        l_countryMapGraph.addVertex(l_country);
                        line = reader.readLine();
                    }
                }
            }

            map.setD_continentMapGraph(l_continentMapGraph);
            map.setD_countryMapGraph(l_countryMapGraph);

            System.out.println("Continent Graph:\n"+map.getD_continentMapGraph().vertexSet());
            System.out.println("Country Graph:\n"+map.getD_countryMapGraph().vertexSet());

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
