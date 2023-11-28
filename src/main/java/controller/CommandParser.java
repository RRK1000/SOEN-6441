package controller;


import gamelog.LogManager;
import global.Commands;
import global.Constants;
import models.Country;
import models.Map;
import models.Player;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.StartupPhase;
import util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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


    /**
     * Prints error on console and in game log
     */
    public static void displayError() {
        System.out.println(Constants.CMD_ERROR);
        LogManager.logAction(Constants.CMD_ERROR);
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
            System.out.println("\nplayer: " + l_currentPlayer.getD_playerName() + "'s turn");
            System.out.println("available reinforcement armies: " + l_currentPlayer.getD_numArmies());
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
                LogManager.logAction("Displayed the map.");

                break;

            case Commands.EDIT_NEIGHBOR:

                String[] l_editNeighbourInput = p_input.split(" -");
                p_gameManager.getD_gamePhase().editNeighbor(l_editNeighbourInput, l_map);
                LogManager.logAction("Edited neighbors with input: " + String.join(" ", l_cmdSplit));

                break;

            case Commands.EDIT_CONTINENT:
                String[] l_inputSplit = p_input.split(" -");
                p_gameManager.getD_gamePhase().editContinent(l_inputSplit, l_map);
                LogManager.logAction("Edited continents with input: " + String.join(" ", l_cmdSplit));
                break;

            case Commands.EDIT_COUNTRY:
                String[] l_editCountryInput = p_input.split(" -");
                p_gameManager.getD_gamePhase().editCountry(l_editCountryInput, l_map);
                LogManager.logAction("Edited countries with input: " + String.join(" ", l_cmdSplit));

                break;

            case Commands.SAVE_MAP:
                String l_fileName = l_cmdSplit[1];
                Scanner l_scanner = new Scanner(System.in); 

                System.out.println("Please select the format to save the map (domination/conquest):");
                String l_format = l_scanner.nextLine().trim().toLowerCase();
                MapFileReader l_fileReader;
                if ("conquest".equals(l_format)) {
                    l_fileReader = new ConquestMapFileReaderAdapter();
                } else if ("domination".equals(l_format)) {
                    l_fileReader = new DominationMapFileReader();
                } else {
                    System.out.println("Invalid format. Please specify 'conquest' or 'domination'.");
                    break;
                }
                try {
                    if (l_fileReader.saveMap(l_map, l_fileName)) {
                        System.out.println("Map successfully saved in " + l_format + " format.");
                        LogManager.logAction("Map saved in " + l_format + " format: " + l_fileName);
                    } else {
                        System.out.println("Failed to save the map.");
                        LogManager.logAction("Failed to save the map.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while saving the map: " + e.getMessage());
                    LogManager.logAction("Error occurred while saving the map: " + e.getMessage());
                }
                break;



            case Commands.EDIT_MAP:
                p_gameManager.getD_gamePhase().editMap(p_gameManager, l_cmdSplit);
                LogManager.logAction("Edited the map with input: " + String.join(" ", l_cmdSplit));
                break;

            case Commands.VALIDATE_MAP:
                if (l_map != null) {
                    p_gameManager.getD_gamePhase().validateMap(l_map, p_gameManager);
                    LogManager.logAction("Validated the map.");

                } else {
                    displayError();
                }
                break;

            case Commands.LOAD_MAP:
                String l_filename = l_cmdSplit[1];
                MapFileReader l_loadfileReader;

                if (MapUtil.isMapConquest(l_filename)) {
                    l_loadfileReader = new ConquestMapFileReaderAdapter();
                	System.out.println("This file is Conquest Format.");

                } else {
                    l_loadfileReader = new DominationMapFileReader();
                }

                try {
                    Map loadedMap = l_loadfileReader.loadMap(l_filename);
                    if(MapUtil.isValidMap(loadedMap)) {
                        p_gameManager.setD_map(loadedMap);
                        p_gameManager.setD_mapFileName(l_filename);
                        p_gameManager.setD_gamePhase(p_gameManager.getD_gamePhase().nextPhase());
                        LogManager.logAction("Loaded a map: " + l_filename);
                    }
                } catch (IOException e) {
                    System.out.println("Error loading the map file: " + e.getMessage());
                    LogManager.logAction("Error loading the map file: " + l_filename);
                }
                break;

            case Commands.TOURNAMENT:
                String[] l_tournamentInput = p_input.split(" -");
                List<String> l_listOfPlayerStrategies = new ArrayList<>();
                List<String> l_mapList = new ArrayList<>();
                int l_numberOfGames = 0;
                int l_maxNumberOfTurns = 0;
                boolean l_hasError = false;

                // iterating through each option value
                for (int l_i = 1; l_i < l_tournamentInput.length; l_i++) {
                    String[] l_params = l_tournamentInput[l_i].split(" ");
                    if(l_params[0].startsWith("M")){
                        String l_mapFiles = l_params[1];
                        l_mapList.addAll(Arrays.asList(l_mapFiles.split(",")));
                    }
                    if (l_params[0].startsWith("P")) {
                        String l_playerStrategies = l_params[1];
                        String[] l_stringOfPlayerStrategies = l_playerStrategies.split(",");
                        if(l_stringOfPlayerStrategies.length < 2){
                            displayError();
                            break;
                        }

                        l_listOfPlayerStrategies.addAll(Arrays.asList(l_stringOfPlayerStrategies));
                    } else if (l_params[0].startsWith("G")) {
                        if(Integer.parseInt(l_params[1]) < 1 || Integer.parseInt(l_params[1]) > 5) {
                            System.out.println("G option value invalid, 1 to 5 games allowed");
                            l_hasError = true;
                            break;
                        }
                        l_numberOfGames = Integer.parseInt(l_params[1]);
                    } else if (l_params[0].startsWith("D")) {
                        if(Integer.parseInt(l_params[1]) < 10 || Integer.parseInt(l_params[1]) > 50) {
                            System.out.println("D option value invalid, 10 to 50 turns allowed");
                            l_hasError = true;
                            break;
                        }
                        l_maxNumberOfTurns = Integer.parseInt(l_params[1]);
                    } else {
                        System.out.println(Constants.CMD_ERROR);
                    }
                }
                if(l_hasError) break;

                // setting up the tournament game manager with the user specified values
                TournamentGameManager l_tournamentGameManager= new TournamentGameManager();
                l_tournamentGameManager.setD_mapList(l_mapList);
                l_tournamentGameManager.setD_strategyList(l_listOfPlayerStrategies);
                l_tournamentGameManager.setD_numGames(l_numberOfGames);
                l_tournamentGameManager.setD_maxTurns(l_maxNumberOfTurns);
                l_tournamentGameManager.runTournament();
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
                LogManager.logAction("Countries were assigned to players.");

                break;

            case Commands.DEPLOY_ORDER:
                Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                String l_playerName = l_currentPlayer.getD_playerName(); // to get player's name for logs
                //Getting the countryID
                int l_countryID = Integer.parseInt(l_cmdSplit[1]);
                Country l_country = p_gameManager.getD_map().getD_countryByID(l_countryID);
                //Getting number of armies
                int l_numArmies = Integer.parseInt(l_cmdSplit[2]);
                //call issueOrder()
                p_gameManager.getD_gamePhase().deploy(p_gameManager, l_currentPlayer, l_country, l_numArmies);

                LogManager.logAction(l_playerName + " deployed " + l_numArmies + " armies to country ID " + l_countryID);

                break;

            case Commands.ADVANCE_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                Country l_countryfrom = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                Country l_countryto = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[2]));
                l_numArmies = Integer.parseInt(l_cmdSplit[3]);
                p_gameManager.getD_gamePhase().advance(p_gameManager, l_currentPlayer, l_countryfrom, l_countryto, l_numArmies);
                LogManager.logAction("Advance order issued by " + l_currentPlayer.getD_playerName());
                break;

            case Commands.BOMB_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                l_country = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                p_gameManager.getD_gamePhase().bomb(p_gameManager, l_currentPlayer, l_country);
                LogManager.logAction("Bomb order issued by " + l_currentPlayer.getD_playerName());
                break;

            case Commands.AIRLIFT_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                Country l_countryFrom = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                Country l_countryTo = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[2]));
                l_numArmies = Integer.parseInt(l_cmdSplit[3]);
                p_gameManager.getD_gamePhase().airlift(p_gameManager, l_currentPlayer, l_countryFrom, l_countryTo, l_numArmies);
                LogManager.logAction("Airlift order issued by " + l_currentPlayer.getD_playerName());
                break;

            case Commands.BLOCKADE_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                l_country = p_gameManager.getD_map().getD_countryByID(Integer.parseInt(l_cmdSplit[1]));
                p_gameManager.getD_gamePhase().blockade(p_gameManager, l_currentPlayer, l_country);
                LogManager.logAction("Blockade order issued by " + l_currentPlayer.getD_playerName() + " on country ID " + l_cmdSplit[1]);
                break;

            case Commands.DIPLOMACY_ORDER:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                Player l_targetPlayer = p_gameManager.findPlayerByName(l_cmdSplit[1]);
                p_gameManager.getD_gamePhase().negotiate(p_gameManager, l_currentPlayer, l_targetPlayer);
                LogManager.logAction("Diplomacy order issued by " + l_currentPlayer.getD_playerName() + " towards player " + l_cmdSplit[1]);
                break;

            case Commands.COMMIT:
                l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                if (l_currentPlayer.getD_numArmies() > 0) {

                    System.out.println("cannot end turn\n" + l_currentPlayer.getD_numArmies()
                            + " reinforcement army/armies left to be placed");

                    break;
                }
                //Updating the player turn.
                p_gameManager.addPlayerToSkipList(p_gameManager.getD_currentPlayerTurn());
                p_gameManager.updatePlayerTurn();
                LogManager.logAction("Turn ended by " + l_currentPlayer.getD_playerName());
                break;

            case Commands.LOAD_GAME:
                p_gameManager.getD_gamePhase().loadGame(p_gameManager, l_cmdSplit[1]);
                break;

            case Commands.SAVE_GAME:
                p_gameManager.getD_gamePhase().saveGame(p_gameManager, l_cmdSplit[1]);
                break;

            case "exit":
                break;

            default:
                displayError();

        }
    }
}

