package strategy;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import orders.*;

import java.util.List;
import java.util.Random;

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

        if(null == l_weakestCountry) return null;
        Order l_order = null;
                
        if(l_currentPlayer.getD_numArmies() != 0) {
            l_order = new DeployOrder(l_currentPlayer, l_weakestCountry, l_currentPlayer.getD_numArmies());
        } else if (l_currentPlayer.getD_playerCardList().contains(Cards.AIRLIFT_CARD)){
            Country l_countryFrom = getStrongestCountry(l_countries);
            l_order = new AirliftOrder(l_currentPlayer, l_countryFrom, l_weakestCountry, l_countryFrom.getD_numArmies()-1);
        } else if(l_currentPlayer.getD_playerCardList().contains(Cards.DIPLOMACY_CARD) && !l_currentPlayer.getD_countryList().isEmpty()){
            Player l_randomPlayer = getRandomPlayer(l_currentPlayer, p_gameManager);
            l_order = new NegotiateOrder(l_currentPlayer, l_randomPlayer);
        } else if(l_currentPlayer.getD_playerCardList().contains(Cards.BLOCKADE_CARD)){
            l_order = new BlockadeOrder(l_currentPlayer, l_weakestCountry);
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

    private Country getStrongestCountry(List<Country> p_countryList) {
        Country l_strongestCountry = null;
        int l_maxArmies = Integer.MIN_VALUE;
        for (Country l_c: p_countryList) {
            if(l_c.getD_numArmies() > l_maxArmies) {
                l_strongestCountry = l_c;
                l_maxArmies = l_c.getD_numArmies();
            }
        }
        return l_strongestCountry;
    }

    private Player getRandomPlayer(Player p_currentPlayer, GameManager p_gameManager){
        Random l_random = new Random();
        Player l_oppPlayer;
        do {
            l_oppPlayer = p_gameManager.getD_playerList().get(
                    l_random.nextInt(p_gameManager.getD_playerList().size()));
        } while(l_oppPlayer!= p_currentPlayer);
        return l_oppPlayer;
    }
}
