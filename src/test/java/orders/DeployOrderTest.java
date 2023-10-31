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

import static org.junit.jupiter.api.Assertions.*;

class DeployOrderTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String player1Name = "Player1";
        String player2Name = "Player2";
        d_gameManager.addPlayer(player1Name);
        d_gameManager.addPlayer(player2Name);
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    @Test
    void execute() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        Order l_order = new DeployOrder(l_country, 3);
        l_order.execute();
        assertEquals(l_country.getD_numArmies(), 3);
    }

    @Test
    void isValidTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        Order l_order = new DeployOrder(l_country, 3);
        assertTrue(l_order.isValid(l_p1));
    }

    @Test
    void isValidTest2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        Order l_order = new DeployOrder(l_country, 4);
        assertFalse(l_order.isValid(l_p1));
    }
}