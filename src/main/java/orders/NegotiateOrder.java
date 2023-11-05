package orders;

import models.Player;
import models.Order;
import controller.GameManager;

import java.util.List;

/**
 * The NegotiateOrder class represents a negotiation order, allowing players to establish a peaceful agreement.
 * If the negotiation is successful, players cannot attack each other, and a "negotiate" card is removed from the issuing player.
 * A Negotiate order is valid if the target player exists and is not already in negotiation with the issuing player.
 *
 * @author Nimisha Jadav
 * @author Abhigyan
 */
public class NegotiateOrder implements Order {
    private final GameManager d_player; // The game manager
    private final Player issuingPlayer; // The player issuing the negotiation order
    private final Player targetPlayer; // The target player for negotiation
    private final String targetPlayerName; // The name of the target player

    /**
     * Constructs a new NegotiateOrder with the specified parameters.
     *
     * @param p_player          The game manager.
     * @param issuingPlayer     The player issuing the negotiation order.
     * @param targetPlayer      The target player for negotiation.
     * @param targetPlayerName  The name of the target player.
     */
    public NegotiateOrder(GameManager p_player, Player issuingPlayer, Player targetPlayer, String targetPlayerName) {
        d_player = p_player;
        this.issuingPlayer = issuingPlayer;
        this.targetPlayer = targetPlayer;
        this.targetPlayerName = targetPlayerName;
    }

    /**
     * Executes the negotiation order. If the negotiation is successful, players are unable to attack each other, and the "negotiate" card is removed from the issuing player.
     */
    @Override
    public void execute() {
        if (issuingPlayer.isInNegotiationWith(targetPlayer)) {
            System.out.println("Players are in negotiation and cannot attack each other.");
        } else {
            // Enforce peace between the players
            issuingPlayer.addPlayerNegotiation(targetPlayer);
            targetPlayer.addPlayerNegotiation(issuingPlayer);

            // Remove the "negotiate" card from the issuing player
            // Assuming you have a method to remove the card in your Player class
            issuingPlayer.getD_playerCardList().remove("negotiate");

            // Access the player names and print the negotiation message to the console
            String executionLog = "Negotiation with " + targetPlayer.getD_playerName() + " approached by " + issuingPlayer.getD_playerName() + " successful!";
            System.out.println(executionLog);
        }
    }

    /**
     * Checks the validity of the Negotiate order.
     *
     * @return True if the order is valid; false otherwise.
     */
    @Override
    public boolean isValid() {
        if (issuingPlayer.isInNegotiationWith(targetPlayer)) {
            System.out.println("Players are in negotiation and cannot attack each other.");
            return false;
        }

        if (targetPlayer != null && targetPlayer.getD_playerName() != null && !targetPlayer.getD_playerName().isEmpty()) {
            // Target player is not null and has a name
            return true;
        } else {
            System.out.println("Player to negotiate doesn't exist!");
            return false;
        }
    }
}
