package phases;

import controller.GameManager;
import models.Country;
import models.Map;
import models.Player;

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
     * Deploys an order
     *
     * @param p_currentPlayer The current player
     * @param p_country       The country in the order
     * @param p_num           The number of armies to be deployed
     */
    void deploy(Player p_currentPlayer, Country p_country, int p_num);

    // Execute Order Phase


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
     * @param l_cmdSplit The input given by the user to save map
     */
    void saveMap(Map p_map, String[] l_cmdSplit);

    /**
     * This method is used to edit the map
     *
     * @param p_gameManager The game manager object
     * @param l_cmdSplit    The input given by the user
     */
    void editMap(GameManager p_gameManager, String[] l_cmdSplit);

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
}
