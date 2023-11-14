package phases;

import controller.GameManager;
import models.Country;
import models.Map;
import models.Player;

/**
 * Interface implemented by all the phases
 *
 * @author Anuja Somthankar
 */
public interface Phase {

    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    Phase nextPhase();

    /**
     * This method displays the map.
     *
     * @param p_map         The map object
     * @param p_gameManager The object of the game manager.
     */
    void showMap(Map p_map, GameManager p_gameManager);

    // Issue Order Phase

    /**
     * Deploys armies to a country
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The country in the order
     * @param p_num           The number of armies to be deployed
     */
    void deploy(GameManager p_gameManager, Player p_currentPlayer, Country p_country, int p_num);

    /**
     * Advances(attack) armies from an owned country to an opponents
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    void advance(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num);

    /**
     * Bomb an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    void bomb(GameManager p_gameManager, Player p_currentPlayer, Country p_country);

    /**
     * Blockade an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    void blockade(GameManager p_gameManager, Player p_currentPlayer, Country p_country);

    /**
     * Enforces negotiation for a turn
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_otherPlayer   The opponent
     */
    void negotiate(GameManager p_gameManager, Player p_currentPlayer, Player p_otherPlayer);

    /**
     * Airlifts armies from player's country to another of their owned countries
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    void airlift(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num);


    // Init Map Phase

    /**
     * This method adds and removes countries during MapInit phase.
     *
     * @param p_editCountryInput The input given by the user to add/remove countries
     * @param p_map              The Map object
     */
    void editCountry(String[] p_editCountryInput, Map p_map);

    /**
     * This method adds and removes continents during MapInit phase.
     *
     * @param p_inputSplit The input given by the user to add/remove continents
     * @param p_map        The Map object
     */
    void editContinent(String[] p_inputSplit, Map p_map);

    /**
     * This method adds and removes neighbour during MapInit phase.
     *
     * @param p_editNeighbourInput The input given by the user to add/remove neighbours
     * @param p_map                The Map object
     */
    void editNeighbor(String[] p_editNeighbourInput, Map p_map);

    /**
     * This method is used to validate the map.
     *
     * @param p_map         The Map object
     * @param p_gameManager The GameManager object
     */
    void validateMap(Map p_map, GameManager p_gameManager);

    /**
     * This method is used to save the map
     *
     * @param p_map      The Map object
     * @param p_cmdSplit The input given by the user to save map
     */
    void saveMap(Map p_map, String[] p_cmdSplit);

    /**
     * This method is used to edit the map
     *
     * @param p_gameManager The game manager object
     * @param p_cmdSplit    The input given by the user
     */
    void editMap(GameManager p_gameManager, String[] p_cmdSplit);

    // Startup phase

    /**
     * This method loads the map
     *
     * @param p_fileName    The name of the file from where the map is to be loaded
     * @param p_gameManager The game manager object
     */
    void loadMap(String p_fileName, GameManager p_gameManager);

    /**
     * This method adds/removes the game players
     *
     * @param p_cmdSplit    The input given by the user to add/remove players
     * @param p_gameManager The game manager object
     */
    void gamePlayer(String[] p_cmdSplit, GameManager p_gameManager);

    /**
     * This method is used to assign countries
     *
     * @param p_gameManager The game manager object
     */
    void assignCountries(GameManager p_gameManager);


    /**
     * This method is used to execute orders
     *
     * @param p_gameManager The game manager object
     */
    void executeOrder(GameManager p_gameManager);

    /**
     * Loads a game from a file
     *
     * @param p_gameManager {@link GameManager}
     * @param p_filename    file to load the game from
     */
    void loadGame(GameManager p_gameManager, String p_filename);

    /**
     * Saves a game to a file
     *
     * @param p_gameManager {@link GameManager}
     * @param p_filename    file to load the game from
     */
    void saveGame(GameManager p_gameManager, String p_filename);


}
