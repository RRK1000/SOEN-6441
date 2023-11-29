package phases;

import controller.GameManager;
import models.Country;
import models.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.HumanStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Issue orders phase
 */
class IssueOrderPhaseTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before all tests, initalises game manager, loads map etc.
     */
    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name, new HumanStrategy());
        d_gameManager.addPlayer(l_player2Name, new HumanStrategy());
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    /**
     * Sets the game manager to null after tests are executed
     */
    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    /**
     * Test for the deploy command
     */
    @Test
    void deployTest() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        d_gameManager.getD_gamePhase().deploy(d_gameManager, l_p1, l_country, 1);
        assertEquals(l_p1.getD_numArmies(), 2); // 3 -> 2
    }

    /**
     * Test case to verify that the nextPhase() method returns the expected next phase.
     */
    @Test
    public void testNextPhase(){
        IssueOrderPhase l_issueOrderPhase = IssueOrderPhase.getInstance();
        Phase l_nextPhase = l_issueOrderPhase.nextPhase();
        assertEquals(ExecuteOrderPhase.class, l_nextPhase.getClass());
    }
}