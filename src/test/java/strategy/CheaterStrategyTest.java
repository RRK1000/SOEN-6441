package strategy;

import controller.GameManager;
import models.Country;
import models.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import static org.junit.jupiter.api.Assertions.*;

class CheaterStrategyTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name);
        d_gameManager.findPlayerByName(l_player1Name).setD_playerStrategy(new CheaterStrategy());
        d_gameManager.addPlayer(l_player2Name);
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    @Test
    void createOrderTest1() {
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertNull(l_order);
    }

    @Test
    void createOrderTest2() {
        Country l_enemyCountry = d_gameManager.findPlayerByName("Player2").getD_countryList().get(0);
        d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertSame(l_enemyCountry.getD_owner(), d_gameManager.findPlayerByName("Player1"));
    }
}