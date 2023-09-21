package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private List<Country> d_countries;
    private List<Continent> d_continents;

    public Map() {
        d_countries = new ArrayList<>();
        d_continents = new ArrayList<>();
    }

    /**
     * Shows the map.
     */
    public void showMap() {
        System.out.println("Showing the map...");
        for (Continent l_continent : d_continents) {
            System.out.println("Continent: " + l_continent.getName());
            for (Country l_country : l_continent.getCountries()) {
                System.out.println("  Country: " + l_country.getName());
            }
        }
    }

    /**
     * Saves the map to a file.
     */
    
    public void saveMap() {
        System.out.println("Saving the map...");
        
        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter("map_data.txt"))) {
            for (Continent l_continent : d_continents) {
                l_writer.write("Continent: " + l_continent.getName() + "\n");
                for (Country l_country : l_continent.getCountries()) {
                    l_writer.write("  Country: " + l_country.getName() + "\n");
                }
            }
            System.out.println("Map saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the map: " + e.getMessage());
        }
    }

    /**
     * Loads the map from a given file.
     * @param p_filename The name of the file to load the map from.
     */
    public void loadMap(String p_filename) {
        System.out.println("Loading the map from " + p_filename + "...");
        
        try (BufferedReader l_reader = new BufferedReader(new FileReader(p_filename))) {
            String l_line;
            Continent l_currentContinent = null;
            while ((l_line = l_reader.readLine()) != null) {
                if (l_line.startsWith("Continent: ")) {
                	
                    String l_continentName = l_line.substring(11); // Extract continent name
                    l_currentContinent = new Continent();
                    l_currentContinent.setName(l_continentName);
                    d_continents.add(l_currentContinent);
                    
                } else if (l_line.startsWith("  Country: ") && l_currentContinent != null) {
                	
                    String l_countryName = l_line.substring(11); // Extract country name
                    Country l_country = new Country();
                    l_country.setName(l_countryName);
                    l_currentContinent.getCountries().add(l_country);
                    d_countries.add(l_country);
                    
                }
            }
            System.out.println("Map loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading the map: " + e.getMessage());
        }
    }
    
    

}
