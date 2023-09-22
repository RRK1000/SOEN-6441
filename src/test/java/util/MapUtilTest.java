package util;

import models.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class MapUtilTest {
    private Map d_map;

    @BeforeEach
    void setUp() {
        d_map = new Map();
    }

    @Test
    void loadMap()  {
        MapUtil mapUtil = new MapUtil();
        mapUtil.d_map = d_map;
        mapUtil.loadMap("src/test/resources/validMap1.txt");

        mapUtil.showMap(d_map);
 
        assertFalse(d_map.getD_countryMapGraph().vertexSet().isEmpty());
        assertFalse(d_map.getD_continentMapGraph().vertexSet().isEmpty());

    }
}