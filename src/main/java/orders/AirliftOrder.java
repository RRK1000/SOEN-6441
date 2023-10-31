package orders;

import models.Order;

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
    public boolean isValid() {
        return false;
    }
}
