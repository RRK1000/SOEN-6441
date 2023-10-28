package orders;

import models.Country;
import models.Order;

/**
 * This class handles the negotiation.
 * @author Nimisha Jadav
 */
public class Negotiate extends Order {
    /**
     * Constructor for the Order class.
     */
    public Negotiate() {
        super();
        setD_type("negotiate");
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
