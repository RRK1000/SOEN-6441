package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import models.Continent;
import models.Country;
import models.Map;

/**
 * Adapter class for reading and writing maps in Conquest file format
 * 
 * @author Yusuke Ishii
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

        Map l_conquestMap = loadConquestMap(p_fileName);
        if (l_conquestMap == null) {
            throw new IOException("Failed to load Conquest map format.");
        }

        Map l_dominationMap = convertToDominationFormat(l_conquestMap);

        if (!MapUtil.isValidMap(l_dominationMap)) {
            System.out.println("Map is invalid");
        }
//
//      
//        System.out.println("=== Converted Domination Map ===");
//        System.out.println("[continents]");
//        l_dominationMap.getD_continentMapGraph().vertexSet().forEach(continent -> {
//            System.out.println(continent.getD_continentName() + " " + continent.getD_continentValue());
//        });
//
//        System.out.println("\n[countries]");
//        l_dominationMap.getD_countryMapGraph().vertexSet().forEach(country -> {
//            Continent l_continent = l_dominationMap.getD_continentByID(country.getD_continentID());
//            System.out.println(
//                    country.getD_countryID() + " " + country.getD_countryName() + " " + l_continent.getD_continentID()
//                            + " " + country.getD_xCoordinate() + " " + country.getD_yCoordinate());
//        });
//
//        System.out.println("\n[borders]");
//        l_dominationMap.getD_countryMapGraph().vertexSet().forEach(country -> {
//            System.out.print(country.getD_countryID());
//            for (Integer l_neighborID : country.getD_neighbourCountryIDList()) {
//                System.out.print(" " + l_neighborID);
//            }
//            System.out.println();
//        });

        return l_dominationMap;
    }

	/**
	 * Converts a map from Conquest format to Domination format
	 *
	 * @param p_map Map object in Conquest format
	 * @return Map object in Domination format
	 */
	public Map convertToDominationFormat(Map p_map) {
	    HashMap<Integer, Continent> l_tempContinents = new HashMap<>();
	    HashMap<Integer, Country> l_tempCountries = new HashMap<>();
	    HashMap<Integer, List<Integer>> l_tempBorders = new HashMap<>();

	    // Continents and countries copying
	    for (Continent l_conquestContinent : p_map.getD_continentMapGraph().vertexSet()) {
	        Continent l_dominationContinent = new Continent();
	        l_dominationContinent.setD_continentID(l_conquestContinent.getD_continentID());
	        l_dominationContinent.setD_continentName(l_conquestContinent.getD_continentName());
	        l_dominationContinent.setD_continentValue(l_conquestContinent.getD_continentValue());
	        l_tempContinents.put(l_conquestContinent.getD_continentID(), l_dominationContinent);
	        System.out.println("Continent " + l_dominationContinent.getD_continentName() + " includes countries:");
	        for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
	            if (l_country.getD_continentID() == l_dominationContinent.getD_continentID()) {
	                System.out.println(" - " + l_country.getD_countryName());
	            }
	        }
	    }

	    for (Country l_conquestCountry : p_map.getD_countryMapGraph().vertexSet()) {
	        Country l_dominationCountry = new Country();
	        l_dominationCountry.setD_countryID(l_conquestCountry.getD_countryID());
	        l_dominationCountry.setD_countryName(l_conquestCountry.getD_countryName());
	        l_dominationCountry.setD_continentID(l_conquestCountry.getD_continentID());
	        l_dominationCountry.setD_xCoordinate(l_conquestCountry.getD_xCoordinate());
	        l_dominationCountry.setD_yCoordinate(l_conquestCountry.getD_yCoordinate());
	        l_tempCountries.put(l_conquestCountry.getD_countryID(), l_dominationCountry);
	        l_tempBorders.put(l_dominationCountry.getD_countryID(), l_conquestCountry.getD_neighbourCountryIDList());
	        Continent l_continent = l_tempContinents.get(l_conquestCountry.getD_continentID());
	        System.out.println("Country " + l_conquestCountry.getD_countryName() + " belongs to continent " + l_continent.getD_continentName());
	    }

	    // Neighbors copying
	    for (Country l_conquestCountry : p_map.getD_countryMapGraph().vertexSet()) {
	        List<Integer> l_neighbors = getNeighborsForCountry(l_conquestCountry, p_map, l_tempBorders);
	        Country l_dominationCountry = l_tempCountries.get(l_conquestCountry.getD_countryID());
	        System.out.println("Setting neighbors for " + l_dominationCountry.getD_countryName() + " (ID: " + l_dominationCountry.getD_countryID() + ")");
	        for (Integer l_neighborID : l_neighbors) {
	            System.out.print(l_neighborID + ", ");
	        }
	        System.out.println();
	        l_dominationCountry.setD_neighbourCountryIDList(l_neighbors);
	    }

	    // Create a new Map object and set relationships
	    Map l_newMap = new Map();
	    l_newMap.setD_continentMapGraph(new DefaultDirectedGraph<>(DefaultEdge.class));
	    l_newMap.setD_countryMapGraph(new DefaultDirectedGraph<>(DefaultEdge.class));

	    // Add vertices to the continent and country graphs
	    for (Continent l_continent : l_tempContinents.values()) {
	        l_newMap.getD_continentMapGraph().addVertex(l_continent);
	    }
	    for (Country l_country : l_tempCountries.values()) {
	        l_newMap.getD_countryMapGraph().addVertex(l_country);
	    }

	    // Adding edges between countries based on neighboring relationships
	    for (java.util.Map.Entry<Integer, List<Integer>> entry : l_tempBorders.entrySet()) {
	        Country l_country = l_tempCountries.get(entry.getKey());
	        for (Integer l_neighborID : entry.getValue()) {
	            Country l_neighborCountry = l_tempCountries.get(l_neighborID);
	            l_newMap.getD_countryMapGraph().addEdge(l_country, l_neighborCountry);
	            System.out.println("Added edge between countries: " + l_country.getD_countryName() + " and " + l_neighborCountry.getD_countryName());
	        }
	    }

	    // Adding edges between continents if their countries are neighbors
	    for (Country l_country : l_tempCountries.values()) {
	        Continent l_continent = l_tempContinents.get(l_country.getD_continentID());
	        for (Integer l_neighborID : l_country.getD_neighbourCountryIDList()) {
	            Country l_neighborCountry = l_tempCountries.get(l_neighborID);
	            Continent l_neighborContinent = l_tempContinents.get(l_neighborCountry.getD_continentID());
	            if (!l_continent.equals(l_neighborContinent)) {
	                l_newMap.getD_continentMapGraph().addEdge(l_continent, l_neighborContinent);
	                System.out.println("Added edge between continents: " + l_continent.getD_continentName() + " and " + l_neighborContinent.getD_continentName());
	            }
	        }
	    }

	    return l_newMap;
	}


	/**
	 * Gets neighbors for a country object
	 *
	 * @param p_map     The Map object to be saved
	 * @param p_country The Country data to be loaded
	 * @return a list of neighbors to convert to Domination format
	 */
	private List<Integer> getNeighborsForCountry(Country p_country, Map p_map,
			HashMap<Integer, List<Integer>> l_tempBorders) {
		List<Integer> l_neighbors = l_tempBorders.getOrDefault(p_country.getD_countryID(), new ArrayList<>());

		System.out.println(
				"Getting neighbors for " + p_country.getD_countryName() + " (ID: " + p_country.getD_countryID() + ")");
		System.out.println("Neighbors found for " + p_country.getD_countryName() + " (ID: " + p_country.getD_countryID()
				+ "): " + l_neighbors);

		return l_neighbors;
	}

	/**
	 * Loads a map from a file in Conquest format
	 *
	 * @param p_fileName Name of the file to be read
	 * @return Map object loaded from the file
	 * @throws IOException if there is an issue in file reading
	 */
	public Map loadConquestMap(String p_fileName) throws IOException {
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
					l_country.setD_countryID(l_countryID++); // 国IDは行数に基づいて自動割り当て
					l_country.setD_countryName(l_parts[0].trim());
					l_country.setD_continentID(l_continentIDForCountry);
					l_country.setD_xCoordinate(Integer.parseInt(l_parts[1].trim())); // X座標
					l_country.setD_yCoordinate(Integer.parseInt(l_parts[2].trim())); // Y座標
					l_countryMapGraph.addVertex(l_country);
					l_countryMap.put(l_parts[0].trim(), l_country);
					l_countryNameIdMap.put(l_parts[0].trim(), l_countryID - 1); // 国名とIDの関連付け
				}
			}
		}

		// Load neighboring relationships
		try (BufferedReader l_reader3 = new BufferedReader(new FileReader("src/main/resources/" +p_fileName))) {
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
						System.out.println("Processing country: " + l_country.getD_countryName());
						for (int i = 4; i < l_parts.length; i++) {
							String l_neighborName = l_parts[i].trim();
							Integer l_neighborId = l_countryNameIdMap.get(l_neighborName);
							System.out.println("Neighbor name: " + l_neighborName + ", ID: " + l_neighborId);
							if (l_neighborId != null) {
								l_neighbors.add(l_neighborId);
							}
						}
						l_country.setD_neighbourCountryIDList(l_neighbors);
						System.out.println("Neighbors for " + l_country.getD_countryName() + ": " + l_neighbors);
					}
				}
			}
		}

		l_conquestMap.setD_continentMapGraph(l_continentMapGraph);
		l_conquestMap.setD_countryMapGraph(l_countryMapGraph);
		return l_conquestMap;
	}

	/**
	 * Saves a domination map to a file in Conquest format
	 *
	 * @param p_map      The Map object to be saved
	 * @param p_fileName The file name to save the map to
	 * @return true if map is saved successfully, false otherwise
	 * @throws IOException if there is an issue in file writing
	 */
	@Override
	public boolean saveMap(Map p_map, String p_fileName) throws IOException {
	    String l_modifiedFileName = p_fileName;

	
		if (p_fileName.endsWith(".domination")) {
			
	        l_modifiedFileName = p_fileName.substring(0, p_fileName.length() - 11) + ".conquest";

			p_fileName = p_fileName.substring(0, p_fileName.length() - 11) + ".conquest";
		}

		else if (!p_fileName.endsWith(".conquest")) {
			p_fileName += ".conquest";
		}

	    String l_fullPath = "src/main/resources/" + l_modifiedFileName;

		
        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(l_fullPath))) {

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
			System.out.println("Error saving the map: " + e.getMessage());
			return false;
		}

        ConquestMapFileReaderAdapter l_Map = new ConquestMapFileReaderAdapter();
        Map l_loadedMap = l_Map.loadMap(l_modifiedFileName); 
        if (!MapUtil.isValidMap(l_loadedMap)) {
            System.out.println("Map is invalid, check the file.");
            return false;
        }
        System.out.println("Map has been saved to :" + l_modifiedFileName);
        return true;
	}

}