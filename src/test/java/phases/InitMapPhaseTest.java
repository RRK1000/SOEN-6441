package phases;

import controller.GameManager;
import models.Country;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InitMapPhaseTest {

    static GameManager d_gameManager;
    static Phase d_gamePhase;
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();
    }

    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    @Test
    void loadMapTest() {
        d_gamePhase.loadMap("europe.map", d_gameManager);
        assertEquals(d_gameManager.getD_gamePhase().getClass(), StartupPhase.class);
    }

    @Test
    void editCountryTest() {
        String[] l_cmdSplit = {"editmap", "MapInitTest.txt"};
        String[] p_editCountryInput = {"editcountry", "add 10 1", "remove 10", "add 20 1"};
        d_gamePhase.editMap(d_gameManager, l_cmdSplit);
        d_gamePhase.editCountry(p_editCountryInput, d_gameManager.getD_map());

        assertNotNull(d_gameManager.getD_map().getD_countryByID(20));
        assertNull(d_gameManager.getD_map().getD_countryByID(10));
    }

    @Test
    void editContinentTest() {
        String[] l_cmdSplit = {"editmap", "MapInitTest.txt"};
        String[] p_editContinentInput = {"editcontinent", "add 10 5", "remove 10", "add 20 5"};
        d_gamePhase.editMap(d_gameManager, l_cmdSplit);
        d_gamePhase.editContinent(p_editContinentInput, d_gameManager.getD_map());

        assertNotNull(d_gameManager.getD_map().getD_continentByID(20));
        assertNull(d_gameManager.getD_map().getD_continentByID(10));
    }
  //  String[] p_editNeighbourInput, Map p_map
    @Test
    void editNeighborTest() {
        String[] l_cmdSplit = {"editmap", "MapInitTest.txt"};
        String[] p_editNeighborInput = {"editneighbor", "add 10 20", "add 10 1", "remove 10 1"};
        String[] p_editCountryInput = {"editcountry", "add 10 1", "add 20 1"};
        d_gamePhase.editMap(d_gameManager, l_cmdSplit);
        d_gamePhase.editCountry(p_editCountryInput, d_gameManager.getD_map());
        d_gamePhase.editNeighbor(p_editNeighborInput, d_gameManager.getD_map());
        Country l_country1 = d_gameManager.getD_map().getD_countryByID(10);
        Country l_country2 = d_gameManager.getD_map().getD_countryByID(20);
        Country l_country3 = d_gameManager.getD_map().getD_countryByID(1);

        assertTrue(d_gameManager.getD_map().getD_countryMapGraph().containsEdge(l_country1, l_country2));
        assertFalse(d_gameManager.getD_map().getD_countryMapGraph().containsEdge(l_country1, l_country3));
    }

}