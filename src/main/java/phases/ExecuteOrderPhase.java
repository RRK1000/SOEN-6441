package phases;

import controller.GameManager;
import gamelog.LogManager;
import global.Constants;
import models.Country;
import models.Map;
import models.Order;
import models.Player;

/**
 * This class implements the commands in the execute phase
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 */
public class ExecuteOrderPhase extends Phase {

    /**
     * Singleton instance
     */
    private static ExecuteOrderPhase l_instance;

    /**
     * Private constructor to present instantiation
     */
    private ExecuteOrderPhase(){}

    /**
     * Get the singleton instance for ExecuteOrderPhase
     * @return ExecuteOrderPhase instance
     */
    public static ExecuteOrderPhase getInstance(){
        if(l_instance==null){
            l_instance= new ExecuteOrderPhase();
        }
        return l_instance;
    }
    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    @Override
    public Phase nextPhase() {
        LogManager.logAction("Phase changed from ExecuteOrderPhase to IssueOrderPhase\n");
        return IssueOrderPhase.getInstance();
    }

    /**
     * Executes all the orders from all the players for the current turn, updating the game state
     */
    public void executeOrder(GameManager p_gameManager) {
        for (Player l_player : p_gameManager.getD_playerList()) {
            Order l_order = l_player.nextOrder();
            while (null != l_order) {
                l_order.execute();
                l_order = l_player.nextOrder();
            }
        }
        System.out.println("Orders have been executed for this round.");
        LogManager.logAction("Orders have been executed for this round.");
        p_gameManager.updatePlayerList();
    }
}
