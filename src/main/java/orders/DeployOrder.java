package orders;


import java.nio.file.Path;
import java.nio.file.Paths;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
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
    
    private static LogEntryBuffer d_logBuffer;
    private static LogFileWriter d_logWriter;

    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "src/main/resources", "game.log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }
    /**
     * Constructor for the Order class.
     */
    public DeployOrder(Player p_player, Country p_country, int p_num) {
        this.d_player = p_player;
        this.d_country = p_country;
        this.d_num = p_num;
    }
    
    private static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
        d_logBuffer.notifyObservers();
    }

    
    public int getD_num() {
        return d_num;
    }

    @Override
    public void execute() {
        if(!d_player.getD_countryList().contains(d_country)) {
            System.out.println("Player no longer owns country: " + d_country.getD_countryID());
            return;
        }
        d_country.setD_numArmies(d_country.getD_numArmies() + d_num);
        logAction("Deploy order executed: " + d_num + " armies deployed to " + d_country.getD_countryName());

    }

    @Override
    public boolean isValid() {
        if(!d_player.getD_countryList().contains(d_country)) {
            System.out.println("Player does not own country: " + d_country.getD_countryID());
            logAction("Invalid Deploy Order: Player does not own country: " + d_country.getD_countryID());
            return false;
        } else if(d_player.getD_numArmies() < d_num) {
            System.out.println("Cannot deploy more armies than available in reinforcement pool.");
            logAction("Invalid Deploy Order: Cannot deploy more armies than available in reinforcement pool.");
            return false;
        }

        return true;
    }
}
