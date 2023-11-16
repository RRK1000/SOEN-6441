package phases;

import controller.GameManager;
import models.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.MapUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for startup phase
 * @author Rishi Ravikumar
 */
class StartupPhaseTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Executes before any test is run, initialises game manager and loads map
     */
    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = StartupPhase.getInstance();

        Map l_map = MapUtil.loadMap("europe.map");
        d_gameManager.setD_map(l_map);
    }

    /**
     * Executes after all the tests are run, sets game manager to null
     */
    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    /**
     * Tests the game player addition and removal command
     * Expected Result: Player list of game manager should not be empty
     */
    @Test
    void gamePlayerTest() {
        String[] l_cmdSplit = {"gameplayer", "-add", "p1", "-remove", "p2"};
        d_gamePhase.gamePlayer(l_cmdSplit, d_gameManager);
        assertFalse(d_gameManager.getD_playerList().isEmpty());
    }

    /**
     * Tests the assign countries command
     * Expected Results: Phase should be changed to issue order
     */
    @Test
    void assignCountriesTest() {
        String[] l_cmdSplit = {"gameplayer", "-add", "p1", "-add", "p2"};
        d_gamePhase.gamePlayer(l_cmdSplit, d_gameManager);
        d_gamePhase.assignCountries(d_gameManager);
        assertEquals(d_gameManager.getD_gamePhase().getClass(), IssueOrderPhase.class);
    }
}