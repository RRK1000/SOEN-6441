package orders;


import gamelog.LogManager;
import models.Country;
import models.Order;
import models.Player;

import java.io.Serializable;

/**
 * This class handles the DeployOrder functionality.
 *
 * @author Nimisha Jadav
 */
public class DeployOrder implements Order, Serializable {
    private final Player d_player;
    private final Country d_country;
    private final int d_num;

    /**
     * Constructor for the Order class.
     *
     * @param p_player  The player giving the order
     * @param p_country The country on which armies are deployed
     * @param p_num     The number of armies to be deployed
     */
    public DeployOrder(Player p_player, Country p_country, int p_num) {
        this.d_player = p_player;
        this.d_country = p_country;
        this.d_num = p_num;
    }


    /**
     * Returns the number of armies
     *
     * @return number of armies
     */
    public int getD_num() {
        return d_num;
    }

    /**
     * Executes the deploy order
     */
    @Override
    public void execute() {
        if (!d_player.getD_countryList().contains(d_country)) {
            LogManager.logAction("err: Execute order failed. Player no longer owns country:" + d_country.getD_countryID());
            return;
        }
        d_country.setD_numArmies(d_country.getD_numArmies() + d_num);
        LogManager.logAction("[" + d_player.getD_playerName() + "] Deploy order executed: " + d_num + " armies deployed to " + d_country.getD_countryID());

    }

    /**
     * Checks if the deploy order is valid
     *
     * @return True if command is valid, false otherwise
     */
    @Override
    public boolean isValid() {
        if (null == d_country) {
            String l_err = "err: Invalid Deploy Order, Country does not exist";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (!d_player.getD_countryList().contains(d_country)) {
            String l_err = "err: Invalid Deploy Order, Player does not own country: " + d_country.getD_countryID();
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_player.getD_numArmies() < d_num) {
            String l_err = "err: Invalid Deploy Order, Cannot deploy more armies than available in reinforcement pool.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_country.isD_isNeutral()) {
            String l_err = "err: Invalid Deploy Order, Cannot deploy on a neutral country.";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        }

        return true;
    }
}
