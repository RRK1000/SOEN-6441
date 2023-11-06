package orders;

import java.nio.file.Path;
import java.nio.file.Paths;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;

/**
 * This class handles the blockade type order.
 *
 * @author Nimisha Jadav
 * @author Abhigyan
 */
public class BlockadeOrder implements Order {
    private final Country d_country;

    private final Player d_player;
    
    private static LogEntryBuffer d_logBuffer;
    private static LogFileWriter d_logWriter;

    
    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }

    
    /**
     * Constructing a new BlockadeOrder with the specified target country and player.
     *
     * @param p_player  The player issuing the blockade order
     * @param p_country The target country to be blocked
     */
    public BlockadeOrder(Player p_player, Country p_country) {
        d_country = p_country;
        d_player = p_player;
    }
    
    private static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
        d_logBuffer.notifyObservers();
    }

    private void neutralizeCountry() {
        d_country.setD_numArmies(d_country.getD_numArmies() * 3);
        d_country.setD_isNeutral(true);
        logAction("Country neutralized: " + d_country.getD_countryID());

    }

    /**
     * Makes a country neutral and removes it from the player's list if the Blockade order is valid.
     */
    public void execute() {
        if(!d_player.getD_countryList().contains(d_country)) {
            System.out.println("Player no longer owns country: " + d_country.getD_countryID());
            logAction("Player no longer owns country: " + d_country.getD_countryID());
            return;
        }

        this.neutralizeCountry();
        d_player.getD_playerCardList().remove(Cards.BLOCKADE_CARD);
        System.out.println("Blockade on " + d_country.getD_countryName() + " by " + d_player.getD_playerName());
        logAction("Blockade executed on " + d_country.getD_countryName() + " by " + d_player.getD_playerName());

    }

    /**
     * Checks the validity of the Blockade order.
     *
     * @return True if the order is valid; false otherwise.
     */
    @Override
    public boolean isValid() {
        if (d_country.getD_owner() != d_player) {
            System.out.println("The country does not belong to the player");
            logAction(" Invalid Blockade Order: The country does not belong to the player");
            return false;
        } else if (!d_player.getD_playerCardList().contains(Cards.BLOCKADE_CARD)) {
            System.out.println("Player doesn't have Blockade Card.");
            logAction("Invalid Blockade Order: Player doesn't have Blockade Card.");
            return false;
        }
        return true;
    }
}
