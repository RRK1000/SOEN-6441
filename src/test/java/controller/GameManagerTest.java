package controller;

import models.Map;
import models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.StartupPhase;
import util.MapUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for game manager
 * @author Abhigyan Singh
 * @author Rishi Ravikumar
 */
class GameManagerTest {
    private GameManager d_gameManager;
    private Player d_player1;
    private Player d_player2;
    private Map d_map = new Map();

    /**
     * Setup before tests, initialises game manager, map etc.
     */
    @BeforeEach
    void setUp() {
        // Create test players
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";

        Map d_map = MapUtil.loadMap("europe.map");

        d_gameManager = new GameManager();
        d_gameManager.setD_gamePhase(new StartupPhase());
        d_gameManager.setD_map(d_map);
        d_gameManager.addPlayer(l_player1Name);
        d_gameManager.addPlayer(l_player2Name);

    }

    /**
     * This test tests the assignReinforcements function. It checks weather each player is getting reinforcements according to war-zone rules.
     * Expected Results: return true if it matches the expected value.
     */

    @Test
    void assignReinforcements() {
        // Calculate the expected number of reinforcements for each player
        int l_expectedPlayer1Armies = Math.max( (d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
        int l_expectedPlayer2Armies = Math.max( (d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
//        System.out.println(expectedPlayer1Armies);
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager); // calls assignReinforcements internally

        // Check if the number of armies for each player matches the expected value
        assertEquals(l_expectedPlayer1Armies, d_gameManager.getD_playerList().get(0).getD_numArmies());
        assertEquals(l_expectedPlayer2Armies, d_gameManager.getD_playerList().get(1).getD_numArmies());
    }

}