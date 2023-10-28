package orders;

import models.Country;
import models.Order;

/**
 * This class handles the advance type order.
 * @author Nimisha Jadav
 */
public class Advance extends Order {
    /**
     * Constructor for the Order class.
     */
    public Advance() {
        super();
        setD_type("advance");
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
