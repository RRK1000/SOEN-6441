import controller.CommandParser;
import controller.GameManager;
import global.Constants;

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
        System.out.println(Constants.HELP_MESSAGE);


        String l_inputCommand = l_scanner.nextLine();
        if(!CommandParser.isvalidInput(l_inputCommand)){
            System.out.println(Constants.CMD_ERROR);
            System.out.println(Constants.HELP_MESSAGE);
        }else{
            while (!Objects.equals(l_inputCommand, "exit")) {
                CommandParser.inputParser(d_gameManager, l_inputCommand);
                l_inputCommand = l_scanner.nextLine();
            }
        }
    }
}


