package phases;

import controller.GameManager;
import models.Country;
import models.Map;
import models.Player;

public interface Phase {

    public Phase nextPhase();

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

    void editCountry(String[] p_editCountryInput, Map p_map);

    void editContinent(String[] p_inputSplit, Map p_map);

    void editNeighbor(String[] p_editNeighbourInput, Map p_map);

    void validateMap(Map p_map, GameManager p_gameManager);

    void saveMap(Map p_map, String[] l_cmdSplit);

    void editMap(GameManager p_gameManager, String[] l_cmdSplit);

    // Startup phase
    void loadMap(String p_fileName, GameManager p_gameManager);

    void gamePlayer(String[] p_cmdSplit, GameManager p_gameManager);

    void assignCountries(GameManager p_gameManager);
}
