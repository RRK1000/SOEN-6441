package controller;

import java.io.FileReader;
import java.util.*;
import java.io.*;
import util.MapUtil;
import models.Map;

public class GameStartup {

    private int d_gameplayer;

    public void getGamePlayer(int p_gameplayer){
        this.d_gameplayer = p_gameplayer;
    }

    public int setGamePLayer(){
        return d_gameplayer;
    }

    public void assignCountries(){

    }

    public int menuGame(){
        int d_choice;
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Continue Existing Game");
        System.out.println("2. Start a New Game");
        System.out.println("3. Exit");
        System.out.println("Please enter your choice : ");
        d_choice = sc.nextInt();
        return d_choice;
    }

    public void loadGame(){

        Scanner sc = new Scanner(System.in);
        Map d_map;
        String d_mapName;
        System.out.println("Enter the map name to load the game");
        d_mapName = sc.next();
        //write logic to check whether the map exist or not, if not create a new map
        if(ifMapExists(d_mapName)){
            //continue the saved game
        }
        else{
            //start a new game
        }
    }

    public void newGame(){
        //write logic to create new map and then validate the map
    }

    public boolean ifMapExists(String d_mapName){
        File f_file = new File(d_mapName);
        boolean d_exists = f_file.exists();
        return d_exists;
    }

}
