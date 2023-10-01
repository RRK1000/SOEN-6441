import controller.CommandParser;
import controller.GameManager;
import models.Map;

import java.util.Scanner;

public class GameEngine {
    private static GameManager d_gameManager;


    /**
     * Main method to drive the game logic.
     *
     * @param p_args Command line arguments.
     */
    public static void main(String[] p_args) {
        Map l_map = new Map();
        d_gameManager = new GameManager(l_map);
        Scanner l_sc = new Scanner(System.in);
        String l_mapname;
        String l_usercommand = "";
        System.out.println("--- WELCOME TO WARZONE ---");
        System.out.println("--- STARTING THE GAME ---");
        System.out.println("--- MENU ---");
        System.out.println("1. Start an existing game");
        System.out.println("2. Start an new game");
        System.out.println("3. Exit");
        int l_userinput = 0;
        System.out.println("Choose an option from the menu");
        l_userinput = l_sc.nextInt();

        switch (l_userinput) {
            case 1:

                System.out.println("Starting an existing game...");
                System.out.println("Command : ");
                l_usercommand = l_sc.nextLine();
                CommandParser.inputParser(d_gameManager, l_usercommand);
                break;

            case 2:
                System.out.println("Starting a new game");
                System.out.println("Command : ");
                l_usercommand = l_sc.nextLine();
                CommandParser.inputParser(d_gameManager, l_usercommand);
                break;

            default:
                System.out.println("Please enter a correct option.");
        }
    }



}


