package strategy;

import controller.GameManager;
import models.Order;

public class AggressiveStrategy implements Strategy{

    /**
     * Creates an order according to player strategy
     *
     * @param p_gameManager {@link GameManager}
     * @return {@link Order}
     */
    @Override
    public Order createOrder(GameManager p_gameManager) {
        return null;
    }
}
