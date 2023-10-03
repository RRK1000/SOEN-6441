package util;

import models.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the test methods for MapUtil.java.
 * The tested functions are loadMap, validateMap.
 *
 * @author Anuja-Somthankar Rishi Ravikumar
 */
class MapUtilTest {

    /**
     * This test loads a valid map and checks if the countryMapGraph and the continentMapGraph is initialized
     */
    @Test
    void loadMapTest1() {
        Map l_map = MapUtil.loadMap("src/test/resources/validMap2.txt");
        MapUtil.showMap(l_map);

        assertFalse(l_map.getD_countryMapGraph().vertexSet().isEmpty());
        assertFalse(l_map.getD_continentMapGraph().vertexSet().isEmpty());
    }

    /**
     * This test  checks the editMap() function for a valid file
     */
    @Test
    void editMapTest1() {
        Map map = MapUtil.editMap("src/test/resources/InvalidMap1.txt");
        MapUtil.showMap(map);

        assertNotNull(map);
    }

    /**
     * This test  checks the editMap() function for a non-existing file
     */
    @Test
    void editMapTest2() {
        Map map = MapUtil.editMap("src/test/resources/invalid.txt");
        MapUtil.showMap(map);

        assertNotNull(map);
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, the map object has a country which does not have neighbours, hence map is invalid.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned, with the message "Country x doesn't have neighbours"
     */
    @Test
    void isValidMapTest1() {
        Map l_map = MapUtil.loadMap("src/test/resources/InvalidMap1.txt");

        assertFalse(MapUtil.isValidMap(l_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, the map is valid.
     * Context: A map object is passed.
     * Expected Results: The map is valid, hence true should be returned, with the message "Map is Valid!"
     */
    @Test
    void isValidMapTest2() {
        Map l_map = MapUtil.loadMap("src/test/resources/validMap2.txt");

        assertTrue(MapUtil.isValidMap(l_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, the map object is empty, hence map is invalid.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned, with the message "Graph is Empty"
     */
    @Test
    void isValidMapTest3() {
        Map l_map = null;

        assertFalse(MapUtil.isValidMap(l_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, one neighbour is mapped to another, but that neighbour is not mapped to it.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned.
     */
    @Test
    void isValidMapTest4() {  //neighbour mismatch
        Map l_map = MapUtil.loadMap("src/test/resources/InvalidMap3.txt");

        assertFalse(MapUtil.isValidMap(l_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, there is a self loop on one of the countries.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned.
     */
    @Test
    void isValidMapTest5() {  //neighbour mismatch
        Map l_map = MapUtil.loadMap("src/test/resources/InvalidMap4.txt");

        assertFalse(MapUtil.isValidMap(l_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, there is a duplicate country.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned.
     */
    @Test
    void isValidMapTest6() {  //neighbour mismatch
        Map l_map = MapUtil.loadMap("src/test/resources/InvalidMap5.txt");

        assertFalse(MapUtil.isValidMap(l_map));
    }

    /**
     * This test checks the saveMap() function for a valid map
     */
    @Test
    void saveMap() {
        Map l_map = MapUtil.loadMap("src/test/resources/validMap2.txt");
        assertTrue(MapUtil.saveMap(l_map, "src/test/resources/savedMap.txt"));
    }
}