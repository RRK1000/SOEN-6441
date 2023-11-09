package orders;

import gamelog.LogManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;

import java.util.List;

/**
 * This class handles the airlift type order.
 *
 * @author Nimisha Jadav
 * @author Anuja-Somthankar
 */
public class AirliftOrder implements Order {

    private final Player d_player;
    private final Country d_sourceCountry;
    private final Country d_targetCountry;
    private final int d_numArmies;


    /**
     * Constructor for the Order class.
     *
     * @param p_player        The player giving the order
     * @param p_sourceCountry The country from which armies would be airlifted
     * @param p_targetCountry The country to which armies would be airlifted
     * @param p_numArmies     The number of armies to be airlifted
     */
    public AirliftOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_numArmies) {
        d_player = p_player;
        d_sourceCountry = p_sourceCountry;
        d_targetCountry = p_targetCountry;
        d_numArmies = p_numArmies;

    }


    /**
     * Executes the airlift order
     */
    @Override
    public void execute() {
        if (!d_player.getD_countryList().contains(d_sourceCountry)) {
            LogManager.logAction("err: Execute Airlift Order failed. Player no longer owns country:" + d_sourceCountry.getD_countryID());
            return;
        }

        d_sourceCountry.setD_numArmies(d_sourceCountry.getD_numArmies() - d_numArmies);
        d_targetCountry.setD_numArmies(d_targetCountry.getD_numArmies() + d_numArmies);
        List<String> l_playerCardList = d_player.getD_playerCardList();
        l_playerCardList.remove(Cards.BOMB_CARD);
        d_player.setD_playerCardList(l_playerCardList);
        LogManager.logAction("Airlift order executed: " + d_numArmies + " armies moved from " +
                d_sourceCountry.getD_countryID() + " to " + d_targetCountry.getD_countryID());

    }

    /**
     * Checks if the airlift order is valid
     *
     * @return True if the command is valid, false otherwise
     */
    @Override
    public boolean isValid() {
        if (!d_player.getD_countryList().contains(d_sourceCountry)) {
            String l_err = "err: Invalid Airlift Order, Player does not own source country: " + d_sourceCountry.getD_countryID();
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_numArmies == d_sourceCountry.getD_numArmies()) {
            String l_err = "err: Invalid Airlift Order, one army must remain on all territories";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_numArmies > d_sourceCountry.getD_numArmies()) {
            String l_err = "err: Invalid Airlift Order, available armies on country: " + d_sourceCountry.getD_numArmies();
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (!d_player.getD_countryList().contains(d_targetCountry)) {
            String l_err = "err: Invalid Airlift Order, Player does not own target country: " + d_targetCountry.getD_countryID();
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (!d_player.getD_playerCardList().contains(Cards.AIRLIFT_CARD)) {
            String l_err = "err: Invalid Airlift Order, Player doesn't have Airlift Card.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_targetCountry.isD_isNeutral()) {
            String l_err = "err: Invalid Airlift Order, Cannot deploy on a neutral country.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_sourceCountry.isD_isNeutral()) {
            String l_err = "err: Invalid Airlift Order, Cannot deploy on a neutral country.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        }
        return true;
    }
}
