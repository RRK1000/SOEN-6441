package strategy;

import controller.GameManager;
import gamelog.LogManager;
import models.Country;
import models.Order;
import models.Player;

public class CheaterStrategy implements Strategy {

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

        for (Country l_country : l_currentPlayer.getD_countryList()) {
            for (int l_neighbourCountryId : l_country.getD_neighbourCountryIDList()) {
                Country l_neighbour = p_gameManager.getD_map().getD_countryByID(l_neighbourCountryId);
                if(l_neighbour.getD_owner() != l_currentPlayer)
                    l_neighbour.getD_owner().getD_countryList().remove(l_neighbour);
                l_neighbour.setD_owner(l_currentPlayer);
                LogManager.logAction("CountryID " + l_neighbour.getD_countryID() + " conquered by " + l_currentPlayer.getD_playerName());
            }
        }
        for (Country l_country : l_currentPlayer.getD_countryList()) {
            for (int l_neighbourCountryId : l_country.getD_neighbourCountryIDList()) {
                Country l_neighbour = p_gameManager.getD_map().getD_countryByID(l_neighbourCountryId);
                if (l_neighbour.getD_owner() != l_currentPlayer) {
                    l_country.setD_numArmies(2 * l_country.getD_numArmies());
                    break;
                }
            }
        }
        return null;
    }
}
