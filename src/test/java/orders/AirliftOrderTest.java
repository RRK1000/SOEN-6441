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
 * Tests for the Deploy order command
 * @author Anuja Somthankar
 */
class AirliftOrderTest {

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
        d_gameManager.addPlayer(l_player1Name);
        d_gameManager.addPlayer(l_player2Name);
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
     * Tests a valid airlift order scenario
     */
    @Test
    void executeTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(0);
        Country l_countryTo = l_p2.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 2);
        l_deployOrder2.execute();
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.AIRLIFT_CARD)));

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 2);
        l_airlift.execute();
        assertEquals(3, l_countryFrom.getD_numArmies());
    }

    /**
     * Test to check a correct airlift order
     */
    @Test
    void isValidTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();
        List<String> l_cards = Arrays.asList(Cards.AIRLIFT_CARD);
        l_p1.setD_playerCardList(l_cards);
        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 2);
        assertTrue(l_airlift.isValid());
    }

    /**
     * Test to check an invalid airlift order, when play does not own target country
     */
    @Test
    void isValidTest2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 2);
        l_deployOrder2.execute();
        List<String> l_cards = Arrays.asList(Cards.AIRLIFT_CARD);
        l_p1.setD_playerCardList(l_cards);

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 2);
        assertFalse(l_airlift.isValid());
    }

    /**
     * Test to check an invalid airlift order, when all armies are trying to be airlifted
     */
    @Test
    void isValidTest3() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();
        List<String> l_cards = Arrays.asList(Cards.AIRLIFT_CARD);
        l_p1.setD_playerCardList(l_cards);

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 5);
        assertFalse(l_airlift.isValid());
    }

    /**
     * Test to check an invalid airlift order, when excessive armies are airlifted
     */
    @Test
    void isValidTest4() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();
        List<String> l_cards = Arrays.asList(Cards.AIRLIFT_CARD);
        l_p1.setD_playerCardList(l_cards);

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 7);
        assertFalse(l_airlift.isValid());
    }
}