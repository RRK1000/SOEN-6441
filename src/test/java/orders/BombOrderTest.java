package orders;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Bomb order command
 * @author Rishi Ravikumar
 */
class BombOrderTest {
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
        String player1Name = "Player1";
        String player2Name = "Player2";
        d_gameManager.addPlayer(player1Name);
        d_gameManager.addPlayer(player2Name);
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
     * Test to check the execution of bomb order
     */
    @Test
    void execute() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country1 = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_country1, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_country2 = l_p2.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_country2, 2);
        l_deployOrder2.execute();
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.BOMB_CARD)));

        Order l_bombOrder = new BombOrder(l_p1, l_country2);
        l_bombOrder.execute();
        assertEquals(1, l_country2.getD_numArmies());
    }

    /**
     * Test to check the validation of valid bomb order
     */
    @Test
    void isValidTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country1 = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_country1, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_country2 = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_country2, 1);
        l_deployOrder2.execute();
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.BOMB_CARD)));

        Order l_bombOrder = new BombOrder(l_p1, l_country2);
        assertTrue(l_bombOrder.isValid());
    }

    /**
     * Test to cehck the validation of invalid bomb order, when country is not a neighbour
     */
    @Test
    void isValidTest2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country1 = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_country1, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_country2 = l_p2.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_country2, 1);
        l_deployOrder2.execute();
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.BOMB_CARD)));


        Order l_bombOrder = new BombOrder(l_p1, l_country1);
        assertFalse(l_bombOrder.isValid());
    }
}