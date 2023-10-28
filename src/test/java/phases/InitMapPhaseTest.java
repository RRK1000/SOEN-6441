package phases;

import controller.GameManager;
import models.Map;
import org.junit.jupiter.api.*;
import util.MapUtil;

import static org.junit.jupiter.api.Assertions.*;

class InitMapPhaseTest {

    static GameManager d_gameManager;
    static Phase d_gamePhase;
    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();
    }

    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    @Test
    void loadMapTest(){
        d_gamePhase.loadMap("europe.map", d_gameManager);
        assertEquals(d_gameManager.d_gamePhase.getClass(), StartupPhase.class);
    }

}