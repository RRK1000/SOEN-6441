package strategy;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import orders.*;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy, Serializable {

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
        if(l_currentPlayer.getD_countryList().isEmpty()) return null;

        int l_numArmies = l_currentPlayer.getD_numArmies();
        int l_randNumArmies;
        Order l_order;
        if (l_numArmies != 0) {
            int l_randCountry = l_random.nextInt(l_currentPlayer.getD_countryList().size());
            l_randNumArmies = Math.max(1, l_random.nextInt(l_numArmies + 1));
            l_order = new DeployOrder(l_currentPlayer, l_currentPlayer.getD_countryList().get(l_randCountry), l_randNumArmies);
            return l_order;
        } else {
            l_order = generateCardOrder(p_gameManager, l_currentPlayer);
            if(null != l_order) return l_order;

            //Advance
            int l_tryAdvanceOrders = Math.max(1, l_currentPlayer.getD_countryList().size());
            int l_randCountryFrom = -1;
            for (int l_i = 1; l_i < l_tryAdvanceOrders; l_i++) {
                l_randCountryFrom = l_random.nextInt(l_currentPlayer.getD_countryList().size());
                if (l_currentPlayer.getD_countryList().get(l_randCountryFrom).getD_numArmies() != 0) {
                    break;
                }
            }
            if (l_randCountryFrom == -1 || l_currentPlayer.getD_countryList().get(l_randCountryFrom).getD_numArmies() <= 0)
                return null;

            int l_randAttackArmies = l_random.nextInt(l_currentPlayer.getD_countryList().get(l_randCountryFrom).getD_numArmies());
            List<Integer> l_neighborList = l_currentPlayer.getD_countryList().get(l_randCountryFrom).getD_neighbourCountryIDList();
            int l_randCountryTo = l_random.nextInt(l_neighborList.size());
            l_order = new AdvanceOrder(l_currentPlayer, l_currentPlayer.getD_countryList().get(l_randCountryFrom),
                    p_gameManager.getD_map().getD_countryByID(l_neighborList.get(l_randCountryTo)), l_randAttackArmies);
            return l_order;
        }
    }

    /**
     * Generates a random order involving an owned card
     * @param p_gameManager {@link GameManager} game manager object
     * @param p_currentPLayer {@link Player} Current player
     * @return {@link Order} generated order object, or null
     */
    private Order generateCardOrder(GameManager p_gameManager, Player p_currentPLayer) {
        Random l_random = new Random();

        // to have randomness in whether to use card
        if(l_random.nextBoolean()) return null;

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

                case Cards.BOMB_CARD:
                    if (p_currentPLayer.getD_countryList().size() == 1) break;
                    Country l_randCountry = p_currentPLayer.getD_countryList().get(
                            l_random.nextInt(p_currentPLayer.getD_countryList().size()));
                    for (int l_neighbourID : l_randCountry.getD_neighbourCountryIDList()) {
                        if (p_gameManager.getD_map().getD_countryByID(l_neighbourID).getD_owner() != p_currentPLayer) {
                            l_randCountryTo = p_gameManager.getD_map().getD_countryByID(l_neighbourID);
                            return new BombOrder(p_currentPLayer, l_randCountryTo);
                        }
                    }
                    break;

                case Cards.BLOCKADE_CARD:
                    l_randCountry = p_currentPLayer.getD_countryList().get(
                            l_random.nextInt(p_currentPLayer.getD_countryList().size()));
                    return new BlockadeOrder(p_currentPLayer, l_randCountry);
            }
        }
        return null;
    }
}
