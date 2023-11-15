package phases;

import controller.GameManager;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;

/**
 * Interface implemented by all the phases
 *
 * @author Anuja Somthankar
 */
public abstract class Phase {

    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    public abstract Phase nextPhase();

    /**
     * This method displays the map.
     *
     * @param p_map         The map object
     * @param p_gameManager The object of the game manager.
     */
    public void showMap(Map p_map, GameManager p_gameManager){
        p_gameManager.showMap();
    }

    // Issue Order Phase

    /**
     * Deploys armies to a country
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The country in the order
     * @param p_num           The number of armies to be deployed
     */
    public void deploy(GameManager p_gameManager, Player p_currentPlayer, Country p_country, int p_num){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Advances(attack) armies from an owned country to an opponents
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    public void advance(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Bomb an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    public void bomb(GameManager p_gameManager, Player p_currentPlayer, Country p_country){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Blockade an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    public void blockade(GameManager p_gameManager, Player p_currentPlayer, Country p_country){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Enforces negotiation for a turn
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_otherPlayer   The opponent
     */
    public void negotiate(GameManager p_gameManager, Player p_currentPlayer, Player p_otherPlayer){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Airlifts armies from player's country to another of their owned countries
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    public void airlift(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    // Init Map Phase

    /**
     * This method adds and removes countries during MapInit phase.
     *
     * @param p_editCountryInput The input given by the user to add/remove countries
     * @param p_map              The Map object
     */
    public void editCountry(String[] p_editCountryInput, Map p_map){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method adds and removes continents during MapInit phase.
     *
     * @param p_inputSplit The input given by the user to add/remove continents
     * @param p_map        The Map object
     */
    public void editContinent(String[] p_inputSplit, Map p_map){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method adds and removes neighbour during MapInit phase.
     *
     * @param p_editNeighbourInput The input given by the user to add/remove neighbours
     * @param p_map                The Map object
     */
    public void editNeighbor(String[] p_editNeighbourInput, Map p_map){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method is used to validate the map.
     *
     * @param p_map         The Map object
     * @param p_gameManager The GameManager object
     */
    public void validateMap(Map p_map, GameManager p_gameManager){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method is used to save the map
     *
     * @param p_map      The Map object
     * @param p_cmdSplit The input given by the user to save map
     */
    public void saveMap(Map p_map, String[] p_cmdSplit){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method is used to edit the map
     *
     * @param p_gameManager The game manager object
     * @param p_cmdSplit    The input given by the user
     */
    public void editMap(GameManager p_gameManager, String[] p_cmdSplit){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    // Startup phase

    /**
     * This method loads the map
     *
     * @param p_fileName    The name of the file from where the map is to be loaded
     * @param p_gameManager The game manager object
     */
    public void loadMap(String p_fileName, GameManager p_gameManager){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method adds/removes the game players
     *
     * @param p_cmdSplit    The input given by the user to add/remove players
     * @param p_gameManager The game manager object
     */
    public void gamePlayer(String[] p_cmdSplit, GameManager p_gameManager){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method is used to assign countries
     *
     * @param p_gameManager The game manager object
     */
    public void assignCountries(GameManager p_gameManager){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method is used to execute orders
     *
     * @param p_gameManager The game manager object
     */
    public void executeOrder(GameManager p_gameManager){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Loads a game from a file
     *
     * @param p_gameManager {@link GameManager}
     * @param p_filename    file to load the game from
     */
    public void loadGame(GameManager p_gameManager, String p_filename){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * Saves a game to a file
     *
     * @param p_gameManager {@link GameManager}
     * @param p_filename    file to load the game from
     */
    public void saveGame(GameManager p_gameManager, String p_filename){
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }
}
