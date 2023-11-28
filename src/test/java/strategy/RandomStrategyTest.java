package strategy;

import controller.GameManager;
import models.Order;
import models.Player;
import orders.AdvanceOrder;
import orders.DeployOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.ExecuteOrderPhase;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.Phase;

import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyTest {

    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before the test, initialises game manager, map etc.
     */
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name, new HumanStrategy());
        d_gameManager.findPlayerByName(l_player1Name).setD_playerStrategy(new RandomStrategy());
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

    @Test
    void createOrderTest1() {
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertTrue(l_order instanceof DeployOrder);
    }

}