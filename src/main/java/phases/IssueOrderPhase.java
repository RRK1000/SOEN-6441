package phases;

import controller.GameManager;
import global.Constants;
import models.Country;
import models.Map;
import models.Order;
import models.Player;
import orders.DeployOrder;

public class IssueOrderPhase implements Phase {
    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    @Override
    public Phase nextPhase() {
        return new ExecuteOrderPhase();
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
     * Deploys an order
     *
     * @param p_currentPlayer The current player
     * @param p_country       The country in the order
     * @param p_num           The number of armies to be deployed
     */
    @Override
    public void deploy(Player p_currentPlayer, Country p_country, int p_num) {
        if (p_currentPlayer != null) {
            // Create an order using the provided parameters (p_countryID and num)
            Order l_order = new DeployOrder(p_country, p_num);
            if (!l_order.isValid(p_currentPlayer)) {
                throw new IllegalArgumentException("Cannot deploy more armies than available in reinforcement pool.");
            }
            p_currentPlayer.setD_currentOrder(l_order);
            // Call the issue_order() method of the current player to add the order
            p_currentPlayer.issueOrder();
            System.out.println("Issued Order");
        } else {
            // Handle the case where there is no current player or it's not their turn
            System.out.println("No current player or it's not their turn to issue orders.");
        }
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
     * @param l_cmdSplit The input given by the user to save map
     */
    @Override
    public void saveMap(Map p_map, String[] l_cmdSplit) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_gameManager The game manager object
     * @param l_cmdSplit    The input given by the user
     */
    @Override
    public void editMap(GameManager p_gameManager, String[] l_cmdSplit) {
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
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_cmdSplit    The input given by the user to add/remove players
     * @param p_gameManager The game manager object
     */
    @Override
    public void gamePlayer(String[] p_cmdSplit, GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    /**
     * This method prints the invalid phase error as it is not a valid command for this phase.
     *
     * @param p_gameManager The game manager object
     */
    @Override
    public void assignCountries(GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }


}
