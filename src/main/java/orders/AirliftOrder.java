package orders;

import models.Order;
import models.Player;

/**
 * This class handles the airlift type order.
 *
 * @author Nimisha Jadav
 */
public class AirliftOrder implements Order {
    /**
     * Constructor for the Order class.
     */
    public AirliftOrder() {
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isValid(Player p_player) {
        return false;
    }
}
