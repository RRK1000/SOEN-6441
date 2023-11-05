package orders;

import controller.GameManager;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegotiateOrderTest {
    private GameManager gameManager;
    private Player issuingPlayer;
    private Player targetPlayer;
    private String targetPlayerName;
    private NegotiateOrder negotiateOrder;

    @BeforeEach
    void setUp() {
        // Initialize the GameManager and players
        gameManager = new GameManager();
        issuingPlayer = new Player("PlayerA");
        targetPlayerName = "PlayerB";
        targetPlayer = new Player(targetPlayerName);

        // Add players to the game manager
        gameManager.addPlayer(issuingPlayer.getD_playerName());
        gameManager.addPlayer(targetPlayerName);

        // Initialize the NegotiateOrder
        negotiateOrder = new NegotiateOrder(issuingPlayer, targetPlayer);
    }

    @AfterEach
    void tearDown() {
        gameManager = null;
    }

    @Test
    void execute() {
        // Ensure that the execute method works without errors
        assertDoesNotThrow(() -> negotiateOrder.execute());
    }

    @Test
    void isValid1() {
        // Test the validity of the order when the target player exists
        assertTrue(negotiateOrder.isValid());

        // Test the validity of the order when the target player doesn't exist
        targetPlayerName = "NonExistentPlayer";
        negotiateOrder = new NegotiateOrder(issuingPlayer, null);
        assertFalse(negotiateOrder.isValid());

        // Test the validity of the order when players are in negotiation
        issuingPlayer.addPlayerNegotiation(targetPlayer);
        targetPlayer.addPlayerNegotiation(issuingPlayer);
        assertFalse(negotiateOrder.isValid());

    }
    @Test
    void isValid2() {
        // Test the validity of the order when the target player exists
        assertTrue(negotiateOrder.isValid());

        // Test the validity of the order when the target player exist
        targetPlayerName = "PlayerB";
        negotiateOrder = new NegotiateOrder(issuingPlayer, targetPlayer);
        assertTrue(negotiateOrder.isValid());

        // Test the validity of the order when players are in negotiation
        issuingPlayer.addPlayerNegotiation(targetPlayer);
        targetPlayer.addPlayerNegotiation(issuingPlayer);
        assertFalse(negotiateOrder.isValid());
    }
}
