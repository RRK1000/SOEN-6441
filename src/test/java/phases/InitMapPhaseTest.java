package phases;

import controller.GameManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(d_gameManager.getD_gamePhase().getClass(), StartupPhase.class);
    }

}