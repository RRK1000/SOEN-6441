package strategy;

import controller.GameManager;
import models.Country;
import models.Order;
import models.Player;
import orders.AdvanceOrder;
import orders.DeployOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The following class tests the Behaviour of the benevolent strategy
 * @author Anuja Somthankar
 */
class BenevolentStrategyTest {

    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before the test, initialises game manager, map etc.
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

    @Test
    void issueOrderTest() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 6);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 1);
        l_deployOrder2.execute();

        Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryFrom, l_countryTo, 5);
        l_advanceOrder.execute();
        assertSame(l_countryTo.getD_owner(), l_p1);
        assertFalse(l_p1.getD_playerCardList().isEmpty());
    }
}