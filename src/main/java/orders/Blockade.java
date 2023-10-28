package orders;

import models.Country;
import models.Order;

/**
 * This class handles the blockade type order.
 * @author Nimisha Jadav
 */
public class Blockade extends Order {
    /**
     * Constructor for the Order class.
     */
    public Blockade() {
        super();
        setD_type("blockade");
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
