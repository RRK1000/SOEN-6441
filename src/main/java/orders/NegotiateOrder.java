package orders;

import models.Order;
import models.Player;

/**
 * This class handles the negotiation.
 *
 * @author Nimisha Jadav
 */
public class NegotiateOrder implements Order {
    /**
     * Constructor for the Order class.
     */
    public NegotiateOrder() {
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isValid(Player p_player) {
        return false;
    }
}
