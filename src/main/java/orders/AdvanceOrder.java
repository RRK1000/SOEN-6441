package orders;

import java.nio.file.Path;
import java.nio.file.Paths;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
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
    private final Country d_countryfrom;
    private final Country d_countryto;
    private final int d_num;
    
    private static LogEntryBuffer d_logBuffer;
    private static LogFileWriter d_logWriter;
    
    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }


    /**
     * Constructor for the AdvanceOrder class
     */
    public AdvanceOrder(Player p_player, Country p_countryfrom, Country p_countryto, int p_num) {
        this.d_player = p_player;
        this.d_countryfrom = p_countryfrom;
        this.d_countryto = p_countryto;
        this.d_num = p_num;
    }

    private static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
        d_logBuffer.notifyObservers();
    }
    
    
    /**
     * Executes the AdvanceOrder command
     */
    @Override
    public void execute() {
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
        
        logAction("Advance order executed: " + d_num + " armies moved from " +
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
            System.out.println("Player does not own country: " + d_countryfrom.getD_countryID());
            logAction("Player does not own country: " + d_countryfrom.getD_countryID());
            return false;
        } else if (d_num == d_countryfrom.getD_numArmies()) {
            System.out.println("Invalid order, one army must remain on all territories");
            logAction("Invalid Order: One army must remain on all territories ");
            return false;
        } else if (d_num > d_countryfrom.getD_numArmies()) {
            System.out.println("Invalid order, available armies on country: " + d_countryfrom.getD_numArmies());
            logAction("Invalid Order:  available armies on country"  + d_countryfrom.getD_numArmies()  );

            return false;
        } else if (!d_countryfrom.getD_neighbourCountryIDList().contains(d_countryto.getD_countryID())) {
            System.out.println("Country being attacked is not a neighbour");
            logAction("Invalid Order:Country being attacked is not a neighbour" );

            return false;
        } else if(d_player.isInNegotiationWith(d_countryto.getD_owner())) {
            System.out.println("Diplomacy Card played, peace enforced between players");
            logAction("Invalid Order: Diplomacy Card played, peace enforced between players");

        }
        return true;
    }
}
