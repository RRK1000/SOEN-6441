package controller;

import models.Country;
import models.Map;
import models.Order;
import models.Player;
import util.MapUtil;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

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
    private static void validateInput(String p_input) throws IllegalArgumentException {
        if (p_input == null || p_input.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input provided.");
        }
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
        validateInput(p_input);
        Scanner sc = new Scanner(System.in);
        String l_mapName = null;
        while (!Objects.equals(p_input, "exit")) {
            String[] l_inpcmd = p_input.split(" -");
            System.out.println("Command: " + l_inpcmd[0]);

            Map l_map = new Map();
            MapUtil l_mapUtil = new MapUtil();

            switch (l_inpcmd[0]) {
                case "help":
                    System.out.println("showmap        : show all continents and countries and their respective neighbors");
                    System.out.println("savemap        : save the map file to the text file");
                    System.out.println("editmap        : edit an existing saved map or create a new map");
                    System.out.println("validatemap    : validate the map");
                    System.out.println("editneighbour  : used to add or remove the neighbour country");
                    System.out.println("editcontinent  : used to add or remove the continent");
                    System.out.println("editcountry    : used to add or remove the country");
                    System.out.println("gameplayer     : add or remove players from the game");
                    System.out.println("assigncountries: countries assigned to the players in the start of the game");
                    System.out.println("deploy         : deploy the command given by player to attack");
                    System.out.println("exit           : exit the game");
                    break;

                case "showmap":
                    System.out.println("Showing map");
                    p_gameManager.showMap();
                    break;


                case "editneighbor":
                    for (int i = 1; i < l_inpcmd.length; i++) {
                        if (l_inpcmd[i].startsWith("add")) {
                            String[] addParams = l_inpcmd[i].split(" ");
                            if (addParams.length >= 3) {
                                int l_countryID = Integer.parseInt(addParams[1]);
                                int l_neighbourID = Integer.parseInt(addParams[2]);
                                l_mapUtil.addNeighbour(l_map, l_countryID, l_neighbourID);
                                System.out.println("adding neighbor country: " + l_neighbourID);
                            } else if (l_inpcmd[i].startsWith("remove")) {
                                String[] removeParams = l_inpcmd[i].split(" ");
                                if (removeParams.length >= 3) {
                                    int l_countryID = Integer.parseInt(removeParams[1]);
                                    int l_neighbourID = Integer.parseInt(removeParams[2]);
                                    l_mapUtil.removeNeighbour(l_map, l_countryID, l_neighbourID);
                                    System.out.println("removing neighbour country: " + l_neighbourID);
                                }
                            }
                        }
                    }
                    break;

                case "editcontinent":

                    for (int i = 1; i < l_inpcmd.length; i++) {
                        if (l_inpcmd[i].startsWith("add")) {
                            String[] l_addParams = l_inpcmd[i].split(" ");
                            if (l_addParams.length >= 3) {
                                int l_continentID = Integer.parseInt(l_addParams[1]);
                                int l_continentValue = Integer.parseInt(l_addParams[2]);
                                l_mapUtil.addContinent(l_map, l_continentID, l_continentValue);
                                System.out.println("adding continent: " + l_continentID);
                            }
                        } else if (l_inpcmd[i].startsWith("remove")) {
                            String[] l_removeParams = l_inpcmd[i].split(" ");
                            if (l_removeParams.length >= 2) {
                                int l_continentID = Integer.parseInt(l_removeParams[1]);
                                l_mapUtil.removeContinent(l_map, l_continentID);
                                System.out.println("removing continent: " + l_continentID);
                            }
                        }
                    }
                    break;


                case "editcountry":
                    for (int i = 1; i < l_inpcmd.length; i++) {
                        if (l_inpcmd[i].startsWith("add")) {
                            String[] addParams = l_inpcmd[i].split(" ");
                            if (addParams.length >= 3) {
                                int l_countryID = Integer.parseInt(addParams[1]);
                                int l_continentID = Integer.parseInt(addParams[2]);
                                l_mapUtil.addCountry(l_map, l_countryID, l_continentID);
                                System.out.println("adding country: " + l_countryID);
                            }
                        } else if (l_inpcmd[i].startsWith("remove")) {
                            String[] removeParams = l_inpcmd[i].split(" ");
                            if (removeParams.length >= 2) {
                                int l_countryID = Integer.parseInt(removeParams[1]);
                                l_mapUtil.removeCountry(l_map, l_countryID);
                                System.out.println("removing country: " + l_countryID);
                            }
                        }
                    }
                    break;

                case "savemap":
                    System.out.println("Saving the map");
                    l_mapUtil.saveMap(l_map);
                    break;

                case "editmap":
                    System.out.println("editing the map");
                    l_mapUtil.editMap(l_mapName);
                    break;

                case "validatemap":
                    System.out.println("Validating the map");
                    if (l_mapUtil.isValidMap(l_map)) {
                        System.out.println("Map validation succesful");
                    } else {
                        System.out.println("Map validation unsuccessful");
                    }
                    break;

                case "loadmap":
                    System.out.println("Enter the name of the map: ");
                    l_mapName = sc.nextLine();
                    File l_file = new File("src/main/resources/" + l_mapName);
                    if (l_file.exists()) {
                        System.out.println("Loading the map");
                        l_mapUtil.loadMap(l_mapName);
                    } else {
                        System.out.println("Map does not exist. Start a new game.");
                    }

                    break;

                case "gameplayer":
                    for (int i = 1; i <= l_inpcmd.length; i++) {
                        if (l_inpcmd[i].startsWith("add")) {
                            String[] addParams = l_inpcmd[i].split(" ");
                            if (addParams.length >= 2) {
                                String l_playername = addParams[1];
                                p_gameManager.addPlayer(l_playername);
                                System.out.println("Adding player: " + l_playername);
                            }
                        } else if (l_inpcmd[i].startsWith("remove")) {
                            String[] removeParams = l_inpcmd[i].split(" ");
                            if (removeParams.length >= 2) {
                                String l_playername = removeParams[1];
                                p_gameManager.removePlayer(l_playername);
                                System.out.println("Removing player: " + l_playername);
                            }
                        }
                    }
                    break;

                case "assigncountries":
                    System.out.println("Assigning countries to the players");
                    break;


                case "deploy":
                    while (!p_gameManager.check_armies()) {
                        for (Player l_player : p_gameManager.getD_playerList()) {
                            System.out.println(l_player.getD_playerName() + ", enter your deploy command:");
                            String l_deployCommand = sc.nextLine();
                            String[] l_deployParams = l_deployCommand.split(" ");

                            if (l_deployParams[0].equals("deploy") && l_deployParams.length == 3) {
                                int l_countryID = Integer.parseInt(l_deployParams[1]);
                                int l_numArmies = Integer.parseInt(l_deployParams[2]);

                                if (l_numArmies <= l_player.getD_numArmies()) {
                                    Country l_targetCountry = p_gameManager.getD_map().getD_countryByID(l_countryID);
                                    Order l_deployOrder = new Order(l_targetCountry, l_numArmies);
                                    l_deployOrder.setD_country(l_targetCountry);
                                    l_deployOrder.setD_num(l_numArmies);
                                    l_player.issueOrder(l_deployOrder);
                                    l_player.setD_numArmies(l_player.getD_numArmies() - l_numArmies);
                                } else {
                                    System.out.println("You don't have enough armies to deploy.");
                                }
                            } else {
                                System.out.println("Invalid deploy command. Please try again.");
                            }
                        }
                    }
                    System.out.println("All reinforcements have been placed.");
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + l_inpcmd[0]);
            }


        }
    }
}
