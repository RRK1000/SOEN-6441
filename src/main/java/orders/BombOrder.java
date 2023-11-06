package orders;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
import global.Cards;
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
    private static LogEntryBuffer d_logBuffer;
    private static LogFileWriter d_logWriter;

    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }
    
    /**
     * Constructor for the BombOrder class
     */
    public BombOrder(Player p_player, Country p_country) {
        this.d_player = p_player;
        this.d_country = p_country;
    }

    private static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
        d_logBuffer.notifyObservers();
    }
    /**
     * Executes the bomb order command
     */
    @Override
    public void execute() {
        d_country.setD_numArmies(d_country.getD_numArmies() / 2);
        List<String> l_playerCardList = d_player.getD_playerCardList();
        l_playerCardList.remove(Cards.BOMB_CARD);
        d_player.setD_playerCardList(l_playerCardList);
        logAction("Bomb order executed: Halved armies in " + d_country.getD_countryName());
    }

    /**
     * Validates the Bomb Order command against the player
     *
     * @return boolean value whether the command can be executed or not
     */
    @Override
    public boolean isValid() {
        if (!d_player.getD_playerCardList().contains(Cards.BOMB_CARD)) {
            System.out.println("Player doesn't have Bomb Card.");
            logAction("Player doesn't have Bomb Card.");

            return false;
        }

        for (Country l_country : d_player.getD_countryList()) {
            if (l_country.getD_neighbourCountryIDList().contains(d_country.getD_countryID())) return true;
        }
        System.out.println("Country not a neighbour");
        logAction("Country not a neighbour for Bomb Order.");
        return false;
    }

}
