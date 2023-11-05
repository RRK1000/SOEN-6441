package orders;

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
     */
    public AirliftOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_numArmies) {
        d_player = p_player;
        d_sourceCountry = p_sourceCountry;
        d_targetCountry = p_targetCountry;
        d_numArmies = p_numArmies;
    }

    @Override
    public void execute() {
        if(!d_player.getD_countryList().contains(d_sourceCountry)) {
            System.out.println("Player no longer owns country: " + d_sourceCountry.getD_countryID());
            return;
        }

        d_sourceCountry.setD_numArmies(d_sourceCountry.getD_numArmies() - d_numArmies);
        d_targetCountry.setD_numArmies(d_targetCountry.getD_numArmies() + d_numArmies);
        List<String> l_playerCardList = d_player.getD_playerCardList();
        l_playerCardList.remove(Cards.BOMB_CARD);
        d_player.setD_playerCardList(l_playerCardList);
    }

    @Override
    public boolean isValid() {
        if(!d_player.getD_countryList().contains(d_sourceCountry)){
            System.out.println("Player does not own country: " + d_sourceCountry.getD_countryID());
            return false;
        } else if (d_numArmies == d_sourceCountry.getD_numArmies()) {
            System.out.println("Invalid order, one army must remain on all territories");
            return false;
        } else if (d_numArmies > d_sourceCountry.getD_numArmies()) {
            System.out.println("Invalid order, available armies on country: " + d_sourceCountry.getD_numArmies());
            return false;
        } else if (!d_player.getD_countryList().contains(d_targetCountry)) {
            System.out.println("Player does not own country: " + d_targetCountry.getD_countryID());
            return false;
        } else if (!d_player.getD_playerCardList().contains(Cards.AIRLIFT_CARD)) {
            System.out.println("Player doesn't have Airlift Card.");
            return false;
        }
        return true;
    }
}
