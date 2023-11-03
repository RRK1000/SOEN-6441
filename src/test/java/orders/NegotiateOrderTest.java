package orders;

import models.Player;
import controller.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

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
        negotiateOrder = new NegotiateOrder(gameManager, issuingPlayer, targetPlayer, targetPlayerName);
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
    void isValid() {
        // Test the validity of the order when the target player exists
        assertTrue(negotiateOrder.isValid());

        // Test the validity of the order when the target player doesn't exist
        targetPlayerName = "NonExistentPlayer";
        negotiateOrder = new NegotiateOrder(gameManager, issuingPlayer, null, targetPlayerName);
        assertFalse(negotiateOrder.isValid());

        // Test the validity of the order when players are in negotiation
        issuingPlayer.addPlayerNegotiation(targetPlayer);
        targetPlayer.addPlayerNegotiation(issuingPlayer);

       /* // Capture the console output when players are in negotiation
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        assertFalse(negotiateOrder.isValid());
        System.setOut(System.out);

        // Verify that the expected message is printed
        String printedMessage = outputStream.toString().trim();
        assertEquals("Player to negotiate doesn't exist!", printedMessage);*/
    }
}
