package orders;

import models.Country;
import models.Order;

/**
 * This class handles the bomb type order.
 * @author Nimisha Jadav
 */
public class Bomb extends Order {
    /**
     * Constructor for the Order class.
     */
    public Bomb() {
        super();
        setD_type("bomb");
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
    public boolean validate() {
        return false;
    }

    /**
     *
     */
    @Override
    public void printOrder() {

    }
}
