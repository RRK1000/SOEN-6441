package phases;

import controller.GameManager;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;
import util.MapUtil;

public class InitMapPhase implements Phase {
    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    @Override
    public Phase nextPhase() {
        return new StartupPhase();
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
     * This method adds and removes countries during MapInit phase.
     *
     * @param p_editCountryInput The input given by the user to add/remove countries
     * @param p_map              The Map object
     */
    @Override
    public void editCountry(String[] p_editCountryInput, Map p_map) {
        for (int l_i = 1; l_i < p_editCountryInput.length; l_i++) {
            //checks if add is followed by countryID and continentID
            if (p_editCountryInput[l_i].startsWith("add")) {
                String[] l_addParams = p_editCountryInput[l_i].split(" ");

                int l_countryID = Integer.parseInt(l_addParams[1]);
                int l_continentID = Integer.parseInt(l_addParams[2]);
                System.out.println("adding country: " + l_countryID);
                MapUtil.addCountry(p_map, l_countryID, l_continentID);

            } else if (p_editCountryInput[l_i].startsWith("remove")) {
                //checks if remove is followed by countryID
                String[] l_removeParams = p_editCountryInput[l_i].split(" ");

                int l_countryID = Integer.parseInt(l_removeParams[1]);
                System.out.println("removing country: " + l_countryID);
                MapUtil.removeCountry(p_map, l_countryID);

            } else {
                System.out.println(Constants.CMD_ERROR);
            }
        }
    }

    /**
     * This method adds and removes continents during MapInit phase.
     *
     * @param p_inputSplit The input given by the user to add/remove continents
     * @param p_map        The Map object
     */
    @Override
    public void editContinent(String[] p_inputSplit, Map p_map) {
        for (int l_i = 1; l_i < p_inputSplit.length; l_i++) {
            //Handles the command to add continent
            if (p_inputSplit[l_i].startsWith("add")) {
                String[] l_addParams = p_inputSplit[l_i].split(" ");
                //checks if add is followed by continentID and continentvalue

                int l_continentID = Integer.parseInt(l_addParams[1]);
                int l_continentValue = Integer.parseInt(l_addParams[2]);
                System.out.println("adding continent: " + l_continentID);
                MapUtil.addContinent(p_map, l_continentID, l_continentValue);

                //Handles the command to remove the continent
            } else if (p_inputSplit[l_i].startsWith("remove")) {
                String[] l_removeParams = p_inputSplit[l_i].split(" ");
                //checks if remove is followed by continentID

                int l_continentID = Integer.parseInt(l_removeParams[1]);
                System.out.println("removing continent: " + l_continentID);
                MapUtil.removeContinent(p_map, l_continentID);

            } else {
                System.out.println(Constants.CMD_ERROR);
            }
        }
    }

    /**
     * This method adds and removes neighbour during MapInit phase.
     *
     * @param p_editNeighbourInput The input given by the user to add/remove neighbours
     * @param p_map                The Map object
     */
    @Override
    public void editNeighbor(String[] p_editNeighbourInput, Map p_map) {
        for (int l_i = 1; l_i < p_editNeighbourInput.length; l_i++) {
            //Handles the command to add the neighbour country
            if (p_editNeighbourInput[l_i].startsWith("add")) {
                String[] l_addParams = p_editNeighbourInput[l_i].split(" ");
                //checks if add is followed by countryID and neighborcountryID

                int l_countryID = Integer.parseInt(l_addParams[1]);
                int l_neighbourID = Integer.parseInt(l_addParams[2]);
                System.out.println("adding neighbor country: " + l_neighbourID);
                //Calls addNeighbour() for adding the neighbour country
                MapUtil.addNeighbour(p_map, l_countryID, l_neighbourID);

                //Handles the command to remove the neighbour country
            } else if (p_editNeighbourInput[l_i].startsWith("remove")) {
                String[] l_removeParams = p_editNeighbourInput[l_i].split(" ");
                //checks if remove is followed by countryID and neighborcountryID

                int l_countryID = Integer.parseInt(l_removeParams[1]);
                int l_neighbourID = Integer.parseInt(l_removeParams[2]);
                System.out.println("removing neighbour country: " + l_neighbourID);
                //Calls removeNeighbour() for removing the neighbour country
                MapUtil.removeNeighbour(p_map, l_countryID, l_neighbourID);

            } else {
                System.out.println(Constants.CMD_ERROR);
            }
        }
    }

    /**
     * This method is used to validate the map.
     *
     * @param p_map         The Map object
     * @param p_gameManager The GameManager object
     */
    @Override
    public void validateMap(Map p_map, GameManager p_gameManager) {
        //Checks if the map is valid or not
        if (MapUtil.isValidMap(p_map)) {
            p_gameManager.setD_map(p_map);
        } else {
            System.out.println("Map validation unsuccessful");
        }
    }

    /**
     * This method is used to save the map
     *
     * @param p_map      The Map object
     * @param l_cmdSplit The input given by the user to save map
     */
    @Override
    public void saveMap(Map p_map, String[] l_cmdSplit) {
        //Checks if the map was saved successfully
        if (MapUtil.saveMap(p_map, l_cmdSplit[1])) {
            System.out.println("Map is saved");
            System.out.println("Load the map to start the game.");
        } else {
            System.out.println("Map Invalid");
        }
    }

    /**
     * This method is used to edit the map
     *
     * @param p_gameManager The game manager object
     * @param l_cmdSplit    The input given by the user
     */
    @Override
    public void editMap(GameManager p_gameManager, String[] l_cmdSplit) {
        p_gameManager.setD_map(MapUtil.editMap(l_cmdSplit[1]));
        System.out.println("Map loaded to be edited...");
    }

    /**
     * This method displays the map.
     *
     * @param p_map         The map object
     * @param p_gameManager The object of the game manager.
     */
    @Override
    public void showMap(Map p_map, GameManager p_gameManager) {
        MapUtil.showMap(p_map);
    }

    /**
     * This method loads the map
     *
     * @param p_fileName    The name of the file from where the map is to be loaded
     * @param p_gameManager The game manager object
     */
    @Override
    public void loadMap(String p_fileName, GameManager p_gameManager) {
        Map l_map = MapUtil.loadMap(p_fileName);
        if (MapUtil.isValidMap(l_map)) {
            //Set the game phase to start up phase
            p_gameManager.setD_gamePhase(this.nextPhase());
            p_gameManager.setD_map(l_map);

            System.out.println("Next, add players to the game");
        } else {
            System.out.println("Map validation unsuccessful");
            return;
        }
        System.out.println(Constants.HELP_MESSAGE);
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

    @Override
    public void airlift(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }
}
