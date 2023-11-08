package phases;

import controller.GameManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}