package util;

import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the test methods for MapUtil.java. The tested functions
 * are loadMap, validateMap and continent related functionalities.
 *
 * @author Anuja-Somthankar Rishi Ravikumar Yusuke Ishii
 */
class MapUtilTest {
	private Map d_map;
	private Continent d_continent1, d_continent2;
	private Country d_country1, d_country2, d_country3, d_country4;

	@BeforeEach
	void setUp() {
		// ContinentTest setup
		d_map = new Map();

		d_continent1 = new Continent(1, 5);
		d_continent2 = new Continent(2, 5);

		d_country1 = new Country(1, Arrays.asList(2), 10);
		d_country2 = new Country(2, Arrays.asList(1, 3), 10);
		d_country3 = new Country(3, Arrays.asList(2, 4), 10);
		d_country4 = new Country(4, Arrays.asList(3), 10);

		d_continent1.addCountry(d_country1);
		d_continent1.addCountry(d_country2);
		d_continent2.addCountry(d_country3);
		d_continent2.addCountry(d_country4);

		d_map.getD_countryMapGraph().addVertex(d_country1);
		d_map.getD_countryMapGraph().addVertex(d_country2);
		d_map.getD_countryMapGraph().addVertex(d_country3);
		d_map.getD_countryMapGraph().addVertex(d_country4);

		d_map.getD_continentMapGraph().addVertex(d_continent1);
		d_map.getD_continentMapGraph().addVertex(d_continent2);

		// Add edges based on neighboring country IDs
		addEdgesToGraph(d_map.getD_countryMapGraph(), d_country1);
		addEdgesToGraph(d_map.getD_countryMapGraph(), d_country2);
		addEdgesToGraph(d_map.getD_countryMapGraph(), d_country3);
		addEdgesToGraph(d_map.getD_countryMapGraph(), d_country4);
	}

	/**
	 * Adds edges to the graph based on neighboring country IDs.
	 * 
	 * @param p_graph   The graph to which edges are added.
	 * @param p_country The country for which edges are added.
	 */
	private void addEdgesToGraph(Graph<Country, DefaultEdge> p_graph, Country p_country) {
		for (Integer l_neighborId : p_country.getD_neighbourCountryIDList()) {
			Country l_neighbor = findCountryById(l_neighborId);
			if (l_neighbor != null && !p_graph.containsEdge(p_country, l_neighbor)) {
				p_graph.addEdge(p_country, l_neighbor);
			}
		}
	}

	/**
	 * Finds a country by its ID.
	 * 
	 * @param p_id The ID of the country.
	 * @return The country object if found, null otherwise.
	 */
	private Country findCountryById(Integer p_id) {
		if (d_country1.getD_countryID() == p_id)
			return d_country1;
		if (d_country2.getD_countryID() == p_id)
			return d_country2;
		if (d_country3.getD_countryID() == p_id)
			return d_country3;
		if (d_country4.getD_countryID() == p_id)
			return d_country4;
		return null;
	}

	/**
	 * Tests if there are edges between countries in continent1.
	 */
	@Test
	public void testEdgesBetweenCountriesInContinent1() {
		assertTrue(d_map.getD_countryMapGraph().containsEdge(d_country1, d_country2),
				"Edge between country1 and country2 is missing.");
	}

	/**
	 * Tests if continents are connected subgraphs.
	 */
	@Test
	public void testContinentIsConnectedSubgraph() {
		assertTrue(isConnectedSubgraph(d_continent1.getD_countryList(), d_map.getD_countryMapGraph()),
				"The continent1 should be a connected subgraph.");
		assertTrue(isConnectedSubgraph(d_continent2.getD_countryList(), d_map.getD_countryMapGraph()),
				"The continent2 should be a connected subgraph.");
	}

	/**
	 * Tests the isConnectedSubgraph method.
	 */
	@Test
	public void testIsConnectedSubgraphMethod() {
		Graph<Country, DefaultEdge> l_testGraph = new SimpleGraph<>(DefaultEdge.class);
		l_testGraph.addVertex(d_country1);
		l_testGraph.addVertex(d_country2);
		l_testGraph.addEdge(d_country1, d_country2);
		assertTrue(isConnectedSubgraph(Arrays.asList(d_country1, d_country2), l_testGraph),
				"The method should return true for this test graph.");
	}

	/**
	 * Checks if a list of countries form a connected subgraph.
	 * 
	 * @param p_countries The list of countries.
	 * @param p_graph     The graph to check.
	 * @return True if the countries form a connected subgraph, false otherwise.
	 */
	private boolean isConnectedSubgraph(java.util.List<Country> p_countries, Graph<Country, DefaultEdge> p_graph) {
		Graph<Country, DefaultEdge> l_subgraph = new SimpleGraph<>(DefaultEdge.class);
		for (Country l_country : p_countries) {
			l_subgraph.addVertex(l_country);
		}
		for (Country l_country1 : p_countries) {
			for (Country l_country2 : p_countries) {
				if (p_graph.containsEdge(l_country1, l_country2)) {
					l_subgraph.addEdge(l_country1, l_country2);
				}
			}
		}
		ConnectivityInspector<Country, DefaultEdge> l_inspector = new ConnectivityInspector<>(l_subgraph);
		return l_inspector.isConnected();
	}

	/**
	 * This test loads a valid map and checks if the countryMapGraph and the
	 * continentMapGraph is initialized
	 */
	@Test
	void loadMapTest1() {
		Map l_map = MapUtil.loadMap("validMap2.txt");
		MapUtil.showMap(l_map);

		assertFalse(l_map.getD_countryMapGraph().vertexSet().isEmpty());
		assertFalse(l_map.getD_continentMapGraph().vertexSet().isEmpty());
	}

	/**
	 * This test checks the editMap() function for a valid file
	 */
	@Test
	void editMapTest1() {
		Map map = MapUtil.editMap("InvalidMap1.txt");
		MapUtil.showMap(map);

		assertNotNull(map);
	}

	/**
	 * This test checks the editMap() function for a non-existing file
	 */
	@Test
	void editMapTest2() {
		Map map = MapUtil.editMap("invalid.txt");
		MapUtil.showMap(map);

		assertNotNull(map);
	}

	/**
	 * This test tests the validateMap function. It takes a map object and passes it
	 * to validateMap() to check if validation is correct. In this test, the map
	 * object has a country which does not have neighbours, hence map is invalid.
	 * Context: A map object is passed. Expected Results: The map is invalid, hence
	 * false should be returned, with the message "Country x doesn't have
	 * neighbours"
	 */
	@Test
	void isValidMapTest1() {
		Map l_map = MapUtil.loadMap("InvalidMap1.txt");

		assertFalse(MapUtil.isValidMap(l_map));
	}

	/**
	 * This test tests the validateMap function. It takes a map object and passes it
	 * to validateMap() to check if validation is correct. In this test, the map is
	 * valid. Context: A map object is passed. Expected Results: The map is valid,
	 * hence true should be returned, with the message "Map is Valid!"
	 */
	@Test
	void isValidMapTest2() {
		Map l_map = MapUtil.loadMap("validMap2.txt");

		assertTrue(MapUtil.isValidMap(l_map));
	}

	/**
	 * This test tests the validateMap function. It takes a map object and passes it
	 * to validateMap() to check if validation is correct. In this test, the map
	 * object is empty, hence map is invalid. Context: A map object is passed.
	 * Expected Results: The map is invalid, hence false should be returned, with
	 * the message "Graph is Empty"
	 */
	@Test
	void isValidMapTest3() {
		Map l_map = null;

		assertFalse(MapUtil.isValidMap(l_map));
	}

	/**
	 * This test tests the validateMap function. It takes a map object and passes it
	 * to validateMap() to check if validation is correct. In this test, one
	 * neighbour is mapped to another, but that neighbour is not mapped to it.
	 * Context: A map object is passed. Expected Results: The map is invalid, hence
	 * false should be returned, with message saying that there is a neighbour
	 * mismatch.
	 */
	@Test
	void isValidMapTest4() {
		Map l_map = MapUtil.loadMap("InvalidMap3.txt");

		assertFalse(MapUtil.isValidMap(l_map));
	}

	/**
	 * This test tests the validateMap function. It takes a map object and passes it
	 * to validateMap() to check if validation is correct. In this test, there is a
	 * self loop on one of the countries. Context: A map object is passed. Expected
	 * Results: The map is invalid, hence false should be returned.
	 */
	@Test
	void isValidMapTest5() {
		Map l_map = MapUtil.loadMap("InvalidMap4.txt");

		assertFalse(MapUtil.isValidMap(l_map));
	}

	/**
	 * This test tests the validateMap function. It takes a map object and passes it
	 * to validateMap() to check if validation is correct. In this test, there is a
	 * disconnected continent. Context: A map object is passed. Expected Results:
	 * The map is invalid, hence false should be returned.
	 */
	@Test
	void isValidMapTest6() {
		Map l_map = MapUtil.loadMap("InvalidMap5.txt");

		assertFalse(MapUtil.isValidMap(l_map));
	}

	/**
	 * This test checks the saveMap() function for a valid map
	 */
	@Test
	void saveMap() {
		Map l_map = MapUtil.loadMap("validMap2.txt");
		assertTrue(MapUtil.saveMap(l_map, "savedMap.txt"));
	}
}