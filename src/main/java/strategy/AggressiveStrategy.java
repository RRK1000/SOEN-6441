package strategy;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import orders.*;

import java.util.List;
import java.util.Random;

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
        if(l_currentPlayer.getD_countryList().isEmpty()) return null;

        List<Country> l_countries = l_currentPlayer.getD_countryList();
        Country l_strongestCountry = getStrongestCountry(l_countries);
        Order l_order = null;

        if (null == l_strongestCountry) return l_order;

        if (l_currentPlayer.getD_numArmies() != 0) {
            l_order = new DeployOrder(l_currentPlayer, l_strongestCountry, l_currentPlayer.getD_numArmies());
        } else if (l_currentPlayer.getD_playerCardList().contains(Cards.BOMB_CARD) && !l_currentPlayer.getD_countryList().isEmpty()) {
            Country l_countryToBomb = getUnownedNeighbor(l_currentPlayer, p_gameManager);
            if(l_countryToBomb != null){
                l_order = new BombOrder(l_currentPlayer, l_countryToBomb);
            }
        } else {
            l_order = generateCardOrder(p_gameManager, l_currentPlayer);
            if(null != l_order) return l_order;

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

    /**
     * Iterates through the countries and finds the country with the most deployed armies
     * @param p_countryList List of player owned countries
     * @return {@link Country} with the most deployed armies
     */
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

    /**
     * Finds a country that's a neighbour and unowned by the current player
     * @param p_currentPlayer {@link Player} Current player
     * @param p_gameManager {@link GameManager} game manager object
     * @return Neighbor {@link Country}
     */
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

    /**
     * Generates a random order involving an owned card
     * @param p_gameManager {@link GameManager} game manager object
     * @param p_currentPLayer {@link Player} Current player
     * @return {@link Order} generated order object, or null
     */
    private Order generateCardOrder(GameManager p_gameManager, Player p_currentPLayer) {
        Random l_random = new Random();

        if (!p_currentPLayer.getD_playerCardList().isEmpty()) {
            String l_card = p_currentPLayer.getD_playerCardList().get(l_random.nextInt(p_currentPLayer.getD_playerCardList().size()));
            switch (l_card) {
                case Cards.AIRLIFT_CARD:
                    if (p_currentPLayer.getD_countryList().size() == 1) break;
                    Country l_randCountryFrom = p_currentPLayer.getD_countryList().get(
                            l_random.nextInt(p_currentPLayer.getD_countryList().size()));
                    Country l_randCountryTo;
                    do {
                        l_randCountryTo = p_currentPLayer.getD_countryList().get(
                                l_random.nextInt(p_currentPLayer.getD_countryList().size()));
                    } while (l_randCountryTo != l_randCountryFrom);
                    return new AirliftOrder(p_currentPLayer, l_randCountryFrom, l_randCountryTo, l_randCountryFrom.getD_numArmies());

                case Cards.DIPLOMACY_CARD:
                    Player l_oppPlayer;
                    do {
                        l_oppPlayer = p_gameManager.getD_playerList().get(
                                l_random.nextInt(p_gameManager.getD_playerList().size()));
                    } while (l_oppPlayer != p_currentPLayer);
                    return new NegotiateOrder(p_currentPLayer, l_oppPlayer);

                case Cards.BLOCKADE_CARD:
                    Country l_randCountry = p_currentPLayer.getD_countryList().get(
                            l_random.nextInt(p_currentPLayer.getD_countryList().size()));
                    return new BlockadeOrder(p_currentPLayer, l_randCountry);
            }
        }
        return null;
    }
}
