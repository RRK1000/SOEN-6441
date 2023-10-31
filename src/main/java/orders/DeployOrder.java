package orders;

import models.Country;
import models.Order;
import models.Player;

/**
 * This class handles the DeployOrder functionality.
 *
 * @author Nimisha Jadav
 */
public class DeployOrder implements Order {
    private final Player d_player;
    private final Country d_country;
    private final int d_num;

    /**
     * Constructor for the Order class.
     */
    public DeployOrder(Player p_player, Country p_country, int p_num) {
        this.d_player = p_player;
        this.d_country = p_country;
        this.d_num = p_num;
    }

    public int getD_num() {
        return d_num;
    }

    @Override
    public void execute() {
        d_country.setD_numArmies(d_country.getD_numArmies() + d_num);
    }

    @Override
    public boolean isValid() {
        return d_player.getD_countryList().contains(d_country) && d_player.getD_numArmies() >= d_num;
    }
}
