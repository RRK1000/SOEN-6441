package game;

import controller.CommandParser;
import controller.GameManager;
import global.Constants;

import java.util.Objects;
import java.util.Scanner;

/**
 * Represents the Game Engine.
 * Represents the main game loop.
 *
 * @author Rishi Ravikumar
 * @author Nimisha Jadav
 */
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
        System.out.println("First, load a map / build a new map");
        System.out.println(Constants.HELP_MESSAGE);

        String l_inputCommand;
        //Takes input from user, until the exit command
        do {
            System.out.print("\n> ");
            l_inputCommand = l_scanner.nextLine();
            CommandParser.inputParser(d_gameManager, l_inputCommand);
        } while (!Objects.equals(l_inputCommand, "exit"));
    }
}
