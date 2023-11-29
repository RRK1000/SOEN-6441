package util;

import controller.GameManager;
import models.Continent;
import models.Country;
import models.Map;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the test methods for MapUtil.java. The tested functions
 * are loadMap, validateMap and continent related functionalities.
 *
 * @author Anuja-Somthankar Rishi Ravikumar Yusuke Ishii
 */
class MapUtilTest {
	private Map d_map;

	/**
	 *
	 *  Tests if there are edges between the first two countries in the map. This
	 * ensures that countries within a continent are interconnected, which is a
	 * first step towards validating that a continent is a connected subgraph
	 */
	@Test
	public void isValidContinentTest1() {
		d_map = MapUtil.loadMap("validContinent.txt");

		Object[] l_countries = d_map.getD_countryMapGraph().vertexSet().toArray();
		Country l_firstCountry = (Country) l_countries[0];
		Country l_secondCountry = (Country) l_countries[1];

		assertTrue(d_map.getD_countryMapGraph().containsEdge(l_firstCountry, l_secondCountry),
				"Edge between the first and second country is missing.");
	}

	/**
	 * Tests if all continents in the map are connected subgraphs. each continent
	 * should be a connected subgraph. If any continent is not a connected subgraph,
	 * the test will fail.
	 */

	@Test
	public void isValidContinentTest2() {
		d_map = MapUtil.loadMap("validContinent.txt");

		for (Continent l_continent : d_map.getD_continentMapGraph().vertexSet()) {
			assertTrue(isConnectedSubgraph(l_continent.getD_countryList(), d_map.getD_countryMapGraph()),
					"The continent should be a connected subgraph.");
		}
	}

	/**
	 * Checks if a list of countries form a connected subgraph within the given
	 * graph This method is to isValid that a continent (represented by a list of
	 * countries) is a connected subgraph It constructs a subgraph from the given
	 * list of countries and checks its connectivity
	 * 
	 * @param p_countries The list of countries representing a continent.
	 * @param p_graph     The main graph to check against.
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
		Map l_map = MapUtil.editMap("InvalidMap1.txt", new GameManager());
		MapUtil.showMap(l_map);

		assertNotNull(l_map);
	}

	/**
	 * This test checks the editMap() function for a non-existing file
	 */
	@Test
	void editMapTest2() {
		Map l_map = MapUtil.editMap("invalid.txt", new GameManager());
		MapUtil.showMap(l_map);

		assertNotNull(l_map);
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

	/**
	 * This test checks if the continents from the saved map is loaded sucessfully
	 * or not. A valid map is passed. To check if the map was loaded sucessfully,
	 * we check for total number of continents in the saved map and
	 * the continentValue by the continentByID.
	 */
	@Test
	void loadContinents() {
		Map l_map = MapUtil.loadMap("validMap2.txt");
		assertEquals(18, l_map.getD_continentMapGraph().vertexSet().size());

		Continent scandinavia = l_map.getD_continentByID(1);
		assertNotNull(scandinavia);
		assertEquals(5, scandinavia.getD_continentValue());
	}

	/**
	 * This test checks if the continents from the saved map is loaded sucessfully
	 * or not.A valid map is passed. To check if the map was loaded sucessfully,
	 * we check for total number of countries in the saved map and check the continentID
	 * by using the countryID.
	 */
	@Test
	void loadCountries() {
		Map l_map = MapUtil.loadMap("validMap2.txt");
		assertEquals(180, l_map.getD_countryMapGraph().vertexSet().size());
		assertEquals(1, l_map.getD_countryByID(1).getD_continentID());
	}

	/**
	 * This test checks if the borders of the saved map is loaded successfully
	 * or not. A valid map is passed. It checks for the border between two countries
	 * in the loaded map.
	 */
	@Test
	void loadBorders() {
		Map l_map = MapUtil.loadMap("validMap2.txt");
		Country l_country1 = l_map.getD_countryByID(1);
		Country l_country2 = l_map.getD_countryByID(2);
		assertTrue(l_map.getD_countryMapGraph().containsEdge(l_country1,l_country2));
	}
}