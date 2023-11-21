package orders;

import controller.GameManager;
import global.Cards;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;
import strategy.HumanStrategy;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the negotiate order command
 * @author Abhigyan Singh
 * @author Rishi Ravikumar
 */
class NegotiateOrderTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before all tests, initialises game manager, map etc.
     */
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("validMap2.txt", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name, new HumanStrategy());
        d_gameManager.addPlayer(l_player2Name, new HumanStrategy());
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    /**
     * Sets game manager to null after tests are done
     */
    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    /**
     * Test to check the execution of negotiate order
     */
    @Test
    void execute() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.DIPLOMACY_CARD)));

        Player l_p2 = d_gameManager.getD_playerList().get(1);

        Order l_negotiate = new NegotiateOrder(l_p1, l_p2);
        l_negotiate.execute();
        assertTrue(l_p1.isInNegotiationWith(l_p2));
    }

    /**
     * Test to check the validation of a valid negotiate order
     */
    @Test
    void isValid1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.DIPLOMACY_CARD)));

        Player l_p2 = d_gameManager.getD_playerList().get(1);

        Order l_negotiate = new NegotiateOrder(l_p1, l_p2);
        assertTrue(l_negotiate.isValid());
    }

    /**
     * Test to check validation of an invalid negotiate order, when the player doesnt exist
     */
    @Test
    void isValid2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.DIPLOMACY_CARD)));

        Player l_p2 = null;

        Order l_negotiate = new NegotiateOrder(l_p1, l_p2);
        assertFalse(l_negotiate.isValid());
    }
}
