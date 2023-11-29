package strategy;

import controller.GameManager;
import models.Order;

import java.io.Serializable;

/**
 * Represents the Human Strategy
 * @author Anuja Somthankar
 */
public class HumanStrategy implements Strategy, Serializable {

    /**
     * Issues an order according to player strategy
     *
     * @param p_gameManager
     * @return
     */
    @Override
    public Order createOrder(GameManager p_gameManager) {
        return null;
    }
}
