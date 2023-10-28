package phases;

import controller.GameManager;
import models.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.MapUtil;

import static org.junit.jupiter.api.Assertions.*;

class StartupPhaseTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new StartupPhase();

        Map d_map = MapUtil.loadMap("europe.map");
        d_gameManager.setD_map(d_map);
    }

    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    @Test
    void gamePlayerTest() {
        String[] l_cmdSplit = {"gameplayer", "-add", "p1", "-remove", "p2"};
        d_gamePhase.gamePlayer(l_cmdSplit, d_gameManager);
        assertFalse(d_gameManager.getD_playerList().isEmpty());
    }

    @Test
    void assignCountriesTest() {
        String[] l_cmdSplit = {"gameplayer", "-add", "p1", "-add", "p2"};
        d_gamePhase.gamePlayer(l_cmdSplit, d_gameManager);
        d_gamePhase.assignCountries(d_gameManager);
        assertEquals(d_gameManager.getD_gamePhase().getClass(), IssueOrderPhase.class);
    }
}