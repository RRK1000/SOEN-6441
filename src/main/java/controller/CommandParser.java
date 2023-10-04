package controller;

import global.Constants;
import models.Country;
import models.Map;
import models.Player;
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

    /**
     * Checks the initial phase of the game.
     *
     * @param p_gameManager Instance of Game Manager class
     * @return Boolean true if current phase is initial phase or else return false.
     */
    public static Boolean isGamePhaseMapInit(GameManager p_gameManager) {
        return p_gameManager.getD_gamePhase() == GamePhase.Map_Init;
    }

    private static void displayError() {
        System.out.println(Constants.CMD_ERROR);
        System.out.println(Constants.HELP_MESSAGE);
    }

    /**
     * Displays the current phase of the game
     *
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
     * @param p_input       - Command entered by the player
     * @param p_gameManager {@link GameManager}
     */
    public static void inputParser(GameManager p_gameManager, String p_input) {
        //Breaks the input command around space.
        String[] l_cmdSplit = p_input.split(" ");

        Map l_map;
        l_map = p_gameManager.getD_map();

        //Handles index 0 of the input command given by the player.
        switch (l_cmdSplit[0]) {
            //Displays the help command
            case "help":
                displayInstructions(p_gameManager);
                break;

            case "showmap":
                if (l_map == null) {
                    System.out.println("Map not loaded");
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                }

                if (p_gameManager.getD_gamePhase() == GamePhase.Map_Init
                        || p_gameManager.getD_gamePhase() == GamePhase.Game_Startup) {
                    MapUtil.showMap(l_map);
                } else if (p_gameManager.getD_gamePhase() == GamePhase.AssignReinforcements
                        || p_gameManager.getD_gamePhase() == GamePhase.IssueOrder) {
                    p_gameManager.showMap();
                }
                break;

            case "editneighbor":
                if (isGamePhaseMapInit(p_gameManager)) {
                    String[] l_editNeighbourInput = p_input.split(" -");
                    for (int i = 1; i < l_editNeighbourInput.length; i++) {
                        //Handles the command to add the neighbour country
                        if (l_editNeighbourInput[i].startsWith("add")) {
                            String[] addParams = l_editNeighbourInput[i].split(" ");
                            //checks if add is followed by countryID and neighborcountryID
                            if (addParams.length == 3) {
                                int l_countryID = Integer.parseInt(addParams[1]);
                                int l_neighbourID = Integer.parseInt(addParams[2]);
                                System.out.println("adding neighbor country: " + l_neighbourID);
                                //Calls addNeighbour() for adding the neighbour country
                                MapUtil.addNeighbour(l_map, l_countryID, l_neighbourID);
                            } else {
                                System.out.println(Constants.CMD_ERROR);
                            }
                            //Handles the command to remove the neighbour country
                        } else if (l_editNeighbourInput[i].startsWith("remove")) {
                            String[] removeParams = l_editNeighbourInput[i].split(" ");
                            //checks if remove is followed by countryID and neighborcountryID
                            if (removeParams.length == 3) {
                                int l_countryID = Integer.parseInt(removeParams[1]);
                                int l_neighbourID = Integer.parseInt(removeParams[2]);
                                System.out.println("removing neighbour country: " + l_neighbourID);
                                //Calls removeNeighbour() for removing the neighbour country
                                MapUtil.removeNeighbour(l_map, l_countryID, l_neighbourID);
                            } else {
                                System.out.println(Constants.CMD_ERROR);
                            }
                        } else {
                            System.out.println(Constants.CMD_ERROR);
                        }
                    }
                } else {
                    //Displays error if the command is invalid
                    displayError();
                }
                break;

            case "editcontinent":
                if (isGamePhaseMapInit(p_gameManager)) {
                    String[] l_inputSplit = p_input.split(" -");
                    for (int i = 1; i < l_inputSplit.length; i++) {
                        //Handles the command to add continent
                        if (l_inputSplit[i].startsWith("add")) {
                            String[] l_addParams = l_inputSplit[i].split(" ");
                            //checks if add is followed by continentID and continentvalue
                            if (l_addParams.length == 3) {
                                int l_continentID = Integer.parseInt(l_addParams[1]);
                                int l_continentValue = Integer.parseInt(l_addParams[2]);
                                System.out.println("adding continent: " + l_continentID);
                                MapUtil.addContinent(l_map, l_continentID, l_continentValue);
                            } else {
                                System.out.println(Constants.CMD_ERROR);
                            }
                            //Handles the command to remove the continent
                        } else if (l_inputSplit[i].startsWith("remove")) {
                            String[] l_removeParams = l_inputSplit[i].split(" ");
                            //checks if remove is followed by continentID
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
                } else {
                    displayError();
                }
                break;

            case "editcountry":
                if (isGamePhaseMapInit(p_gameManager)) {
                    String[] l_editCountryInput = p_input.split(" -");
                    for (int i = 1; i < l_editCountryInput.length; i++) {
                        //checks if add is followed by countryID and continentID
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
                            //checks if remove is followed by countryID
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
                } else {
                    displayError();
                }
                break;

            case "savemap":
                //Checks if the savemap command is followed by the filename
                if (l_cmdSplit.length == 2 && isGamePhaseMapInit(p_gameManager)) {
                    System.out.println("Saving the map");
                    //calls saveMap() function
                    Boolean l_isMapSaved = MapUtil.saveMap(l_map, l_cmdSplit[1]);
                    //Checks if the map was saved successfully
                    if (l_isMapSaved) {
                        System.out.println("Map is saved");
                        System.out.println("Load the map to start the game.");
                    } else {
                        System.out.println("The map was not saved");
                    }
                } else {
                    displayError();
                }
                break;

            case "editmap":
                if (isGamePhaseMapInit(p_gameManager)) {
                    //calls the editMap() function
                    p_gameManager.setD_map(MapUtil.editMap(l_cmdSplit[1]));
                    System.out.println("Map loaded to be edited...");
                } else {
                    displayError();
                }
                break;

            case "validatemap":
                if (l_map != null && isGamePhaseMapInit(p_gameManager)) {
                    System.out.println("Validating the map");
                    //Checks if the map is valid or not
                    if (MapUtil.isValidMap(l_map)) {
                        p_gameManager.setD_map(l_map);
                    } else {
                        System.out.println("Map validation unsuccessful");
                    }
                } else {
                    displayError();
                }
                break;

            case "loadmap":
                if (isGamePhaseMapInit(p_gameManager)) {
                    //calls loadMap() function
                    l_map = MapUtil.loadMap(l_cmdSplit[1]);
                    if (!l_map.getD_countryMapGraph().vertexSet().isEmpty() && MapUtil.isValidMap(l_map)) {
                        //Set the game phase to start up phase
                        p_gameManager.setD_gamePhase(GamePhase.Game_Startup);
                        p_gameManager.setD_map(l_map);

                        System.out.println("Next, add players to the game");
                    } else {
                        System.out.println("Map validation unsuccessful");
                        break;
                    }
                    System.out.println(Constants.HELP_MESSAGE);
                } else {
                    displayError();
                }
                break;

            case "gameplayer":
                p_gameManager.setD_gamePhase(GamePhase.Game_Startup);
                if (p_gameManager.getD_map() == null) {
                    System.out.println("Map not loaded");
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                }
                //Handles the case to add or remove player
                for (int i = 1; i < l_cmdSplit.length - 1; i++) {
                    if (l_cmdSplit[i].startsWith("-add") && i + 1 < l_cmdSplit.length
                            && !l_cmdSplit[i + 1].startsWith("-")) {
                        String l_playername = l_cmdSplit[i + 1];
                        //calls addPlayer() to add the Player in the game
                        p_gameManager.addPlayer(l_playername);
                    } else if (l_cmdSplit[i].startsWith("-remove") && i + 1 < l_cmdSplit.length
                            && !l_cmdSplit[i + 1].startsWith("-")) {
                        String l_playername = l_cmdSplit[i + 1];
                        //calls removePlayer() to remove Player from the game
                        p_gameManager.removePlayer(l_playername);
                    }
                }
                break;

            case "assigncountries":
                //assign countries to the players
                p_gameManager.setD_gamePhase(GamePhase.AssignReinforcements);
                p_gameManager.assignCountries();
                break;

            case "deploy":
                Player l_currentPlayer = p_gameManager.getD_playerList().get(p_gameManager.getD_currentPlayerTurn());

                //checks deploy command is followed by countryID and num
                if (l_cmdSplit.length < 3) {
                    displayError();
                    break;
                }
                //Getting the countryID
                int l_countryID = Integer.parseInt(l_cmdSplit[1]);
                Country l_country = p_gameManager.getD_map().getD_countryByID(l_countryID);
                //Getting number of armies
                int l_numArmies = Integer.parseInt(l_cmdSplit[2]);
                //call issueOrder()
                p_gameManager.issueOrder(l_country, l_numArmies);

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

