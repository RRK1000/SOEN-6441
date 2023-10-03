package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Continent;
import models.Country;
import models.Map;
import models.Player;
import util.MapUtil;

/**
 * This class contains the test methods for GameManager.java.
 * The tested function is assignReinforcements.
 * @author Abhigyan
 */

class GameManagerTest {

    private GameManager gameManager;
    private Player player1;
    private Player player2;
    private Map map = new Map();

    @BeforeEach
    void setUp() {
        // Create test players
        String player1Name = "Player1";
        String player2Name = "Player2";
      //  Player player1 = new Player("Player1");
       // Player player2 = new Player("Player2");
        player1 = new Player(player1Name);  
        player2 = new Player(player2Name);  

        
        // Create test countries and continents
        int country1ID = 1;
        int country2ID = 2;
        int continent1ID = 1;
        Country country1 = new Country(1, new ArrayList<>(), 3);
        Country country2 = new Country(2, new ArrayList<>(), 3);
        Continent continent1 = new Continent(1, 3);

        // Add countries and continents to players
        List<Continent> continentList = new ArrayList<>();
        continentList.add(continent1);
        player1.addCountry(country1);
        player1.setD_continentList(continentList);

        player2.addCountry(country2);

        // Create a test map and game manager
        // Use MapUtil to add countries to the map
        List<Country> countriesToAdd = new ArrayList<>();
        countriesToAdd.add(country1);
        countriesToAdd.add(country2);

       // GameManager gameManager = new GameManager(map);
        gameManager = new GameManager(map);  // Use class field directly


        MapUtil.addCountry(map, country1ID, continent1ID);
        MapUtil.addCountry(map, country2ID, continent1ID);


        // Add players to the game manager
        gameManager.addPlayer(player1Name);
        gameManager.addPlayer(player2Name);

        // Set the game phase to AssignReinforcements for testing
        gameManager.setD_gamePhase(GamePhase.AssignReinforcements);

        // Assign test data to class fields for use in test methods
        this.gameManager = gameManager;
        this.player1 = player1;
        this.player2 = player2;
        this.map = map;
    }

    /**
     * This test tests the assignReinforcements function. It checks weather each player is getting reinforcements according to war-zone rules.
     * Expected Results: return true if it matches the expected value.
     */

    @Test
    void assignReinforcements() {
        // Calculate the expected number of reinforcements for each player
        int expectedPlayer1Armies = (int) Math.min((double) (map.getD_countryMapGraph().vertexSet().size() / 3), 3) + 3; // 3 for continent value
        int expectedPlayer2Armies = (int) Math.min((double) (map.getD_countryMapGraph().vertexSet().size() / 3), 3); // No continents
        System.out.println(expectedPlayer1Armies);

        // Call the assignReinforcements method

        gameManager.assignReinforcements();

        // Check if the number of armies for each player matches the expected value
        assertEquals(expectedPlayer1Armies, player1.getD_numArmies());
        assertEquals(expectedPlayer2Armies, player2.getD_numArmies());
    }
}