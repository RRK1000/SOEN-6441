package orders;

import models.Player;
import models.Order;
import controller.GameManager;

import java.util.List;

/**
 * The class represents a Negotiate order.
 */
public class NegotiateOrder implements Order {
    private final GameManager d_player;
    private final Player issuingPlayer;
    private final Player targetPlayer;
    private final String targetPlayerName;


    public NegotiateOrder(GameManager p_player, Player issuingPlayer, Player targetPlayer, String targetPlayerName) {
        d_player = p_player;
        this.issuingPlayer = issuingPlayer;
        this.targetPlayer = targetPlayer;
        this.targetPlayerName = targetPlayerName;
    }

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