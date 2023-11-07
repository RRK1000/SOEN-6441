package orders;


import gamelog.LogManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;

import java.util.List;

/**
 * This class handles the bomb type order.
 *
 * @author Nimisha Jadav
 */
public class BombOrder implements Order {
    private final Player d_player;
    private final Country d_country;

    
    /**
     * Constructor for the BombOrder class
     * @param p_player The player giving the order
     * @param p_country The country to be bombed
     */
    public BombOrder(Player p_player, Country p_country) {
        this.d_player = p_player;
        this.d_country = p_country;
    }


    /**
     * Executes the bomb order command
     */
    @Override
    public void execute() {
        if (d_player.isInNegotiationWith(d_country.getD_owner())) {
            String l_err = "err: Invalid Bomb Order, Diplomacy Card played, peace enforced between players";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return;
        }
        d_country.setD_numArmies(d_country.getD_numArmies() / 2);
        List<String> l_playerCardList = d_player.getD_playerCardList();
        l_playerCardList.remove(Cards.BOMB_CARD);
        d_player.setD_playerCardList(l_playerCardList);
        LogManager.logAction("Bomb order executed: Halved armies in " + d_country.getD_countryName());
    }

    /**
     * Validates the Bomb Order command against the player
     *
     * @return boolean value whether the command can be executed or not
     */
    @Override
    public boolean isValid() {
        if (!d_player.getD_playerCardList().contains(Cards.BOMB_CARD)) {
            String l_err = "err: Invalid Bomb Order, Player doesn't have Bomb Card.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if(d_country.isD_isNeutral()) {
            String l_err = "err: Invalid Bomb Order, Cannot deploy on a neutral country.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        }

        for (Country l_country : d_player.getD_countryList()) {
            if (l_country.getD_neighbourCountryIDList().contains(d_country.getD_countryID())) return true;
        }

        String l_err = "err: Invalid Bomb Order, Country not a neighbour";
        System.out.println(l_err);
        LogManager.logAction(l_err);
        return false;
    }

}
