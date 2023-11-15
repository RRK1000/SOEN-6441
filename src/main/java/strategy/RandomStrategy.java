package strategy;

import controller.GameManager;
import models.Order;
import models.Player;
import orders.DeployOrder;

import java.util.Random;

public class RandomStrategy implements Strategy{

    /**
     * Creates an order according to player strategy
     *
     * @param p_gameManager {@link GameManager}
     * @return {@link Order}
     */
    @Override
    public Order createOrder(GameManager p_gameManager) {
        Random l_random = new Random();

        Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
        int l_numArmies = l_currentPlayer.getD_numArmies();
        int l_randNumArmies = 1;
        Order l_order;
        if(l_numArmies != 0) {
            int l_randCountry = l_random.nextInt(l_currentPlayer.getD_countryList().size());
            l_randNumArmies = Math.max(1, l_random.nextInt(l_numArmies+1));
            l_order = new DeployOrder(l_currentPlayer, l_currentPlayer.getD_countryList().get(l_randCountry-1), l_randNumArmies);
            return l_order;
        }else {
            //Advance
        }
        return null;
    }
}
