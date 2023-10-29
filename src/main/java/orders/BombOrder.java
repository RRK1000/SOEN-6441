package orders;

import models.Order;
import models.Player;

/**
 * This class handles the bomb type order.
 *
 * @author Nimisha Jadav
 */
public class BombOrder implements Order {
    /**
     * Constructor for the Order class.
     */
    public BombOrder() {
    }

    /**
     *
     */
    @Override
    public void execute() {

    }

    /**
     * @return
     */
    @Override
    public boolean isValid(Player p_player) {
        return false;
    }

}
