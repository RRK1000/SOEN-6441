package strategy;

import controller.GameManager;
import models.Order;

/**
 * Strategy implementation - defines the way a particular strategy would play out
 * @author Anuja Somthankar
 */
public interface Strategy {

    /**
     * Creates an order according to player strategy
     *
     * @param p_gameManager {@link GameManager}
     * @return {@link Order}
     */
    Order createOrder(GameManager p_gameManager);
}
