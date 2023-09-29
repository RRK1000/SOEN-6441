package controller;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;

import models.Map;
import util.MapUtil;

public class GameEngine {
    GameManager d_gameManager;

    public void main(String[] p_args){
        Map l_map = new Map();
        d_gameManager = new GameManager(l_map);
        Scanner l_sc = new Scanner(System.in);
        String l_mapname;
        String l_usercommand;
        System.out.println("--- WELCOME TO WARZONE ---");
        System.out.println("--- STARTING THE GAME ---");
        System.out.println("--- MENU ---");
        System.out.println("1. Start an existing game");
        System.out.println("2. Start an new game");
        System.out.println("3. Exit");
        int l_userinput;
        System.out.println("Choose an option from the menu");
        l_userinput = l_sc.nextInt();

        switch (l_userinput){
            case 1:

                System.out.println("Starting an existing game...");
                System.out.println("Command : ");
                l_usercommand = l_sc.nextLine();
                inputParser(l_usercommand);
                break;

            case 2:
                System.out.println("Starting a new game");
                System.out.println("Command : ");
                l_usercommand = l_sc.nextLine();
                inputParser(l_usercommand);
                break;

            default:
                System.out.println("Please enter a correct option.");
        }
    }


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
    private void validateInput(String p_input) throws IllegalArgumentException {
        if (p_input == null || p_input.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input provided.");
        }
    }


    /**
     * This method is used to read the command given by the player and validates if the syntax
     * of the command is correct and then takes the action accordingly
     * @param d_input - Command entered by the player
     * @author - Abhigyan
     * @author - Nimisha
     * @author - Yusuke
     * @version - 1.0.0
     */
    public void inputParser(String d_input) {

        Scanner sc = new Scanner(System.in);
        String l_mapname=null;
        while (!Objects.equals(d_input, "exit")) {
            String[] l_inpcmd = d_input.split(" -");
            System.out.println("Command: " + l_inpcmd[0]);

            Map l_map = new Map();
            MapUtil mp = new MapUtil();

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
                    mp.showMap(l_map);
                    break;


                case "editneighbor":
                    for (int i = 1; i < l_inpcmd.length; i++) {
                        if (l_inpcmd[i].startsWith("add")) {
                            String[] addParams = l_inpcmd[i].split(" ");
                            if (addParams.length >= 3) {
                                int l_countryID = Integer.parseInt(addParams[1]);
                                int l_neighbourID = Integer.parseInt(addParams[2]);
                                mp.addNeighbour(l_map, l_countryID, l_neighbourID);
                                System.out.println("adding neighbor country: " + l_neighbourID);
                            } else if (l_inpcmd[i].startsWith("remove")) {
                                String[] removeParams = l_inpcmd[i].split(" ");
                                if (removeParams.length >= 3) {
                                    int l_countryID = Integer.parseInt(removeParams[1]);
                                    int l_neighbourID = Integer.parseInt(removeParams[2]);
                                    mp.removeNeighbour(l_map, l_countryID, l_neighbourID);
                                    System.out.println("removing neighbour country: " + l_neighbourID);
                                }
                            }
                        }
                    }
                    break;

                case "editcontinent":

                    for (int i = 1; i < l_inpcmd.length; i++) {
                        if (l_inpcmd[i].startsWith("add")) {
                            String[] addParams = l_inpcmd[i].split(" ");
                            if (addParams.length >= 3) {
                                int l_continentID = Integer.parseInt(addParams[1]);
                                int l_continentValue = Integer.parseInt(addParams[2]);
                                mp.addContinent(l_map, l_continentID, l_continentValue);
                                System.out.println("adding continent: " + l_continentID);
                            }
                        } else if (l_inpcmd[i].startsWith("remove")) {
                            String[] removeParams = l_inpcmd[i].split(" ");
                            if (removeParams.length >= 2) {
                                int continentID = Integer.parseInt(removeParams[1]);
                                mp.removeContinent(l_map, continentID);
                                System.out.println("removing continent: " + continentID);
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
                                mp.addCountry(l_map, l_countryID, l_continentID);
                                System.out.println("adding country: " + l_countryID);
                            }
                        } else if (l_inpcmd[i].startsWith("remove")) {
                            String[] removeParams = l_inpcmd[i].split(" ");
                            if (removeParams.length >= 2) {
                                int l_countryID = Integer.parseInt(removeParams[1]);
                                mp.removeCountry(l_map, l_countryID);
                                System.out.println("removing country: " + l_countryID);
                            }
                        }
                    }
                    break;

                    case "savemap":
                        System.out.println("Saving the map");
                        mp.saveMap(l_map);
                        break;

                    case "editmap":
                        System.out.println("editing the map");
                        mp.editMap(l_mapname);
                        break;

                    case "validatemap":
                        System.out.println("Validating the map");
                        if(mp.isValidMap(l_map)){
                            System.out.println("Map validation succesful");
                        } else {
                            System.out.println("Map validation unsuccessful");
                        }
                        break;

                    case "loadmap":
                        System.out.println("Enter the name of the map: ");
                        l_mapname = sc.nextLine();
                        File l_file = new File("src/main/resources/"+l_mapname);
                        if(l_file.exists()){
                            System.out.println("Loading the map");
                            mp.loadMap(l_mapname);
                        } else {
                            System.out.println("Map does not exist. Start a new game.");
                        }

                        break;

                    case "gameplayer":
                        for(int i=1; i<=l_inpcmd.length; i++){
                            if(l_inpcmd[i].startsWith("add")){
                                String[] addParams = l_inpcmd[i].split(" ");
                                if (addParams.length >= 2) {
                                    String l_playername = addParams[1];
                                    d_gameManager.addPlayer(l_playername);
                                    System.out.println("Adding player: " + l_playername);
                                }
                            } else if(l_inpcmd[i].startsWith("remove")) {
                                String[] removeParams = l_inpcmd[i].split(" ");
                                if (removeParams.length >= 2) {
                                    String l_playername = removeParams[1];
                                    d_gameManager.removePlayer(l_playername);
                                    System.out.println("Removing player: " + l_playername);
                                }
                            }
                        }
                        break;

                    case "assigncountries":
                        System.out.println("Assigning countries to the players");
                        break;

                    case "deploy":



                default:
                    throw new IllegalStateException("Unexpected value: " + l_inpcmd[0]);
            }


        }
    }
}


