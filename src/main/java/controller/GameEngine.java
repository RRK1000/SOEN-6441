package controller;
import java.util.ArrayList;

import models.*;
import org.apache.commons.*;
import org.apache.commons.cli.*;

public class GameEngine {


    public static void main(String[] p_args){
        Options options = new Options();
        options.addOption("h", "help", false, "Print help message");
        options.addOption("f", "file", true, "Input file");

        // Create a command-line parser
        CommandLineParser parser = new DefaultParser();

        try {
            // Parse the command-line arguments
            CommandLine cmd = parser.parse(options, p_args);

            // Check for specific options and perform actions based on them
            if (cmd.hasOption("help")) {
                // Handle the --help option (e.g., display usage information)
                printHelp(options); // You can define a method to print usage information
            }

            if (cmd.hasOption("file")) {
                String inputFile = cmd.getOptionValue("file");
                // Process the input file option and its value
                // Add your code here
            }

            // Continue with your application logic
            // ...

        } catch (ParseException e) {
            // Handle parsing errors (e.g., display an error message)
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
            printHelp(options); // Optionally, print usage information after an error
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("YourApplication", options);
    }
    /*
    public static ArrayList<Player> d_playerList = new ArrayList<Player>();


    public static void gameMenu(){
        GameStartup gameStartup = new GameStartup();

        int d_selectMenuOption = gameStartup.menuGame();
        do{
            switch (d_selectMenuOption){
                case 1:
                    gameStartup.loadGame();
                    break;
                case 2:
                    gameStartup.newGame();
                    break;
                case 3:
                    System.out.println("Exiting from the game");
                    break;
                default:
                    System.out.println("Incorrect Input! Enter the correct option");
            }
        }while(d_selectMenuOption!=3);
    }*/

}
