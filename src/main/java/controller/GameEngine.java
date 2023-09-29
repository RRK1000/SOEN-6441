package controller;
import java.util.Objects;



import models.Map;
import util.MapUtil;

public class GameEngine {
    GameManager d_gameManager;

    public void main(String[] p_args){
        Map l_map = new Map();
        d_gameManager = new GameManager(l_map);
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

        while (!Objects.equals(d_input, "exit")) {
            String[] l_inpcmd = d_input.split(" -");
            System.out.println("Command: " + l_inpcmd[0]);

            Map l_map = new Map();
            MapUtil mp = new MapUtil();

            switch (l_inpcmd[0]) {
                case "showmap":
                    System.out.println("Shwoing map");
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
                        break;
                    }


                    case "savemap":
                        System.out.println("Saving the map");
                        mp.saveMap(l_map);

                    case "editmap":
                        System.out.println("editing the map");
                        mp.editMap();

                        case "validatemap":

                        case "loadmap":

                        case "gameplayer":

                        case "assigncountries":

                        case "deploy":


                default:
                    throw new IllegalStateException("Unexpected value: " + l_inpcmd[0]);
            }


        }
    }



}


