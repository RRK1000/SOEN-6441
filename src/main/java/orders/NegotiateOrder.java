package orders;


import gamelog.LogManager;
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
    private final Player d_targetPlayer; // The target player for negotiation

    /**
     * Constructs a new NegotiateOrder with the specified parameters.
     *
     * @param p_player       The player issuing the negotiation order.
     * @param p_targetPlayer The target player for negotiation.
     */
    public NegotiateOrder(Player p_player, Player p_targetPlayer) {
        this.d_player = p_player;
        this.d_targetPlayer = p_targetPlayer;
    }


    /**
     * Executes the negotiation order. If the negotiation is successful, players are unable to attack each other, and the "negotiate" card is removed from the issuing player.
     */
    @Override
    public void execute() {
        if (d_targetPlayer == null) {
            LogManager.logAction("err: Execute Negotiate order failed. Player to negotiate doesn't exist");
            return;
        }

        // Enforce peace between the players
        d_player.addPlayerNegotiation(d_targetPlayer);
        d_targetPlayer.addPlayerNegotiation(d_player);

        String l_executionLog = "Negotiation with " + d_targetPlayer.getD_playerName() + " approached by " + d_player.getD_playerName() + " successful!";
        System.out.println(l_executionLog);
        LogManager.logAction(l_executionLog);

    }

    /**
     * Checks the validity of the Negotiate order.
     *
     * @return True if the order is valid; false otherwise.
     */
    @Override
    public boolean isValid() {
        if (d_targetPlayer == null) {
            String l_err = "err: Invalid Negotiate Order, Player to negotiate doesn't exist!";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (!d_player.getD_playerCardList().contains(Cards.DIPLOMACY_CARD)) {
            String l_err = "err: Invalid Negotiate Order, Player doesn't have Diplomacy Card.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else {
            return true;
        }
    }
}
