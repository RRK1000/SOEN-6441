package strategy;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.GameManager;
import models.Country;
import models.Order;
import phases.InitMapPhase;
import phases.Phase;

/**
 * The following class tests the Behaviour and working of AggressiveStrategy class
 * @author Abhigyan Singh
 */
class CheaterStrategyTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;
    /**
     * Setup before the test, initializes game manager, map, etc.
     */
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name, new HumanStrategy());
        d_gameManager.findPlayerByName(l_player1Name).setD_playerStrategy(new CheaterStrategy());
        d_gameManager.addPlayer(l_player2Name, new HumanStrategy());
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    /**
     * Cleans the test environment after each test case execution.
     * Resets the {@link GameManager} to null.
     */
    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    /**
     * Tests the creation of an order by the cheater strategy.
     * Verifies that no order is created by the cheater strategy.
     */
    @Test
    void createOrderTest1() {
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertNull(l_order);
    }

    /**
     * Tests the creation of an order by the cheater strategy and checks
     * if the cheater successfully owns all enemy countries.
     */
    @Test
    void createOrderTest2() {
        Country l_enemyCountry = d_gameManager.findPlayerByName("Player2").getD_countryList().get(0);
        d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertSame(l_enemyCountry.getD_owner(), d_gameManager.findPlayerByName("Player2"));
    }
}