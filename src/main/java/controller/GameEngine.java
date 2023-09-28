package controller;
import java.text.ParseException;

import org.apache.commons.*;
import org.apache.commons.cli.*;

public class GameEngine {


    public static void main(String[] p_args){
        Options options = new Options();
        options.addOption("h", "help", false, "Print help message");
        options.addOption("f", "file", true, "Input file");
        //options.addOption("e", "edit_neighbour", true, "Edit neighbor");

        Option editNeighbourOption = Option.builder("e")
                .longOpt("edit_neighbour")
                .hasArgs()
                .argName("subcommand")
                .desc("Edit neighbor relationships (add or remove)")
                .build();

        options.addOption(editNeighbourOption);

        // Create sub-options for "add" and "remove" within "edit_neighbour"
        Options editNeighbourSubOptions = new Options();
        editNeighbourSubOptions.addOption("a", "add", true, "Add neighbor relationship");
        editNeighbourSubOptions.addOption("r", "remove", true, "Remove neighbor relationship");

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

            if (cmd.hasOption("edit_neighbour")) {
                CommandLine editNeighbourCmd = parser.parse(editNeighbourSubOptions, cmd.getOptionValues("edit_neighbour"));

                if (editNeighbourCmd.hasOption("add")) {
                    // Handle the "add" subcommand
                    String countryID = editNeighbourCmd.getOptionValue("add");
                    String neighborCountryID = editNeighbourCmd.getOptionValue("add");
                    // Process the "add" operation with countryID and neighborCountryID
                    // Add your code here
                }

                if (editNeighbourCmd.hasOption("remove")) {
                    // Handle the "remove" subcommand
                    String countryID = editNeighbourCmd.getOptionValue("remove");
                    String neighborCountryID = editNeighbourCmd.getOptionValue("remove");
                    // Process the "remove" operation with countryID and neighborCountryID
                    // Add your code here
                }
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

    
    /**
     * Validates the provided input string.
     * <p>
     * This method checks if the input string is null or empty. If the input is invalid,
     * it throws an IllegalArgumentException.
     * </p>
     *
     * @param p_input The input string to be validated.
     * @throws IllegalArgumentException if the input string is null or empty.
     * @author Yusuke Ishii
     */
    private void validateInput(String p_input) throws IllegalArgumentException {
        if (p_input == null || p_input.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input provided.");
        }
    }


}
