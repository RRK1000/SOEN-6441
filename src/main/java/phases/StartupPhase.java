package phases;

import controller.GameManager;
import gamelog.LogManager;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;

import java.util.List;

/**
 * This class implements the commands in the Start up phase
 *
 * @author Anuja Somthankar
 */
public class StartupPhase implements Phase {

    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    @Override
    public Phase nextPhase() {
        LogManager.logAction("Phase changed from StartupPhase to IssueOrderPhase\n");
        return new IssueOrderPhase();
    }

    /**
     * This method displays the map.
     *
     * @param p_map         The map object
     * @param p_gameManager The object of the game manager.
     */
    @Override
    public void showMap(Map p_map, GameManager p_gameManager) {
        p_gameManager.showMap();
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_currentPlayer The current player
     * @param p_country       The country in the order
     * @param p_num           The number of armies to be deployed
     */
    @Override
    public void deploy(GameManager p_gameManager, Player p_currentPlayer, Country p_country, int p_num) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Advances(attack) armies from an owned country to an opponents
     *
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    @Override
    public void advance(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Bomb an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    @Override
    public void bomb(GameManager p_gameManager, Player p_currentPlayer, Country p_country) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Blockade an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    @Override
    public void blockade(GameManager p_gameManager, Player p_currentPlayer, Country p_country) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Enforces negotiation for a turn
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_otherPlayer   The opponent
     */
    @Override
    public void negotiate(GameManager p_gameManager, Player p_currentPlayer, Player p_otherPlayer) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_editCountryInput The input given by the user to add/remove countries
     * @param p_map              The Map object
     */
    @Override
    public void editCountry(String[] p_editCountryInput, Map p_map) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_inputSplit The input given by the user to add/remove continents
     * @param p_map        The Map object
     */
    @Override
    public void editContinent(String[] p_inputSplit, Map p_map) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_editNeighbourInput The input given by the user to add/remove neighbours
     * @param p_map                The Map object
     */
    @Override
    public void editNeighbor(String[] p_editNeighbourInput, Map p_map) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_map         The Map object
     * @param p_gameManager The GameManager object
     */
    @Override
    public void validateMap(Map p_map, GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_map      The Map object
     * @param p_cmdSplit The input given by the user to save map
     */
    @Override
    public void saveMap(Map p_map, String[] p_cmdSplit) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_gameManager The game manager object
     * @param p_cmdSplit    The input given by the user
     */
    @Override
    public void editMap(GameManager p_gameManager, String[] p_cmdSplit) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_fileName    The name of the file from where the map is to be loaded
     * @param p_gameManager The game manager object
     */
    @Override
    public void loadMap(String p_fileName, GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
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
                //calls addPlayer() to add the Player in the game
                p_gameManager.addPlayer(l_playername);
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

        p_gameManager.logAction("Assigned countries to the players. Game has Started!");
        p_gameManager.setD_gamePhase(this.nextPhase());
    }

    /**
     * This method is used to execute orders
     *
     * @param p_gameManager The game manager object
     */
    @Override
    public void executeOrder(GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    @Override
    public void airlift(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }
}
