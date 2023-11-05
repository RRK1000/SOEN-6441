package orders;

import global.Cards;
import models.Order;
import models.Map;
import models.Country;
import models.Player;

/**
 * This class handles the blockade type order.
 *
 * @author Nimisha Jadav
 * @author Abhigyan
 */
public class BlockadeOrder implements Order {
    /**
     * Constructor for the Order class.
     */
    private final Country d_country;

    private final Player d_player;
    /**
     * Constructing a new BlockadeOrder with the specified target country and player.
     *
     * @param p_Country The target country to be blocked.
     * @param p_Player  The player issuing the blockade order.
     */

    public BlockadeOrder(Country p_Country, Player p_Player) {
        d_country = p_Country;
        d_player = p_Player;
    }

    public void add_NeutralCountry(Country p_country) {
        p_country.setD_owner(null);
        p_country.setD_numArmies(d_player.getD_numArmies()*3);
    }


    /**
     * Makes a country neutral and removes it from the player's list if the Blockade order is valid.
     */
    public void execute() {
        if (isValid()) {
            int currentArmies = d_country.getD_numArmies();
            d_country.setD_numArmies(currentArmies * 3);
            add_NeutralCountry(d_country); // Neutralize the country
            d_player.getD_countryList().remove(d_country); // Remove the country from the player's list
            d_player.getD_playerCardList().remove(Cards.BLOCKADE_CARD);

            System.out.println("Blockade on " + d_country.getD_countryName() + " by " + d_player.getD_playerName());
        }
    }

    /**
     * Checks the validity of the Blockade order.
     *
     * @return True if the order is valid; false otherwise.
     */
    @Override
    public boolean isValid() {
        if (d_player == null) {
            System.err.println("The Player is not valid.");
            return false;
        }

        if (d_country.getD_owner() != d_player) {
            System.err.println("The target country does not belong to the player");
            return false;
        }
        if (!d_player.getD_playerCardList().contains(Cards.BLOCKADE_CARD)) {
            System.err.println("Player doesn't have Blockade Card.");
            return false;
        }
        return true;
    }
}
