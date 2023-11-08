package orders;

import controller.GameManager;
import global.Cards;
import models.Country;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Blockade order command
 * @author Rishi Ravikumar
 */
class BlockadeOrderTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before all tests, initialises game manager, map etc.
     */
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("validMap2.txt", d_gameManager);
        String l_player1Name = "Player1";
        String l_player2Name = "Player2";
        d_gameManager.addPlayer(l_player1Name);
        d_gameManager.addPlayer(l_player2Name);
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    /**
     * Sets game manager to null after tests are done
     */
    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    /**
     * Test to validate execution of blockade order
     */
    @Test
    void execute() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.BLOCKADE_CARD)));

        Country l_country = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_country, 5);
        l_deployOrder1.execute();

        Order l_blockade = new BlockadeOrder(l_p1, l_country);
        l_blockade.execute();
        assertEquals(15, l_country.getD_numArmies());
    }

    /**
     * Test to check the validation of correct blockade order
     */
    @Test
    public void isValid() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.BLOCKADE_CARD)));

        Country l_country = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_country, 5);
        l_deployOrder1.execute();

        Order l_blockade = new BlockadeOrder(l_p1, l_country);
        assertTrue(l_blockade.isValid());
    }
}