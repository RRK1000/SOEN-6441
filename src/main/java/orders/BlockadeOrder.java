package orders;

import models.Order;

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
    public boolean isValid() {
        return false;
    }
}
