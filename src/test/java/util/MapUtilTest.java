package util;

import models.Country;
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

        for(Country l_c : d_map.getD_countryMapGraph().vertexSet()){
            System.out.println(d_map.getD_countryMapGraph().outgoingEdgesOf(l_c));
        }

        assertFalse(d_map.getD_countryMapGraph().vertexSet().isEmpty());
        assertFalse(d_map.getD_continentMapGraph().vertexSet().isEmpty());

    }
}