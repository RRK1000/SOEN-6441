package orders;

import models.Order;
import models.Player;

/**
 * This class handles the blockade type order.
 *
 * @author Nimisha Jadav
 */
public class BlockadeOrder implements Order {
    /**
     * Constructor for the Order class.
     */
    public BlockadeOrder() {
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isValid(Player p_player) {
        return false;
    }
}
