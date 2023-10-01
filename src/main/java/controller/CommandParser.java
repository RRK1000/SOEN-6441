package controller;

import global.Constants;
import models.Map;
import util.MapUtil;

public class CommandParser {

    /**
     * Validates the provided input string.
     * <p>
     * This method checks if the input string is null or empty. If the input is invalid,
     * it throws an IllegalArgumentException.
     * </p>
     *
     * @param p_input The input string to be validated.
     * @throws IllegalArgumentException if the input string is null or empty.
     * @author Yusuke
     */
    public static void validateInput(String p_input) throws IllegalArgumentException {
        if (p_input == null || p_input.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input provided.");
        }
    }

    public static void displayInstructions(GameManager p_gameManager) {
        switch (p_gameManager.getD_gamePhase()) {
            case Map_Init:
                System.out.println(Constants.MAP_INIT_HELP);
                break;
            case Game_Startup:
                System.out.println(Constants.GAME_STARTUP_HELP);
                break;
            case IssueOrder:
                System.out.println(Constants.ISSUE_ORDER_HELP);
                break;
            default:
                System.out.println(Constants.IN_GAME_HELP);
        }
        System.out.println(Constants.GAME_EXIT);
    }


    /**
     * This method is used to read the command given by the player and validates if the syntax
     * of the command is correct and then takes the action accordingly
     *
     * @param p_input - Command entered by the player
     * @author - Abhigyan
     * @author - Nimisha
     * @author - Yusuke
     */
    public static void inputParser(GameManager p_gameManager, String p_input) {
        String[] l_cmdSplit = p_input.split(" ");
        System.out.println("Command: " + l_cmdSplit[0]);

        Map l_map = new Map();
        l_map = p_gameManager.getD_map();

        switch (l_cmdSplit[0]) {
            case "help":
                displayInstructions(p_gameManager);
                break;

            case "showmap":
                System.out.println("Showing map");
                p_gameManager.showMap();
                break;


            case "editneighbor":
                String[] l_editNeighbourInput = p_input.split(" -");
                for (int i = 1; i < l_editNeighbourInput.length; i++) {
                    if (l_editNeighbourInput[i].startsWith("add")) {
                        String[] addParams = l_editNeighbourInput[i].split(" ");
                        if (addParams.length == 3) {
                            int l_countryID = Integer.parseInt(addParams[1]);
                            int l_neighbourID = Integer.parseInt(addParams[2]);
                            System.out.println("adding neighbor country: " + l_neighbourID);
                            MapUtil.addNeighbour(l_map, l_countryID, l_neighbourID);
                        } else {
                            System.out.println("Command " + i + " is invalid.");
                        }
                    } else if (l_editNeighbourInput[i].startsWith("remove")) {
                        String[] removeParams = l_editNeighbourInput[i].split(" ");
                        if (removeParams.length == 3) {
                            int l_countryID = Integer.parseInt(removeParams[1]);
                            int l_neighbourID = Integer.parseInt(removeParams[2]);
                            System.out.println("removing neighbour country: " + l_neighbourID);
                            MapUtil.removeNeighbour(l_map, l_countryID, l_neighbourID);
                        } else {
                            System.out.println("Command " + i + " is invalid.");
                        }
                    } else {
                        System.out.println("Command " + i + " is invalid.");
                    }
                }
                System.out.println("EditNeighbor command execution completed.");
                break;

            case "editcontinent":
                String[] l_inputSplit = p_input.split(" -");
                for (int i = 1; i < l_inputSplit.length; i++) {
                    if (l_inputSplit[i].startsWith("add")) {
                        String[] l_addParams = l_inputSplit[i].split(" ");
                        if (l_addParams.length == 3) {
                            int l_continentID = Integer.parseInt(l_addParams[1]);
                            int l_continentValue = Integer.parseInt(l_addParams[2]);
                            System.out.println("adding continent: " + l_continentID);
                            MapUtil.addContinent(l_map, l_continentID, l_continentValue);
                        } else {
                            System.out.println("Command " + i + " is invalid.");
                        }
                    } else if (l_inputSplit[i].startsWith("remove")) {
                        String[] l_removeParams = l_inputSplit[i].split(" ");
                        if (l_removeParams.length == 2) {
                            int l_continentID = Integer.parseInt(l_removeParams[1]);
                            System.out.println("removing continent: " + l_continentID);
                            MapUtil.removeContinent(l_map, l_continentID);

                        } else {
                            System.out.println("Command " + i + " is invalid.");
                        }
                    } else {
                        System.out.println("Command " + i + " is invalid.");
                    }
                }
                System.out.println("EditContinent command execution completed.");
                break;


            case "editcountry":
                String[] l_editCountryInput = p_input.split(" -");
                for (int i = 1; i < l_editCountryInput.length; i++) {
                    if (l_editCountryInput[i].startsWith("add")) {
                        String[] addParams = l_editCountryInput[i].split(" ");
                        if (addParams.length == 3) {
                            int l_countryID = Integer.parseInt(addParams[1]);
                            int l_continentID = Integer.parseInt(addParams[2]);
                            System.out.println("adding country: " + l_countryID);
                            MapUtil.addCountry(l_map, l_countryID, l_continentID);
                        } else {
                            System.out.println("Command " + i + " is invalid.");
                        }
                    } else if (l_editCountryInput[i].startsWith("remove")) {
                        String[] removeParams = l_editCountryInput[i].split(" ");
                        if (removeParams.length == 2) {
                            int l_countryID = Integer.parseInt(removeParams[1]);
                            System.out.println("removing country: " + l_countryID);
                            MapUtil.removeCountry(l_map, l_countryID);
                        } else {
                            System.out.println("Command " + i + " is invalid.");
                        }
                    } else {
                        System.out.println("Command " + i + " is invalid.");
                    }
                }
                System.out.println("EditCountry command execution completed.");
                break;

            case "savemap":
                System.out.println("Saving the map");
                MapUtil.saveMap(l_map);
                break;

            case "editmap":
                p_gameManager.setD_map(MapUtil.editMap(l_cmdSplit[1]));
                System.out.println("Map loaded to be edited...");
                break;

            case "validatemap":
                System.out.println("Validating the map");
                if (MapUtil.isValidMap(l_map)) {
                    System.out.println("Map validation successful");
                    p_gameManager.d_gamePhase = GameManager.GamePhase.Game_Startup;
                } else {
                    System.out.println("Map validation unsuccessful");
                }
                break;

            case "loadmap":
                l_map = MapUtil.loadMap(l_cmdSplit[1]);
                if (MapUtil.isValidMap(l_map)) {
                    p_gameManager.d_gamePhase = GameManager.GamePhase.Game_Startup;
                    System.out.println("Next, add players to the game");
                } else {
                    l_map = new Map();
                }
                p_gameManager.setD_map(l_map);
                System.out.println(Constants.HELP_MESSAGE);
                break;

            case "gameplayer":
                for (int i = 1; i < l_cmdSplit.length - 1; i++) {
                    if (l_cmdSplit[i].startsWith("-add") && i + 1 < l_cmdSplit.length
                            && !l_cmdSplit[i + 1].startsWith("-")) {
                        String l_playername = l_cmdSplit[i + 1];
                        p_gameManager.addPlayer(l_playername);
                    } else if (l_cmdSplit[i].startsWith("-remove") && i + 1 < l_cmdSplit.length
                            && !l_cmdSplit[i + 1].startsWith("-")) {
                        String l_playername = l_cmdSplit[i + 1];
                        p_gameManager.removePlayer(l_playername);
                    }
                }
                break;

            case "assigncountries":
                System.out.println("Assigning countries to the players");
                p_gameManager.d_gamePhase = GameManager.GamePhase.AssignReinforcements;
                break;


            case "deploy":
                System.out.println("deploy called");
                break;
//                    while (!p_gameManager.check_armies()) {
//                        for (Player l_player : p_gameManager.getD_playerList()) {
//                            if (l_deployParams[0].equals("deploy") && l_deployParams.length == 3) {
//                                int l_countryID = Integer.parseInt(l_deployParams[1]);
//                                int l_numArmies = Integer.parseInt(l_deployParams[2]);
//
//                                if (l_numArmies <= l_player.getD_numArmies()) {
//                                    Country l_targetCountry = p_gameManager.getD_map().getD_countryByID(l_countryID);
//                                    Order l_deployOrder = new Order(l_targetCountry, l_numArmies);
//                                    l_deployOrder.setD_country(l_targetCountry);
//                                    l_deployOrder.setD_num(l_numArmies);
//                                    l_player.setD_currentOrder(l_deployOrder);
//                                    l_player.issueOrder();
//                                    l_player.setD_numArmies(l_player.getD_numArmies() - l_numArmies);
//                                } else {
//                                    System.out.println("You don't have enough armies to deploy.");
//                                }
//                            } else {
//                                System.out.println("Invalid deploy command. Please try again.");
//                            }
//                        }
//                    }
//                    System.out.println("All reinforcements have been placed.");
//                    break;
            default:
                System.out.println(Constants.CMD_ERROR);
                System.out.println(Constants.HELP_MESSAGE);
        }

    }
}

