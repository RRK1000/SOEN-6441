package orders;

import controller.GameManager;
import models.Country;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import static org.junit.jupiter.api.Assertions.*;

class AirliftOrderTest {

    static GameManager d_gameManager;
    static Phase d_gamePhase;

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

    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    @Test
    void executeTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 2);
        l_airlift.execute();
        assertEquals(3, l_countryFrom.getD_numArmies());
    }

    @Test
    void isValidTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 2);
        assertTrue(l_airlift.isValid());
    }

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

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 2);
        assertFalse(l_airlift.isValid());
    }

    @Test
    void isValidTest3() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 5);
        assertFalse(l_airlift.isValid());
    }

    @Test
    void isValidTest4() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 5);
        l_deployOrder1.execute();

        Country l_countryTo = l_p1.getD_countryList().get(1);
        Order l_deployOrder2 = new DeployOrder(l_p1, l_countryTo, 2);
        l_deployOrder2.execute();

        Order l_airlift = new AirliftOrder(l_p1, l_countryFrom, l_countryTo, 7);
        assertFalse(l_airlift.isValid());
    }
}