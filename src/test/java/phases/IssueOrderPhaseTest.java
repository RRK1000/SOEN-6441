package phases;

import controller.GameManager;
import models.Country;
import models.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IssueOrderPhaseTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String player1Name = "Player1";
        String player2Name = "Player2";
        d_gameManager.addPlayer(player1Name);
        d_gameManager.addPlayer(player2Name);
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    @Test
    void deployTest() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        d_gameManager.getD_gamePhase().deploy(l_p1, l_country, 1);
        assertEquals(l_p1.getD_numArmies(), 2); // 3 -> 2
    }
}