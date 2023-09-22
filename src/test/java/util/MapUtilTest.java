package util;

import models.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains the test methods for MapUtil.java.
 * The tested functions are loadMap, validateMap.
 * @author Anuja-Somthankar Rishi Ravikumar
 */
class MapUtilTest {
    private Map d_map;

    @BeforeEach
    void setUp() {
        d_map = new Map();
    }

    /**
     *
     */
    @Test
    void loadMap()  {
        MapUtil mapUtil = new MapUtil();
        mapUtil.d_map = d_map;
        mapUtil.loadMap("src/test/resources/validMap1.txt");

        mapUtil.showMap(d_map);
 
        assertFalse(d_map.getD_countryMapGraph().vertexSet().isEmpty());
        assertFalse(d_map.getD_continentMapGraph().vertexSet().isEmpty());

    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, the map object has a country which does not have neighbours, hence map is invalid.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned.
     */
    @Test
     void validateMapTest1() {
        MapUtil l_mapUtil = new MapUtil();
        l_mapUtil.d_map = d_map;
        l_mapUtil.loadMap("src/test/resources/validMap1.txt");

        assertFalse(l_mapUtil.validateMap(d_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, the map is valid.
     * Context: A map object is passed.
     * Expected Results: The map is valid, hence true should be returned.
     */
    @Test
    void validateMapTest2() {
        MapUtil l_mapUtil = new MapUtil();
        l_mapUtil.d_map = d_map;
        l_mapUtil.loadMap("src/test/resources/validMap2.txt");

        assertTrue(l_mapUtil.validateMap(d_map));
    }

    /**
     * This test tests the validateMap function. It takes a map object and passes it to validateMap() to check if validation is correct.
     * In this test, the map object is empty, hence map is invalid.
     * Context: A map object is passed.
     * Expected Results: The map is invalid, hence false should be returned.
     */
    @Test
    void validateMapTest3() {
        MapUtil l_mapUtil = new MapUtil();
        l_mapUtil.d_map = d_map;

        assertFalse(l_mapUtil.validateMap(d_map));
    }
}