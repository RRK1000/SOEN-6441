package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import models.Continent;
import models.Country;
import models.Map;
import models.Player;

/**
 * Represents the Game Manager
 * Manages game state, and the player actions involved during the game play
 *
 * @author Rishi Ravikumar
 */
public class GameManager {
    private List<Player> d_playerList;
    private Player d_currentPlayerTurn;
    private GamePhase d_gamePhase;

    private static Player d_player;

    private Map d_map;

    public GameManager(Map p_map) {
        this.d_map = p_map;
    }

    public GameManager() { }

    public GameManager(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Used in the Game_Startup game phase to assign countries to the players in the game
     *
     * @author Nimisha Jadav
     */
    public static void assignCountries() {
        // implementation here
        // must iterate through d_playerList and assign countries to each player according to the warzone rules
        // Must update the d_countryList data member of each Player

        Random l_random = new Random();
        GameManager l_gamemanager = new GameManager();
        int l_index = 0;
        for(Player l_player: l_gamemanager.d_playerList){
            l_player.addCountry(d_player.getD_countryList().get(l_index));
            l_index++;
        }
        for(int i=l_index; i<d_player.getD_countryList().size(); i++){
            int l_indexOfPlayer = l_random.nextInt(l_gamemanager.d_playerList.size());
            l_gamemanager.d_playerList.get(l_indexOfPlayer).addCountry(d_player.getD_countryList().get(i));

        }
    }

    /**
     * Assigns to each {@link models.Player} the number of reinforcement armies according to the Warzone rules
     *
     * @author Rishi Ravikumar
     */

    /**
     * check if enough armies are
     * left to be assigned for the player
     * @author Abhigyan
     */

    public boolean check_armies(){
        for(Player l_player : d_playerList){
            if(l_player.getD_numArmies()!=0){
                return false;
            }
        }
        return true;
    }
    public void assignReinforcements(Map p_map) {
        // implementation here
        // must iterate through d_playerList and assign d_numArmies value to each player according to the warzone rules
        //      Must use the d_continentList from the Player object to assign the d_numArmies.
        //      Must use the d_continentValue from each continent conquered by the player
        for (Player l_player : d_playerList) {
            int l_ownedCountries = l_player.getD_countryList().size();
            int l_ArmyCount, l_flag;
            String l_playerName = l_player.getD_playerName();

            l_ArmyCount = Math.max((l_ownedCountries / 5), 5);
            List<Continent> l_continentList = (List<Continent>) p_map.getD_continentMapGraph().vertexSet();
            for (Continent l_continent : l_continentList) {
                l_flag = 0;
                for (Country l_country : l_continent.getD_countryList()) {
                    if (!l_country.getD_Player().getD_PlayerName().equals(l_playerName)) {
                        l_flag = 1;
                        break;
                    }
                }
                if (l_flag == 0) {
                    l_ArmyCount += l_continent.getD_continentValue();
                }
            }

            l_player.setD_numArmies(l_player.getD_numArmies() + l_ArmyCount);
            l_player.d_armiesForNextCountry = l_player.getD_numArmies();
        }

    }

    /**
     * Adds a new {@link models.Player} to the game
     *
     * @param p_playerName name of the player to be added
     * @author Nimisha Jadav
     */
    public void addPlayer(String p_playerName) {
        // implementation here
        // must add a new player object to GameManager->d_playerList
        if(d_playerList.size()<6){
            if(d_playerList.contains(new Player(p_playerName))){
                System.out.println("Player already exists");
            }else {
                d_playerList.add(new Player(p_playerName));
                System.out.println("Successfully added "+p_playerName+" to the game!");
            }
        } else {
            System.out.println("You have reached the limit to add players");
        }

    }

    /**
     * Removes a {@link models.Player} from the game
     *
     * @param p_playerName name of the player to be removed
     * @author Nimisha Jadav
     */
    public void removePlayer(String p_playerName) {
        // implementation here
        // must remove a player object from GameManager->d_playerList
        Player d_player;
        if(d_playerList.size()>2){
            if(d_playerList.contains(new Player(p_playerName))){
                Iterator l_itr = d_playerList.iterator();
                while(l_itr.hasNext()) {
                    String l_name = (String) l_itr.next();
                    if (l_name == p_playerName) {
                        l_itr.remove();
                    }
                }
                System.out.println("Player removed from the game");
            }else {
                System.out.println("Player doesnot existed");
            }
        } else {
            System.out.println("You have reached the limit to remove players");
        }

    }

    /**
     * Adds an order to the current player’s list of orders
     *
     * @author Rishi Ravikumar
     */
    public void issueOrder(int p_countryID, int num) {
        // implementation here
        // must call the d_currentPlayerTurn.issue_order()
    }

    /**
     * Executes all the orders from all the players for the current turn, updating the game state
     *
     * @author Rishi Ravikumar
     */
    public void executeOrder(String p_playerName) {
        // implementation here
        // iterates through each player and does the following for each order:
        //      fetch the next order object using d_currentPlayerTurn.next_order() and then run Order->execute()
    }

    /**
     * Displays the {@link models.Map}, and the current game state
     *
     * @author Rishi Ravikumar
     */
    public void showMap() {
        // implementation here
        // show all countries and continents,
        // armies on each country, ownership, and connectivity in a way that enables efficient game play

    }

    /**
     * Gets the list of players in the current active game
     */
    public List<Player> getD_playerList() {
        return d_playerList;
    }

    /**
     * Sets the list of players in the current active game
     */
    public void setD_playerList(List<Player> d_playerList) {
        this.d_playerList = d_playerList;
    }

    /**
     * Gets the game's current turn it is to perform actions
     */
    public Player getD_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * Sets the game's current turn
     */
    public void setD_currentPlayerTurn(Player d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
    }

    /**
     * Gets the game's current phase.

     * Possible values defined by {@link GamePhase}
     */
    public GamePhase getD_gamePhase() {
        return d_gamePhase;
    }

    /**
     * Gets the game's current phase.

     * Possible values defined by {@link GamePhase}
     */
    public void setD_gamePhase(GamePhase d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }

    public enum GamePhase {
        Game_Startup,
        AssignReinforcements,
        IssueOrder,
        ExecuteOrder
    }
}