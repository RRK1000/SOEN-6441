package phases;

import controller.GameManager;
import gamelog.LogManager;
import global.Constants;
import global.Strategies;
import models.Country;
import models.Player;
import strategy.*;

import java.util.List;

/**
 * This class implements the commands in the Start up phase
 *
 * @author Anuja Somthankar
 */
public class StartupPhase extends Phase {

    /**
     * Singleton instance
     */
    private static StartupPhase l_instance;

    /**
     * Private constructor to present instantiation
     */
    public StartupPhase(){}

    /**
     * Get the singleton instance for StartupPhase
     * @return StartupPhase instance
     */
    public static StartupPhase getInstance(){
        if(l_instance==null){
            l_instance= new StartupPhase();
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
        LogManager.logAction("Phase changed from StartupPhase to IssueOrderPhase\n");
        return IssueOrderPhase.getInstance();
    }

    /**
     * This method adds/removes the game players
     *
     * @param p_cmdSplit    The input given by the user to add/remove players
     * @param p_gameManager The game manager object
     */
    @Override
    public void gamePlayer(String[] p_cmdSplit, GameManager p_gameManager) {
        //Handles the case to add or remove player
        for (int l_i = 1; l_i < p_cmdSplit.length - 1; l_i++) {
            if (p_cmdSplit[l_i].startsWith("-add")
                    && !p_cmdSplit[l_i + 1].startsWith("-")) {
                String l_playername = p_cmdSplit[l_i + 1];
                String l_strategy = p_cmdSplit[l_i + 2];
                Strategy l_playerStrategy;
                switch (l_strategy) {
                    case Strategies.HUMAN_STRATEGY:
                        l_playerStrategy = new HumanStrategy();
                        break;
                    case Strategies.AGGRESSIVE_STRATEGY:
                        l_playerStrategy = new AggressiveStrategy();
                        break;
                    case Strategies.BENEVOLENT_STRATEGY:
                        l_playerStrategy = new BenevolentStrategy();
                        break;
                    case Strategies.CHEATER_STRATEGY:
                        l_playerStrategy = new CheaterStrategy();
                        break;
                    case Strategies.RANDOM_STRATEGY:
                        l_playerStrategy = new RandomStrategy();
                        break;
                    default:
                        System.out.println(Constants.CMD_ERROR);
                        return;
                }
                //calls addPlayer() to add the Player in the game
                p_gameManager.addPlayer(l_playername, l_playerStrategy);
            } else if (p_cmdSplit[l_i].startsWith("-remove")
                    && !p_cmdSplit[l_i + 1].startsWith("-")) {
                String l_playername = p_cmdSplit[l_i + 1];
                //calls removePlayer() to remove Player from the game
                p_gameManager.removePlayer(l_playername);
            }
        }
    }

    /**
     * Used in the Game_Startup game phase to assign countries to the players in the game
     *
     * @param p_gameManager The game manager object
     */
    @Override
    public void assignCountries(GameManager p_gameManager) {
        List<Player> l_playerList = p_gameManager.getD_playerList();
        //Checks the condition where the armies cannot be assigned if the number of players is less than 2.
        if (l_playerList.size() < 2) {
            System.out.println("Too few players added. Minimum players required is 2");
            return;
        }

        //Assigning countries to the player
        int l_playerIndex = 0;
        for (Country l_country : p_gameManager.getD_map().getD_countryMapGraph().vertexSet()) {
            Player l_player = l_playerList.get(l_playerIndex);
            l_player.addCountry(l_country);
            l_country.setD_owner(l_player);
            l_playerIndex = ((l_playerIndex + 1) % l_playerList.size());
        }
        //Setting the game phase to Issue Order and assign armies to the players
        System.out.println("Assigned countries to the players");
        System.out.println("Game has Started!");
        p_gameManager.assignReinforcements();

        //Displaying the current player's name and the armies
        p_gameManager.setD_currentPlayerTurn(0);
        int l_currentPlayerTurn = p_gameManager.getD_currentPlayerTurn();
        System.out.println("Player " + l_playerList.get(l_currentPlayerTurn).getD_playerName() + "'s turn");
        System.out.println("Available Reinforcement Armies: " + l_playerList.get(l_currentPlayerTurn).getD_numArmies());

        p_gameManager.setD_gamePhase(this.nextPhase());
    }
}
