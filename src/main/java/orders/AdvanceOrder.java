package orders;

import gamelog.LogManager;
import models.Country;
import models.Order;
import models.Player;

/**
 * This class handles the advance type order.
 *
 * @author Nimisha Jadav
 * @author Rishi Ravikumar
 */
public class AdvanceOrder implements Order {
    private final Player d_player;

    public Country getD_countryfrom() {
        return d_countryfrom;
    }

    private final Country d_countryfrom;
    private final Country d_countryto;
    private final int d_num;
    



    /**
     * Constructor for the AdvanceOrder class
     * @param p_player The player giving the advance order
     * @param p_countryfrom The country from which armies would be advanced
     * @param p_countryto The country to which armies would be advanced
     * @param p_num The number of armies involved
     */
    public AdvanceOrder(Player p_player, Country p_countryfrom, Country p_countryto, int p_num) {
        this.d_player = p_player;
        this.d_countryfrom = p_countryfrom;
        this.d_countryto = p_countryto;
        this.d_num = p_num;
    }

    public int getD_num() {
        return d_num;
    }

    /**
     * Executes the AdvanceOrder command
     */
    @Override
    public void execute() {
        if (d_num == d_countryfrom.getD_numArmies()) {
            LogManager.logAction("err: one army must remain on all territories");
            return;
        } else if (d_num > d_countryfrom.getD_numArmies()) {
            LogManager.logAction("err: Invalid order, armies unavailable on country.");
            return;
        }

        int l_attackingArmies = (int) (d_num * 0.6);

        int l_defendingArmies = (int) (d_countryto.getD_numArmies() * 0.7);


        // after the attack
        if (l_attackingArmies >= l_defendingArmies) {
            Player l_formerOwner = d_countryto.getD_owner();
            l_formerOwner.getD_countryList().remove(d_countryto);
            d_countryto.setD_owner(d_player);
            d_player.getD_countryList().add(d_countryto);
            d_countryto.setD_numArmies(0);
            d_player.addRandomCard();
        }
        d_countryto.setD_numArmies(Math.max(l_defendingArmies - l_attackingArmies, d_num - l_defendingArmies));
        d_countryfrom.setD_numArmies(d_countryfrom.getD_numArmies() - d_num);
        
        LogManager.logAction("Advance order executed: " + d_num + " armies moved from " +
                d_countryfrom.getD_countryID() + " to " + d_countryto.getD_countryID());
    }

    /**
     * Validates the AdvanceOrder command against the player
     *
     * @return boolean value whether the command can be executed or not
     */
    @Override
    public boolean isValid() {
        if (!d_player.getD_countryList().contains(d_countryfrom)) {
            String l_err = "err: Invalid Advance Order. Player does not own country: " + d_countryfrom.getD_countryID();
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_num == d_countryfrom.getD_numArmies()) {
            String l_err = "err: Invalid Advance Order. Invalid order, one army must remain on all territories";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_num > d_countryfrom.getD_numArmies()) {
            String l_err = "err: Invalid Advance Order. Player does not own country: " + d_countryfrom.getD_countryID();
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (!d_countryfrom.getD_neighbourCountryIDList().contains(d_countryto.getD_countryID())) {
            String l_err = "err: Invalid Advance Order. Country being attacked is not a neighbour";
            System.out.println(l_err);
            LogManager.logAction(l_err);
            return false;
        } else if (d_player.isInNegotiationWith(d_countryto.getD_owner())) {
            String l_err = "err: Invalid Advance Order. Diplomacy Card played, peace enforced between players";
            System.out.println(l_err);
            LogManager.logAction(l_err);
        }
        return true;
    }
}
