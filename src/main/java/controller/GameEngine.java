package controller;
import java.util.ArrayList;

import models.Player;

public class GameEngine {

    public static void main(String[] p_args){
        System.out.println("WELCOME TO WARZONE");

        System.out.println("---GAME BEGINS---");

        gameMenu();
    }
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
    }

}
