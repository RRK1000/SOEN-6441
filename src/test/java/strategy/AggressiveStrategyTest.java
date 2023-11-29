package strategy;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.GameManager;
import global.Cards;
import models.Order;
import models.Player;
import orders.AdvanceOrder;
import orders.BombOrder;
import orders.DeployOrder;
import phases.ExecuteOrderPhase;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.Phase;

/**
 * The following class tests the Behaviour and working of AggressiveStrategy class
 * @author Abhigyan Singh
 */
class AggressiveStrategyTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Sets up the test environment before each test case execution.
     * Initializes a new {@link GameManager} and sets the game phase to {@link InitMapPhase}.
     * Adds players, assigns strategies, and assigns countries to players for testing.
     */
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("validMap2.txt", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name, new HumanStrategy());
        d_gameManager.findPlayerByName(l_player1Name).setD_playerStrategy(new AggressiveStrategy());
        d_gameManager.addPlayer(l_player2Name, new HumanStrategy());
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    
    /**
     * Cleans the test environment after each test case execution.
     * Resets the {@link GameManager} to null.
     */
    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    /**
     * Tests the creation of a deploy order by the aggressive strategy.
     * Verifies that the order created is an instance of {@link DeployOrder}.
     */
    @Test
    void createOrderTest1() {
        Order l_order = d_gameManager.findPlayerByName("Player1")
                .getD_playerStrategy().createOrder(d_gameManager);
        assertTrue(l_order instanceof DeployOrder);
    }

    /**
     * Tests the creation of an advance order by the aggressive strategy.
     * Executes a previous order, sets up the game phase, and verifies
     * that the new order created is an instance of {@link AdvanceOrder}.
     */
    @Test
    void createOrderTest2() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
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
        assertFalse(l_order instanceof AdvanceOrder);
    }

    /**
     * Tests the creation of a bomb order by the aggressive strategy.
     * Uses a bomb card, executes the order, sets up the game phase,
     * and verifies that the new order created is an instance of {@link BombOrder}.
     */
    @Test
    void createOrderTest3() {
        Player l_currentPlayer = d_gameManager.findPlayerByName("Player1");
        List<String> l_cards = Arrays.asList(Cards.BOMB_CARD);
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
        assertTrue(l_order instanceof BombOrder);
    }
}