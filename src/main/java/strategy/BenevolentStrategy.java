package strategy;

import controller.GameManager;
import models.Country;
import models.Order;
import models.Player;
import orders.AdvanceOrder;
import orders.DeployOrder;

import java.util.List;

public class BenevolentStrategy implements Strategy{

    /**
     * Creates an order according to player strategy
     *
     * @param p_gameManager {@link GameManager}
     * @return {@link Order}
     */
    @Override
    public Order createOrder(GameManager p_gameManager) {
        Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
        List<Country> l_countries = l_currentPlayer.getD_countryList();
        Country l_weakestCountry = getWeakestCountry(l_countries);
        Order l_order = null;
                
        if(l_currentPlayer.getD_numArmies() != 0) {
            l_order = new DeployOrder(l_currentPlayer, l_weakestCountry, l_currentPlayer.getD_numArmies());
        } else {
            List<Integer> l_neighbours = l_weakestCountry.getD_neighbourCountryIDList();
            for (int l_neighborCountryID: l_neighbours) {
                Country l_neighborCountry = p_gameManager.getD_map().getD_countryByID(l_neighborCountryID);
                if(l_neighborCountry.getD_numArmies() > 1 && l_neighborCountry.getD_owner().equals(l_currentPlayer)) {
                    l_order = new AdvanceOrder(l_currentPlayer, l_neighborCountry, l_weakestCountry, l_neighborCountry.getD_numArmies()-1);
                }
            }
        }
        return l_order;
    }

    private Country getWeakestCountry(List<Country> p_countries) {
        Country l_weakestCountry = null;
        int l_lowestArmies = Integer.MAX_VALUE;
        for (Country l_country: p_countries) {
            if(l_lowestArmies > l_country.getD_numArmies()) {
                l_weakestCountry = l_country;
                l_lowestArmies = l_country.getD_numArmies();
            }
        }
        return l_weakestCountry;
    }
}
