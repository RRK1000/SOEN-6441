package phases;

import controller.CommandParser;
import controller.GameManager;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;

public class StartupPhase implements Phase {

    @Override
    public Phase nextPhase() {
        return new IssueOrderPhase();
    }

    @Override
    public void showMap(Map p_map, GameManager p_gameManager) {
        p_gameManager.showMap();
    }

    @Override
    public void deploy(Player p_currentPlayer, Country p_country, int p_num) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void editCountry(String[] p_editCountryInput, Map p_map) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void editContinent(String[] p_inputSplit, Map p_map) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void editNeighbor(String[] p_editNeighbourInput, Map p_map) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void validateMap(Map p_map, GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void saveMap(Map p_map, String[] l_cmdSplit) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void editMap(GameManager p_gameManager, String[] l_cmdSplit) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

    @Override
    public void loadMap(String p_fileName, GameManager p_gameManager) {
        System.out.println(Constants.INVALID_PHASE_ERROR);
    }

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

    @Override
    public void assignCountries(GameManager p_gameManager) {
        p_gameManager.assignCountries();
        p_gameManager.d_gamePhase = this.nextPhase();
    }
}
