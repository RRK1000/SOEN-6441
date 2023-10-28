package orders;

import models.Country;
import models.Order;

/**
 * This class handles the airlift type order.
 * @author Nimisha Jadav
 */
public class Airlift extends Order {
    /**
     * Constructor for the Order class.
     */
    public Airlift() {
        super();
        setD_type("airlift");
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void printOrder() {

    }
}
