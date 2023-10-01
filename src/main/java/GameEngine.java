import controller.CommandParser;
import controller.GameManager;

import java.util.Objects;
import java.util.Scanner;

public class GameEngine {


    /**
     * Main method to drive the game logic.
     *
     * @param p_args Command line arguments.
     */
    public static void main(String[] p_args) {
        GameManager d_gameManager = new GameManager();
        Scanner l_scanner = new Scanner(System.in);

        System.out.println("WELCOME TO WARZONE");
        System.out.println("Load a map / Build a new map using the following commands");
        CommandParser.displayInstructions(d_gameManager);


        String l_inputCommand = l_scanner.nextLine();
        CommandParser.validateInput(l_inputCommand);
        while (!Objects.equals(l_inputCommand, "exit")) {
            CommandParser.inputParser(d_gameManager, l_inputCommand);
            l_inputCommand = l_scanner.nextLine();
        }
    }
//        System.out.println("--- MENU ---");
//        System.out.println("1. Start an existing game");
//        System.out.println("2. Start an new game");
//        System.out.println("3. Exit");
//        int l_userinput = 0;
//        System.out.println("Choose an option from the menu");
//        l_userinput = l_sc.nextInt();
//
//        switch (l_userinput) {
//            case 1:
//
//                System.out.println("Starting an existing game...");
//                System.out.println("Command : ");
//                l_usercommand = l_sc.nextLine();
//                CommandParser.inputParser(d_gameManager, l_usercommand);
//                break;
//
//            case 2:
//                System.out.println("Starting a new game");
//                System.out.println("Command : ");
//                l_usercommand = l_sc.nextLine();
//                CommandParser.inputParser(d_gameManager, l_usercommand);
//                break;
//
//            default:
//                System.out.println("Please enter a correct option.");
//        }
//    }


}


