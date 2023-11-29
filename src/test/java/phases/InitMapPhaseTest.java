package phases;

import controller.GameManager;
import models.Continent;
import models.Country;
import models.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.MapUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to check the map initialisation phase
 * @author Anuja Somthankar
 */
class InitMapPhaseTest {

    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before the tests, initialises game manager and phase
     */
    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();
    }

    /**
     * Sets game manager to null after test is done
     */
    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    /**
     * Checks if phase is changed to startup phase after map is loaded
     */
    @Test
    void loadMapTest(){
        d_gamePhase.loadMap("europe.map", d_gameManager);
        assertEquals(d_gameManager.getD_gamePhase().getClass(), StartupPhase.class);
    }

    /**
     * Test case for editNeighbour method to verify the functionality of editing neighbours
     * by adding a neighbouring relationship between two countries.
     */
    @Test
    void testEditNeighbour(){
        String[] input = {"editneighbor", "add 1 2"};
        Map l_map = new Map();
        MapUtil.addCountry(l_map,1,1);
        MapUtil.addCountry(l_map,2,1);
        d_gamePhase.editNeighbor(input,l_map);
        assertTrue(l_map.getD_countryByID(1).getD_neighbourCountryIDList().contains(2));
    }

}