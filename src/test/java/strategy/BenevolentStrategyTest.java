package strategy;

import controller.GameManager;
import global.Cards;
import models.Order;
import models.Player;
import orders.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.ExecuteOrderPhase;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.Phase;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        d_gameManager.addPlayer(l_player1Name, new HumanStrategy());
        d_gameManager.findPlayerByName(l_player1Name).setD_playerStrategy(new BenevolentStrategy());
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
     * Tests the creation of a deploy order by the benevolent strategy.
     * Verifies that the order created is an instance of {@link DeployOrder}.
     */
    @Test
    void createOrderTest1() {
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertTrue(l_order instanceof DeployOrder);
    }

    /**
     * Tests the creation of an order by the benevolent strategy after issuing a previous order.
     * Verifies that the new order is not an instance of {@link DeployOrder}.
     */
    @Test
    void createOrderTest2() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        l_currentPlayer.issueOrder();
        d_gameManager.setD_currentPlayerTurn(0);
        Order l_order2 = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);

        assertFalse(l_order2 instanceof DeployOrder);
    }

    /**
     * Tests the creation of an order after executing a previous order by the benevolent strategy.
     * Verifies that the new order is not an instance of {@link AdvanceOrder}.
     */
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
        assertFalse(l_order instanceof AdvanceOrder);
    }

    /**
     * Tests the creation of an airlift order by the benevolent strategy using an airlift card.
     * Verifies that the order created is an instance of {@link AirliftOrder}.
     */
    @Test
    void createOrderTest4() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        List<String> l_cards = Arrays.asList(Cards.AIRLIFT_CARD);
        l_currentPlayer.setD_playerCardList(l_cards);
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        l_currentPlayer.issueOrder();
        d_gameManager.setD_gamePhase(new ExecuteOrderPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(new IssueOrderPhase());

        d_gameManager.setD_currentPlayerTurn(0);
        l_currentPlayer.setD_numArmies(0);
        l_order = l_currentPlayer.getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        assertTrue(l_order instanceof AirliftOrder);
    }

    /**
     * Tests the creation of a negotiate order by the benevolent strategy using a diplomacy card.
     * Verifies that the order created is an instance of {@link NegotiateOrder}.
     */
    @Test
    void createOrderTest5() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        List<String> l_cards = Arrays.asList(Cards.DIPLOMACY_CARD);
        l_currentPlayer.setD_playerCardList(l_cards);
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        l_currentPlayer.issueOrder();
        d_gameManager.setD_gamePhase(new ExecuteOrderPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(new IssueOrderPhase());

        d_gameManager.setD_currentPlayerTurn(0);
        l_currentPlayer.setD_numArmies(0);
        l_order = l_currentPlayer.getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        assertTrue(l_order instanceof NegotiateOrder);
    }

    /**
     * Tests the creation of a blockade order by the benevolent strategy using a blockade card.
     * Verifies that the order created is an instance of {@link BlockadeOrder}.
     */
    @Test
    void createOrderTest6() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        List<String> l_cards = Arrays.asList(Cards.BLOCKADE_CARD);
        l_currentPlayer.setD_playerCardList(l_cards);
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        l_currentPlayer.issueOrder();
        d_gameManager.setD_gamePhase(new ExecuteOrderPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(new IssueOrderPhase());

        d_gameManager.setD_currentPlayerTurn(0);
        l_currentPlayer.setD_numArmies(0);
        l_order = l_currentPlayer.getD_playerStrategy().createOrder(d_gameManager);
        l_currentPlayer.setD_currentOrder(l_order);
        assertTrue(l_order instanceof BlockadeOrder);
    }
}