package controller;
import java.util.Objects;

//import org.apache.commons.cli.HelpFormatter;

import models.Map;
import util.MapUtil;

public class GameEngine {
    GameManager d_gameManager;

    public void main(String[] p_args){
        Map l_map = new Map();
        d_gameManager = new GameManager(l_map);
    }

//    private static void printHelp(Options options) {
//        HelpFormatter formatter = new HelpFormatter();
//        formatter.printHelp("YourApplication", options);
//    }


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
    public void inputParser(String d_input){

        while(!Objects.equals(d_input,"exit")){
            String[] l_inpcmd = d_input.split(" -");
            System.out.println("Command: "+ l_inpcmd[0]);

            Map l_map = new Map();

            switch(l_inpcmd[0]){
                case "editcontinent":
                    MapUtil mp = new MapUtil();
                    for(int i=1; i<l_inpcmd.length; i++){
                        if(l_inpcmd[i].startsWith("add")) {
                            String[] addParams = l_inpcmd[i].split(" ");
                            if(addParams.length >= 3) {
                                int continentID = Integer.parseInt(addParams[1]);
                                int continentValue = Integer.parseInt(addParams[2]);
                                mp.addContinent(l_map, continentID, continentValue);
                                System.out.println("adding continent: " + continentID);
                            }
                        }
                        else if(l_inpcmd[i].startsWith("remove")) {
                            String[] removeParams = l_inpcmd[i].split(" ");
                            if(removeParams.length >= 2) {
                                int continentID = Integer.parseInt(removeParams[1]);
                                mp.removeContinent(l_map, continentID);
                                System.out.println("removing continent: " + continentID);
                            }
                        }
                    }
                    break;
        


                case "editcountry":
                    for(int i=1; i<l_inpcmd.length; i++){
                        if(l_inpcmd[i].equals("add countryID continentID")){
                            //util.MapUtil.addContinent();
                            System.out.println("adding country: ");
                        }
                        else if(l_inpcmd[i].equals("remove continentID")){
                            //util.MapUtil.removeContinent();
                            System.out.println("removing continent: ");
                        }
                    }
                    break;

                case "editneighbor":

                case "showmap":

                case "savemap":

                case "editmap":

                case "validatemap":

                case "loadmap":

                case "gameplayer":

                case "assigncountries":

                case "deploy":

                default:
                    System.out.println("Please enter a valid command.");
            }
        }




    }



}

