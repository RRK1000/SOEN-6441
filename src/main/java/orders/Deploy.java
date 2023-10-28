package orders;

import models.Country;
import models.Order;

/**
 * This class handles the deployment of order.
 * @author Nimisha Jadav
 */
public class Deploy extends Order {
    /**
     * Constructor for the Order class.
     */
    public Deploy() {
        super();
        setD_type("deploy");
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
