package util;

import models.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapUtilTest {
    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map();
    }

    @Test
    void loadMap()  {
        MapUtil mapUtil = new MapUtil();
        mapUtil.map = map;
        mapUtil.loadMap("src/test/resources/validMap1.txt");
    }
}