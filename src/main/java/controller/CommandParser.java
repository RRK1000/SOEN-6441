package controller;

import global.Commands;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.Phase;
import phases.StartupPhase;
import util.CommandUtil;
import util.MapUtil;

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
        if (p_gameManager.d_gamePhase.getClass().equals(InitMapPhase.class)) {
            System.out.println(Constants.MAP_INIT_HELP);
        } else if (p_gameManager.d_gamePhase.getClass().equals(StartupPhase.class)) {
            System.out.println(Constants.GAME_STARTUP_HELP);
        } else if (p_gameManager.d_gamePhase.getClass().equals(IssueOrderPhase.class)) {
            System.out.println(Constants.ISSUE_ORDER_HELP);
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
        if (!CommandUtil.isValidCmd(p_input, p_gameManager.d_gamePhase)) {
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
                    System.out.println("Map not loaded");
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                }
                p_gameManager.d_gamePhase.showMap(l_map, p_gameManager);
                break;

            case Commands.EDIT_NEIGHBOR:

                String[] l_editNeighbourInput = p_input.split(" -");
                p_gameManager.d_gamePhase.editNeighbor(l_editNeighbourInput, l_map);
                break;

            case Commands.EDIT_CONTINENT:
                String[] l_inputSplit = p_input.split(" -");
                p_gameManager.d_gamePhase.editContinent(l_inputSplit, l_map);
                break;

            case Commands.EDIT_COUNTRY:
                String[] l_editCountryInput = p_input.split(" -");
                p_gameManager.d_gamePhase.editCountry(l_editCountryInput, l_map);
                break;

            case Commands.SAVE_MAP:
                p_gameManager.d_gamePhase.saveMap(l_map, l_cmdSplit);
                break;

            case Commands.EDIT_MAP:
                p_gameManager.d_gamePhase.editMap(p_gameManager, l_cmdSplit);
                break;

            case Commands.VALIDATE_MAP:
                if (l_map != null) {
                    p_gameManager.d_gamePhase.validateMap(l_map, p_gameManager);
                } else {
                    displayError();
                }
                break;

            case Commands.LOAD_MAP:
                p_gameManager.d_gamePhase.loadMap(l_cmdSplit[1], p_gameManager);
                break;

            case Commands.GAME_PLAYER:
                if (p_gameManager.getD_map() == null) {
                    System.out.println("Map not loaded");
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                }
                p_gameManager.d_gamePhase.gamePlayer(l_cmdSplit, p_gameManager);
                break;

            case Commands.ASSIGN_COUNTRIES:
                p_gameManager.d_gamePhase.assignCountries(p_gameManager);
                break;

            case Commands.DEPLOY_ORDER:
                Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());


                //Getting the countryID
                int l_countryID = Integer.parseInt(l_cmdSplit[1]);
                Country l_country = p_gameManager.getD_map().getD_countryByID(l_countryID);
                //Getting number of armies
                int l_numArmies = Integer.parseInt(l_cmdSplit[2]);
                //call issueOrder()
                p_gameManager.d_gamePhase.deploy(l_currentPlayer, l_country, l_numArmies);

                //If the current player has zero armies, move to the next player.
                if (l_currentPlayer.getD_numArmies() == 0) {
                    System.out.println("Player " + l_currentPlayer.getD_playerName() + " turn over. ");
                    System.out.println();
                    //Updating the player turn.
                    p_gameManager.updatePlayerTurn();
                    if (p_gameManager.getD_currentPlayerTurn() == 0) {
                        //Execute the order given by the player
                        p_gameManager.executeOrder();
                        //Assigning armies to the player
                        p_gameManager.assignReinforcements();
                        p_gameManager.d_gamePhase = p_gameManager.d_gamePhase.nextPhase();
                    }
                    l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                    System.out.println("Player " + l_currentPlayer.getD_playerName() + "'s turn ");
                }
                System.out.println("Available Reinforcement Armies: " + l_currentPlayer.getD_numArmies());

                break;
            default:
                displayError();
        }
    }
}

