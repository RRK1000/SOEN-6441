package controller;
import java.util.ArrayList;
import models.Player;

/**
 * This is the main game loop.
 */
public class GameEngine{

    GameManager d_gameManager = new GameManager();

    public static void main(String[] p_args){

    }
    public static ArrayList<Player> d_playerList = new ArrayList<Player>();


    /* public static void gameMenu(){
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
    } */


    /**
     * This method is used to read the command given by the player and validates if the syntax
     * of the command is correct and then takes the action accordingly
     * @param d_input - Command entered by the player
     * @author - Abhigyan
     * @author - Nimisha
     * @author - Yuki
     * @version - 1.0.0
     */
    public void inputParser(String d_input){
        //Implementation here

    }

    /**
     * This method is used to validate the command entered by player. Validates the command based
     * on the current game phase.
     * @param d_input - Command entered by the player
     * @version - 1.0.0
     */
    public void validateInput(String d_input){
        //Implementation here

    }

}
