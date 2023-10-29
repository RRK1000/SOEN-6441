package orders;

import models.Order;
import models.Player;

/**
 * This class handles the advance type order.
 *
 * @author Nimisha Jadav
 */
public class AdvanceOrder implements Order {
    /**
     * Constructor for the Order class.
     */
    public AdvanceOrder() {
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isValid(Player p_player) {
        return false;
    }
}
