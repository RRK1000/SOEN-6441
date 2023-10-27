package phases;

import controller.CommandParser;
import controller.GameManager;
import models.Country;
import models.Map;
import models.Order;
import models.Player;

public class IssueOrderPhase implements Phase {
    @Override
    public Phase nextPhase() {
        return new ExecuteOrderPhase();
    }

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
            if (!p_currentPlayer.getD_countryList().contains(p_country) || p_currentPlayer.getD_numArmies() < p_num) {
                System.out.println("Invalid order, cannot be issued");
                return;
            }

            // Create an order using the provided parameters (p_countryID and num)
            Order l_order = new Order(p_country, p_num);
            p_currentPlayer.setD_currentOrder(l_order);
            // Call the issue_order() method of the current player to add the order
            p_currentPlayer.issueOrder();
            System.out.println("Issued Order");
        } else {
            // Handle the case where there is no current player or it's not their turn
            System.out.println("No current player or it's not their turn to issue orders.");
        }
    }

    @Override
    public void editCountry(String[] p_editCountryInput, Map p_map) {
        CommandParser.displayError();
    }

    @Override
    public void editContinent(String[] p_inputSplit, Map p_map) {
        CommandParser.displayError();
    }

    @Override
    public void editNeighbor(String[] p_editNeighbourInput, Map p_map) {
        CommandParser.displayError();
    }

    @Override
    public void validateMap(Map p_map, GameManager p_gameManager) {
        CommandParser.displayError();
    }

    @Override
    public void saveMap(Map p_map, String[] l_cmdSplit) {
        CommandParser.displayError();
    }

    @Override
    public void editMap(GameManager p_gameManager, String[] l_cmdSplit) {
        CommandParser.displayError();
    }

    @Override
    public void loadMap(String p_fileName, GameManager p_gameManager) {
        CommandParser.displayError();
    }

    @Override
    public void gamePlayer(String[] p_cmdSplit, GameManager p_gameManager) {
        CommandParser.displayError();
    }

    @Override
    public void assignCountries(GameManager p_gameManager) {
        CommandParser.displayError();
    }


}
