package controller;

import global.Constants;
import models.Country;
import models.Map;
import models.Player;
import util.MapUtil;

/** Represents the command parser.
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
     * Validates the provided input string.
     * <p>
     * This method checks if the input string is null or empty.
     * </p>
     *
     * @param p_input The input string to be validated.
     * @return Boolean - true if the input string is not null else false.
     */
    public static Boolean isvalidInput(String p_input) {
        if (p_input == null || p_input.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Checks the initial phase of the game.
     * @param p_gameManager Instance of Game Manager class
     * @return Boolean true if current phase is initial phase or else retuen false.
     */
    public static Boolean isValidMapInitInput(GameManager p_gameManager) {
        if (p_gameManager.getD_gamePhase() != GameManager.GamePhase.Map_Init) {
            System.out.println(Constants.CMD_ERROR);
            System.out.println(Constants.HELP_MESSAGE);

            return false;
        }
        return true;
    }

    /**
     * Displays the current phase of the game
     * @param p_gameManager {@link GameManager}
     */
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
     * @param p_gameManager {@link GameManager}
     */
    public static void inputParser(GameManager p_gameManager, String p_input) {
        String[] l_cmdSplit = p_input.split(" ");

        Map l_map;
        l_map = p_gameManager.getD_map();

        switch (l_cmdSplit[0]) {
            case "help":
                displayInstructions(p_gameManager);
                break;

            case "showmap":
                System.out.println("Showing map");
                if (isValidMapInitInput(p_gameManager)) {
                    MapUtil.showMap(l_map);
                } else {
                    p_gameManager.showMap();
                }
                break;


            case "editneighbor":
                if (isValidMapInitInput(p_gameManager)) {
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
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else if (l_editNeighbourInput[i].startsWith("remove")) {
                            String[] removeParams = l_editNeighbourInput[i].split(" ");
                            if (removeParams.length == 3) {
                                int l_countryID = Integer.parseInt(removeParams[1]);
                                int l_neighbourID = Integer.parseInt(removeParams[2]);
                                System.out.println("removing neighbour country: " + l_neighbourID);
                                MapUtil.removeNeighbour(l_map, l_countryID, l_neighbourID);
                            } else {
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else {
                            System.out.println(Constants.CMD_ERROR);
                        }
                    }
                    System.out.println("EditNeighbor command execution completed.");
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;

            case "editcontinent":
                if (isValidMapInitInput(p_gameManager)) {
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
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else if (l_inputSplit[i].startsWith("remove")) {
                            String[] l_removeParams = l_inputSplit[i].split(" ");
                            if (l_removeParams.length == 2) {
                                int l_continentID = Integer.parseInt(l_removeParams[1]);
                                System.out.println("removing continent: " + l_continentID);
                                MapUtil.removeContinent(l_map, l_continentID);

                            } else {
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else {
                            System.out.println(Constants.CMD_ERROR);
                        }
                    }
                    System.out.println("EditContinent command execution completed.");
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;


            case "editcountry":
                if (isValidMapInitInput(p_gameManager)) {
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
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else if (l_editCountryInput[i].startsWith("remove")) {
                            String[] removeParams = l_editCountryInput[i].split(" ");
                            if (removeParams.length == 2) {
                                int l_countryID = Integer.parseInt(removeParams[1]);
                                System.out.println("removing country: " + l_countryID);
                                MapUtil.removeCountry(l_map, l_countryID);
                            } else {
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else {
                            System.out.println(Constants.CMD_ERROR);
                        }
                    }
                    System.out.println("EditCountry command execution completed.");
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;

            case "savemap":
                if (isValidMapInitInput(p_gameManager)) {
                    System.out.println("Saving the map");
                    Boolean l_isMapSaved = MapUtil.saveMap(l_map);
                    if (l_isMapSaved) {
                        System.out.println("Map is saved");
                        System.out.println("Load the map to start the game.");
                    } else {
                        System.out.println("The map was not saved");
                    }
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;

            case "editmap":
                if (isValidMapInitInput(p_gameManager)) {
                    p_gameManager.setD_map(MapUtil.editMap(l_cmdSplit[1]));
                    System.out.println("Map loaded to be edited...");
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;

            case "validatemap":
                if (isValidMapInitInput(p_gameManager)) {
                    System.out.println("Validating the map");
                    if (MapUtil.isValidMap(l_map)) {
                        System.out.println("Map validation successful");
                        p_gameManager.d_gamePhase = GameManager.GamePhase.Game_Startup;
                    } else {
                        System.out.println("Map validation unsuccessful");
                    }
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;

            case "loadmap":
                if (isValidMapInitInput(p_gameManager)) {
                    l_map = MapUtil.loadMap(l_cmdSplit[1]);
                    if (MapUtil.isValidMap(l_map)) {
                        p_gameManager.d_gamePhase = GameManager.GamePhase.Game_Startup;
                        System.out.println("Next, add players to the game");
                    } else {
                        l_map = new Map();
                    }
                    p_gameManager.setD_map(l_map);
                    System.out.println(Constants.HELP_MESSAGE);
                } else {
                    System.out.println(Constants.CMD_ERROR);
                    System.out.println(Constants.HELP_MESSAGE);
                }
                break;

            case "gameplayer":
                p_gameManager.d_gamePhase = GameManager.GamePhase.Game_Startup;

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
                p_gameManager.assignCountries();
                p_gameManager.d_gamePhase = GameManager.GamePhase.IssueOrder;
                System.out.println("Game has Started!");
                break;

            case "deploy":
                Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());

                if (l_cmdSplit.length < 3) {
                    System.out.println(Constants.CMD_ERROR);
                    break;
                }
                int l_countryID = Integer.parseInt(l_cmdSplit[1]);
                Country l_country = p_gameManager.getD_map().getD_countryByID(l_countryID);

                int l_numArmies = Integer.parseInt(l_cmdSplit[2]);
                p_gameManager.issueOrder(l_country, l_numArmies);

                if (l_currentPlayer.getD_numArmies() == 0) {
                    System.out.println("Player " + l_currentPlayer.getD_playerName() + " turn over. ");
                    System.out.println();
                    p_gameManager.updatePlayerTurn();
                    if(p_gameManager.getD_currentPlayerTurn() == 0){
                        p_gameManager.executeOrder();
                    }
                    l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());
                    System.out.println("Player " + l_currentPlayer.getD_playerName() + "'s turn ");
                } else {
                    System.out.println("Available Reinforcement armies: " + l_currentPlayer.getD_numArmies());
                }
                break;
            default:
                System.out.println(Constants.CMD_ERROR);
                System.out.println(Constants.HELP_MESSAGE);
        }

    }
}

