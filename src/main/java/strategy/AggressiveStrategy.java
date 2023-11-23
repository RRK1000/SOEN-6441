package strategy;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import orders.AdvanceOrder;
import orders.BombOrder;
import orders.DeployOrder;

import java.util.List;

public class AggressiveStrategy implements Strategy {
    private Country d_strongestCountry;

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
        Country l_strongestCountry = getStrongestCountry(l_countries);
        Order l_order = null;

        if (null == l_strongestCountry) return l_order;

        if (l_currentPlayer.getD_numArmies() != 0) {
            l_order = new DeployOrder(l_currentPlayer, l_strongestCountry, l_currentPlayer.getD_numArmies());
        } else if (l_currentPlayer.getD_playerCardList().contains(Cards.BOMB_CARD) && l_currentPlayer.getD_countryList().isEmpty()) {
            Country l_countryToBomb = getUnownedNeighbor(l_currentPlayer, p_gameManager);
            if(l_countryToBomb != null){
                l_order = new BombOrder(l_currentPlayer, l_countryToBomb);
            }
        } else {
            List<Integer> l_neighbours = l_strongestCountry.getD_neighbourCountryIDList();

            // attacking to a non-owned neighbour from the strongest country
            for (int l_neighborCountryID : l_neighbours) {
                Country l_neighborCountry = p_gameManager.getD_map().getD_countryByID(l_neighborCountryID);
                if (!l_neighborCountry.getD_owner().equals(l_currentPlayer)) {
                    l_order = new AdvanceOrder(l_currentPlayer, l_strongestCountry, l_neighborCountry, l_strongestCountry.getD_numArmies() - 1);
                    if (!l_order.isValid()) {
                        l_order = null;
                        continue;
                    }
                    return l_order;
                }
            }

            // reinforcing the strongest country by moving neighbouring armies to the strongest country
            for (int l_neighborCountryID : l_neighbours) {
                Country l_neighborCountry = p_gameManager.getD_map().getD_countryByID(l_neighborCountryID);
                if (l_neighborCountry.getD_numArmies() > 1 && l_neighborCountry.getD_owner().equals(l_currentPlayer)) {
                    l_order = new AdvanceOrder(l_currentPlayer, l_neighborCountry, l_strongestCountry, l_neighborCountry.getD_numArmies() - 1);
                    if (!l_order.isValid()) {
                        l_order = null;
                        continue;
                    }
                    return l_order;
                }
            }
        }
        if(null == l_order) d_strongestCountry = null;
        return l_order;
    }

    private Country getStrongestCountry(List<Country> p_countryList) {
        if(null != d_strongestCountry)  return d_strongestCountry;

        Country l_strongestCountry = null;
        int l_maxArmies = Integer.MIN_VALUE;
        for (Country l_c : p_countryList) {
            if (l_c.getD_numArmies() > l_maxArmies) {
                l_strongestCountry = l_c;
                l_maxArmies = l_c.getD_numArmies();
            }
        }
        return l_strongestCountry;
    }

    private Country getUnownedNeighbor(Player p_currentPlayer, GameManager p_gameManager){
        Country l_neighbor;
        for(Country l_country: p_currentPlayer.getD_countryList()){
            for (int l_neighborID: l_country.getD_neighbourCountryIDList()){
                l_neighbor = p_gameManager.getD_map().getD_countryByID(l_neighborID);
                if(!l_neighbor.getD_owner().equals(p_currentPlayer)){
                    return l_neighbor;
                }
            }
        }
        return null;

    }
}
