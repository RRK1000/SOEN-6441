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

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name);
        d_gameManager.findPlayerByName(l_player1Name).setD_playerStrategy(new BenevolentStrategy());
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
    void createOrderTest1() {
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertTrue(l_order instanceof DeployOrder);
    }

    @Test
    void createOrderTest2() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        l_currentPlayer.issueOrder();

        Order l_order2 = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);

        assertFalse(l_order2 instanceof DeployOrder);
    }

    @Test
    void createOrderTest3() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        l_currentPlayer.issueOrder();
        d_gameManager.setD_gamePhase(new ExecuteOrderPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(new IssueOrderPhase());

        l_currentPlayer.setD_numArmies(0);
        l_order = l_currentPlayer.getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        assertTrue(l_order instanceof AdvanceOrder);
    }
}