package orders;

import controller.GameManager;
import models.Country;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;
import strategy.HumanStrategy;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Deploy order command
 * @author Rishi Ravikumar
 */
class DeployOrderTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before all tests, initialises game manager, map etc.
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
     * Sets game manager to null after tests are done
     */
    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    /**
     * Test to check the execution of deploy order
     */
    @Test
    void execute() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        Order l_order = new DeployOrder(l_p1, l_country, 3);
        l_order.execute();
        assertEquals(l_country.getD_numArmies(), 3);
    }

    /**
     * Test to check the validation of a valid deploy order
     */
    @Test
    void isValidTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        Order l_order = new DeployOrder(l_p1, l_country, 3);
        assertTrue(l_order.isValid());
    }

    /**
     * Test to check the validation of an invalid deploy order, when excessive armies are deployed
     */
    @Test
    void isValidTest2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        Order l_order = new DeployOrder(l_p1, l_country, 4);
        assertFalse(l_order.isValid());
    }
}