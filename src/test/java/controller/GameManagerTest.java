package controller;

import models.Map;
import models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.MapUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains the test methods for GameManager.java.
 * The tested function is assignReinforcements.
 * @author Abhigyan
 */

class GameManagerTest {

    private GameManager d_gameManager;
    private Player d_player1;
    private Player d_player2;
    private Map d_map = new Map();

    @BeforeEach
    void setUp() {
        // Create test players
        String player1Name = "Player1";
        String player2Name = "Player2";

        Map d_map = MapUtil.loadMap("src/test/resources/europe.map");

        d_gameManager = new GameManager(d_map);
        d_gameManager.addPlayer(player1Name);
        d_gameManager.addPlayer(player2Name);
    }

    /**
     * This test tests the assignReinforcements function. It checks weather each player is getting reinforcements according to war-zone rules.
     * Expected Results: return true if it matches the expected value.
     */

    @Test
    void assignReinforcements() {
        // Calculate the expected number of reinforcements for each player
        int expectedPlayer1Armies = Math.max( (d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
        int expectedPlayer2Armies = Math.max( (d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
//        System.out.println(expectedPlayer1Armies);
        d_gameManager.assignCountries(); // calls assignReinforcements internally

        // Check if the number of armies for each player matches the expected value
        assertEquals(expectedPlayer1Armies, d_gameManager.getD_playerList().get(0).getD_numArmies());
        assertEquals(expectedPlayer2Armies, d_gameManager.getD_playerList().get(1).getD_numArmies());
    }
}