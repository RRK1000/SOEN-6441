package controller;

import global.Commands;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.StartupPhase;
import util.CommandUtil;

/**
 * Represents the command parser.
 * It handles the commands entered by player and validates it.
 *
 * @author Rishi Ravikumar
 * @author Anuja Somthankar
 * @author Abhigyan Singh
 * @author Nimisha Jadav
 * @author Yusuke
 */
public class CommandParser {
    public static void displayError() {
        System.out.println(Constants.CMD_ERROR);
        System.out.println(Constants.HELP_MESSAGE);
    }

    /**
     * Displays the current phase of the game
     *
     * @param p_gameManager {@link GameManager}
     */
    public static void displayInstructions(GameManager p_gameManager) {
        if (p_gameManager.getD_gamePhase() instanceof InitMapPhase) {
            System.out.println(Constants.MAP_INIT_HELP);
        } else if (p_gameManager.getD_gamePhase() instanceof StartupPhase) {
            System.out.println(Constants.GAME_STARTUP_HELP);
        } else if (p_gameManager.getD_gamePhase() instanceof IssueOrderPhase) {
            System.out.println(Constants.ISSUE_ORDER_HELP);
            System.out.println(Constants.GAME_EXIT);

            Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
            System.out.println("player: " + l_currentPlayer.getD_playerName() + "'s turn");
            System.out.println("\navailable reinforcement armies: " + l_currentPlayer.getD_numArmies());
            return;
        } else {
            System.out.println(Constants.IN_GAME_HELP);
        }
        System.out.println(Constants.GAME_EXIT);

    }

    /**
     * This method is used to read the command given by the player and validates if the syntax
     * of the command is correct and then takes the action accordingly
     *
     * @param p_input       - Command entered by the player
     * @param p_gameManager {@link GameManager}
     */
    public static void inputParser(GameManager p_gameManager, String p_input) {
        //Breaks the input command around space.
        String[] l_cmdSplit = p_input.split(" ");
        if (!CommandUtil.isValidCmd(p_input, p_gameManager.getD_gamePhase())) {
            displayError();
            return;
        }

        Map l_map;
        l_map = p_gameManager.getD_map();

        //Handles index 0 of the input command given by the player.
        switch (l_cmdSplit[0]) {
            //Displays the help command
            case Commands.HELP:
                displayInstructions(p_gameManager);
                break;

            case Commands.SHOW_MAP:
                if (l_map == null) {
                    System.out.println("map not loaded");
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                }
                p_gameManager.getD_gamePhase().showMap(l_map, p_gameManager);
                break;

            case Commands.EDIT_NEIGHBOR:

                String[] l_editNeighbourInput = p_input.split(" -");
                p_gameManager.getD_gamePhase().editNeighbor(l_editNeighbourInput, l_map);
                break;

            case Commands.EDIT_CONTINENT:
                String[] l_inputSplit = p_input.split(" -");
                p_gameManager.getD_gamePhase().editContinent(l_inputSplit, l_map);
                break;

            case Commands.EDIT_COUNTRY:
                String[] l_editCountryInput = p_input.split(" -");
                p_gameManager.getD_gamePhase().editCountry(l_editCountryInput, l_map);
                break;

            case Commands.SAVE_MAP:
                p_gameManager.getD_gamePhase().saveMap(l_map, l_cmdSplit);
                break;

            case Commands.EDIT_MAP:
                p_gameManager.getD_gamePhase().editMap(p_gameManager, l_cmdSplit);
                break;

            case Commands.VALIDATE_MAP:
                if (l_map != null) {
                    p_gameManager.getD_gamePhase().validateMap(l_map, p_gameManager);
                } else {
                    displayError();
                }
                break;

            case Commands.LOAD_MAP:
                p_gameManager.getD_gamePhase().loadMap(l_cmdSplit[1], p_gameManager);
                break;

            case Commands.GAME_PLAYER:
                if (p_gameManager.getD_map() == null) {
                    System.out.println("map not loaded");
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                }
                p_gameManager.getD_gamePhase().gamePlayer(l_cmdSplit, p_gameManager);
                break;

            case Commands.ASSIGN_COUNTRIES:
                p_gameManager.getD_gamePhase().assignCountries(p_gameManager);
                break;

            case Commands.DEPLOY_ORDER:
                Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());

                //Getting the countryID
                int l_countryID = Integer.parseInt(l_cmdSplit[1]);
                Country l_country = p_gameManager.getD_map().getD_countryByID(l_countryID);
                //Getting number of armies
                int l_numArmies = Integer.parseInt(l_cmdSplit[2]);
                //call issueOrder()
                p_gameManager.getD_gamePhase().deploy(p_gameManager, l_currentPlayer, l_country, l_numArmies);
                System.out.println("available reinforcement armies: " + l_currentPlayer.getD_numArmies());
                break;

            case Commands.ADVANCE_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                Country l_countryfrom = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                Country l_countryto = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[2]));
                l_numArmies = Integer.parseInt(l_cmdSplit[3]);
                p_gameManager.getD_gamePhase().advance(p_gameManager, l_currentPlayer, l_countryfrom, l_countryto, l_numArmies);
                break;

            case Commands.BOMB_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                l_country = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                p_gameManager.getD_gamePhase().bomb(p_gameManager, l_currentPlayer, l_country);
                break;

            case Commands.AIRLIFT_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                Country l_countryFrom = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                Country l_countryTo = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[2]));
                l_numArmies = Integer.parseInt(l_cmdSplit[3]);
                p_gameManager.getD_gamePhase().airlift(p_gameManager, l_currentPlayer, l_countryFrom, l_countryTo, l_numArmies);
                break;

            case Commands.END_TURN:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                if (l_currentPlayer.getD_numArmies() > 0) {
                    System.out.println("cannot end turn" + l_currentPlayer.getD_numArmies() + " left to be placed");
                    break;
                }
                //Updating the player turn.
                p_gameManager.addPlayerToSkipList(p_gameManager.getD_currentPlayerTurn());
                p_gameManager.updatePlayerTurn();
                break;

            default:
                displayError();
        }
    }
}

