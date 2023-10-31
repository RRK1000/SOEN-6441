package orders;

import models.Country;
import models.Order;
import models.Player;

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
        d_country.setD_numArmies(d_country.getD_numArmies() / 2);
    }

    /**
     * Validates the AdvanceOrder command against the player
     * @return boolean value whether the command can be executed or not
     */
    @Override
    public boolean isValid() {
        for (Country l_country : d_player.getD_countryList()) {
            if (l_country.getD_neighbourCountryIDList().contains(d_country.getD_countryID())) return true;
        }
        return false;
    }

}
