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
        for (Continent continent : d_continents) {
            System.out.println("Continent: " + continent.getName());
            for (Country country : continent.getCountries()) {
                System.out.println("  Country: " + country.getName());
            }
        }
    }

    /**
     * Saves the map to a file.
     */
    
    public void saveMap() {
        System.out.println("Saving the map...");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("map_data.txt"))) {
            for (Continent continent : d_continents) {
                writer.write("Continent: " + continent.getName() + "\n");
                for (Country country : continent.getCountries()) {
                    writer.write("  Country: " + country.getName() + "\n");
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
        
        try (BufferedReader reader = new BufferedReader(new FileReader(p_filename))) {
            String line;
            Continent currentContinent = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Continent: ")) {
                	
                    String continentName = line.substring(11); // Extract continent name
                    currentContinent = new Continent();
                    currentContinent.setName(continentName);
                    d_continents.add(currentContinent);
                    
                } else if (line.startsWith("  Country: ") && currentContinent != null) {
                	
                    String countryName = line.substring(11); // Extract country name
                    Country country = new Country();
                    country.setName(countryName);
                    currentContinent.getCountries().add(country);
                    d_countries.add(country);
                    
                }
            }
            System.out.println("Map loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading the map: " + e.getMessage());
        }
    }
    
    

}
