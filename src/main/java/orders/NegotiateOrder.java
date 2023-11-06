package orders;


import java.nio.file.Path;
import java.nio.file.Paths;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
import global.Cards;
import models.Order;
import models.Player;

/**
 * The NegotiateOrder class represents a negotiation order, allowing players to establish a peaceful agreement.
 * If the negotiation is successful, players cannot attack each other, and a "negotiate" card is removed from the issuing player.
 * A Negotiate order is valid if the target player exists and is not already in negotiation with the issuing player.
 *
 * @author Nimisha Jadav
 * @author Abhigyan
 */
public class NegotiateOrder implements Order {
    private final Player d_player;
    private final Player targetPlayer; // The target player for negotiation

    private static LogEntryBuffer d_logBuffer;
    private static LogFileWriter d_logWriter;
    
    
    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }

    
    /**
     * Constructs a new NegotiateOrder with the specified parameters.
     *
     * @param p_player     The player issuing the negotiation order.
     * @param targetPlayer      The target player for negotiation.
     */
    public NegotiateOrder(Player p_player, Player targetPlayer) {
        this.d_player = p_player;
        this.targetPlayer = targetPlayer;
    }

    private static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
        d_logBuffer.notifyObservers();
    }
    
    /**
     * Executes the negotiation order. If the negotiation is successful, players are unable to attack each other, and the "negotiate" card is removed from the issuing player.
     */
    @Override
    public void execute() {
        // Enforce peace between the players
        d_player.addPlayerNegotiation(targetPlayer);
        targetPlayer.addPlayerNegotiation(d_player);

        // Remove the "negotiate" card from the issuing player
        // Assuming you have a method to remove the card in your Player class
        d_player.getD_playerCardList().remove(Cards.DIPLOMACY_CARD);

        String executionLog = "Negotiation with " + targetPlayer.getD_playerName() + " approached by " + d_player.getD_playerName() + " successful!";
        System.out.println(executionLog);
        logAction(executionLog);

    }

    /**
     * Checks the validity of the Negotiate order.
     *
     * @return True if the order is valid; false otherwise.
     */
    @Override
    public boolean isValid() {
        if (targetPlayer == null) {
            System.out.println("Player to negotiate doesn't exist!");
            logAction("Invalid Negotiate Order: Player to negotiate doesn't exist!");
            return false;
        } else if (!d_player.getD_playerCardList().contains(Cards.DIPLOMACY_CARD)) {
            System.out.println("Player doesn't have Diplomacy Card.");
            logAction("Invalid Negotiate Order: Player doesn't have Diplomacy Card.");
            return false;
        } else {
            return true;
        }
    }
}
