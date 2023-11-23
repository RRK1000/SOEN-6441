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

//	    if (!MapUtil.isValidMap(dominationMap)) {
//	        throw new IOException("Converted Domination map is invalid.");
//	    }

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
		}

		for (Country l_conquestCountry : p_map.getD_countryMapGraph().vertexSet()) {
			Country l_dominationCountry = new Country();
			l_dominationCountry.setD_countryID(l_conquestCountry.getD_countryID());
			l_dominationCountry.setD_countryName(l_conquestCountry.getD_countryName());
			l_dominationCountry.setD_continentID(l_conquestCountry.getD_continentID());
			l_tempCountries.put(l_conquestCountry.getD_countryID(), l_dominationCountry);
			l_tempBorders.put(l_dominationCountry.getD_countryID(), new ArrayList<>());
		}

		// Neighbors copying
		for (Country l_conquestCountry : p_map.getD_countryMapGraph().vertexSet()) {
			List<Integer> l_neighbors = getNeighborsForCountry(l_conquestCountry, p_map);
			l_tempBorders.put(l_conquestCountry.getD_countryID(), l_neighbors);
		//	System.out.println("Country: " + l_conquestCountry.getD_countryName() + " (ID: "
		//			+ l_conquestCountry.getD_countryID() + ")");
		//	System.out.println("Neighbors: " + l_neighbors);
		}

		// Create a new Map object and set relationships
		Map l_newMap = new Map();
		l_newMap.setD_continentMapGraph(new DefaultDirectedGraph<>(DefaultEdge.class));
		l_newMap.setD_countryMapGraph(new DefaultDirectedGraph<>(DefaultEdge.class));

		for (Continent l_continent : l_tempContinents.values()) {
			l_newMap.getD_continentMapGraph().addVertex(l_continent);
		}

		for (Country l_country : l_tempCountries.values()) {
			l_newMap.getD_countryMapGraph().addVertex(l_country);
		}

		for (java.util.Map.Entry<Integer, List<Integer>> entry : l_tempBorders.entrySet()) {
			Country l_country = l_tempCountries.get(entry.getKey());
			List<Integer> l_neighbors = entry.getValue();

			l_country.setD_neighbourCountryIDList(l_neighbors);

			for (Integer neighborID : l_neighbors) {
				Country l_neighborCountry = l_tempCountries.get(neighborID);
				l_newMap.getD_countryMapGraph().addEdge(l_country, l_neighborCountry);
			}
		}

		return l_newMap;
	}
	/**
	 * Gets neighbors for a country object
	 *
	 * @param p_map The Map object to be saved
	 * @param p_country The Country data to be loaded 
	 * @return a list of neighbors to convert to Domination format
	 */
	private List<Integer> getNeighborsForCountry(Country p_country, Map p_map) {
		List<Integer> l_neighbors = new ArrayList<>();
		// Iterate over the edges of the country to find its neighbors
		for (DefaultEdge l_edge : p_map.getD_countryMapGraph().edgesOf(p_country)) {
			int l_neighborID = p_map.getD_countryMapGraph().getEdgeTarget(l_edge).getD_countryID();
			if (l_neighborID != p_country.getD_countryID()) {
				l_neighbors.add(l_neighborID);
			}
		}
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
		HashMap<String, Country> l_countryMap = new HashMap<>();
		HashMap<String, Integer> l_countryNameIdMap = new HashMap<>();
		int l_continentID = 1;

		try (BufferedReader l_reader = new BufferedReader(new FileReader(p_fileName))) {
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
					l_continent.setD_continentID(l_continentID++);
					l_continent.setD_continentName(l_parts[0].trim());
					l_continent.setD_continentValue(Integer.parseInt(l_parts[1].trim()));
					l_continentMapGraph.addVertex(l_continent);
					l_continentMap.put(l_parts[0].trim(), l_continent);
				} else if (l_isTerritoriesSection && !l_line.isEmpty()) {
					String[] l_parts = l_line.split(",");
					Continent l_continent = l_continentMap.get(l_parts[3].trim());
					if (l_continent == null) {
						throw new IOException("Continent not found for a territory in the map file.");
					}
					Country l_country = new Country();
					int countryId = Integer.parseInt(l_parts[1].trim());
					l_country.setD_countryID(countryId);
					l_country.setD_countryName(l_parts[0].trim());
					l_country.setD_continentID(l_continent.getD_continentID());
					l_countryMapGraph.addVertex(l_country);
					l_countryMap.put(l_parts[0].trim(), l_country);
					l_countryNameIdMap.put(l_parts[0].trim(), countryId);
				}
			}
		}

		try (BufferedReader l_reader2 = new BufferedReader(new FileReader(p_fileName))) {
			String l_line;
			boolean l_isTerritoriesSection2 = false;

			while ((l_line = l_reader2.readLine()) != null) {
				if (l_line.trim().equals("[Territories]")) {
					l_isTerritoriesSection2 = true;
					continue;
				}

				if (l_isTerritoriesSection2 && !l_line.isEmpty()) {
					String[] l_parts = l_line.split(",");
					Country l_country = l_countryMap.get(l_parts[0].trim());
					if (l_country != null) {
						for (int i = 4; i < l_parts.length; i++) {
							String neighborName = l_parts[i].trim();
							// System.out.println("neighborNAme is "+ neighborName);
							Integer neighborId = l_countryNameIdMap.get(neighborName);
							// System.out.println("neighborId is "+ neighborId);

							if (neighborId != null) {
								Country neighborCountry = l_countryMap.get(neighborName);
								// System.out.println("Neighbor for " + l_country.getD_countryName() + " is " +
								// neighborCountry.getD_countryName() + " (ID: " +
								// neighborCountry.getD_countryID() + ")");

								l_countryMapGraph.addEdge(l_country, neighborCountry);
							}
						}
					}
				}
			}
		}

		l_conquestMap.setD_continentMapGraph(l_continentMapGraph);
		l_conquestMap.setD_countryMapGraph(l_countryMapGraph);
		return l_conquestMap;
	}

	/**
	 * Saves a map to a file in Conquest format
	 *
	 * @param p_map      The Map object to be saved
	 * @param p_fileName The file name to save the map to
	 * @return true if map is saved successfully, false otherwise
	 * @throws IOException if there is an issue in file writing
	 */
	
	@Override
	public boolean saveMap(Map p_map, String p_fileName) throws IOException {
		
		 if (!p_fileName.endsWith(".conquest")) {
		        p_fileName += ".conquest";
		    }
		
	    try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(p_fileName))) {
	
	        l_writer.write("[Continents]\n");
	        for (Continent l_continent : p_map.getD_continentMapGraph().vertexSet()) {
	            l_writer.write(l_continent.getD_continentName() + "=" + l_continent.getD_continentValue() + "\n");
	        }
	        l_writer.write("\n");


	        l_writer.write("[Territories]\n");
	        for (Country l_country : p_map.getD_countryMapGraph().vertexSet()) {
	            Continent l_continent = p_map.getD_continentByID(l_country.getD_continentID());
	            String continentName = l_continent.getD_continentName();


	            l_writer.write(l_country.getD_countryName() + "," + l_country.getD_countryID() + "," + continentName);

	            for (Integer neighborID : l_country.getD_neighbourCountryIDList()) {
	                Country l_neighborCountry = p_map.getD_countryByID(neighborID);
	                l_writer.write("," + l_neighborCountry.getD_countryName());
	            }

	            l_writer.write("\n");
	        }
	    }
	    return true;
	}
	

   


}