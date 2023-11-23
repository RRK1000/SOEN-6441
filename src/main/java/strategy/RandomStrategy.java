package strategy;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import orders.*;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy {

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
        int l_randNumArmies;
        Order l_order;
        if (l_numArmies != 0) {
            int l_randCountry = l_random.nextInt(l_currentPlayer.getD_countryList().size());
            l_randNumArmies = Math.max(1, l_random.nextInt(l_numArmies + 1));
            l_order = new DeployOrder(l_currentPlayer, l_currentPlayer.getD_countryList().get(l_randCountry), l_randNumArmies);
            return l_order;
        } else {
            // playing a cards
            if (!l_currentPlayer.getD_playerCardList().isEmpty()) {
                String l_card = l_currentPlayer.getD_playerCardList().get(l_random.nextInt(l_currentPlayer.getD_playerCardList().size()));
                switch (l_card) {
                    case Cards.AIRLIFT_CARD:
                        if (l_currentPlayer.getD_countryList().size() == 1) break;
                        Country l_randCountryFrom = l_currentPlayer.getD_countryList().get(
                                l_random.nextInt(l_currentPlayer.getD_countryList().size()));
                        Country l_randCountryTo;
                        do {
                            l_randCountryTo = l_currentPlayer.getD_countryList().get(
                                    l_random.nextInt(l_currentPlayer.getD_countryList().size()));
                        } while (l_randCountryTo != l_randCountryFrom);
                        return new AirliftOrder(l_currentPlayer, l_randCountryFrom, l_randCountryTo, l_randCountryFrom.getD_numArmies());

                        case Cards.DIPLOMACY_CARD:
                        Player l_oppPlayer;
                        do {
                            l_oppPlayer = p_gameManager.getD_playerList().get(
                                    l_random.nextInt(p_gameManager.getD_playerList().size()));
                        } while(l_oppPlayer!= l_currentPlayer);
                        return new NegotiateOrder(l_currentPlayer, l_oppPlayer);

                    case Cards.BOMB_CARD:
                        if (l_currentPlayer.getD_countryList().size() == 1) break;
                        Country l_randCountry = l_currentPlayer.getD_countryList().get(
                                l_random.nextInt(l_currentPlayer.getD_countryList().size()));
                        for(int l_neighbourID: l_randCountry.getD_neighbourCountryIDList()) {
                            if(p_gameManager.getD_map().getD_countryByID(l_neighbourID).getD_owner() != l_currentPlayer) {
                                l_randCountryTo = p_gameManager.getD_map().getD_countryByID(l_neighbourID);
                                return new BombOrder(l_currentPlayer, l_randCountryTo);
                            }
                        }

                        break;
                    case Cards.BLOCKADE_CARD:
                        l_randCountry = l_currentPlayer.getD_countryList().get(
                                l_random.nextInt(l_currentPlayer.getD_countryList().size()));
                        return new BlockadeOrder(l_currentPlayer, l_randCountry);
                }

            }

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
}
