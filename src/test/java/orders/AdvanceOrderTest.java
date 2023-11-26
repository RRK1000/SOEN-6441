package orders;

import controller.GameManager;
import models.Country;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for advance order
 * @author Rishi Ravikumar
 */
class AdvanceOrderTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before the test, initialises game manager, map etc.
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
     * Tests an execute command where the attacking army conquers the country
     */
    @Test
    void executeTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 6);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 1);
        l_deployOrder2.execute();

        Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryFrom, l_countryTo, 5);
        l_advanceOrder.execute();
        assertSame(l_countryTo.getD_owner(), l_p1);
        assertFalse(l_p1.getD_playerCardList().isEmpty());
    }

    /**
     * Tests an execute command where the attacking army cannot conquer
     */
    @Test
    void executeTest2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 10);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 10);
        l_deployOrder2.execute();

        Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryFrom, l_countryTo, 1);
        l_advanceOrder.execute();
        assertSame(l_countryTo.getD_owner(), l_p2);
    }

    /**
     * Tests a valid advance order command
     */
    @Test
    void isValidTest1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(4);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 1);
        l_deployOrder2.execute();

        Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryFrom, l_countryTo, 1);
        assertTrue(l_advanceOrder.isValid());
    }

    /**
     * Tests an invalid advance order command, where the number of armies is greater than the deployed count
     */
    @Test
    void isValidTest2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 1);
        l_deployOrder2.execute();

        Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryFrom, l_countryTo, 9);
        assertFalse(l_advanceOrder.isValid());
    }

    /**
     * Tests an invalid advance order command, where the source country is not owned by the player
     */
    @Test
    void isValidTest3() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);
        Order l_deployOrder2 = new DeployOrder(l_p2, l_countryTo, 1);
        l_deployOrder2.execute();

        Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryTo, l_countryFrom, 1);
        assertFalse(l_advanceOrder.isValid());
    }

    /**
     * Tests a valid advance order command, where the target country is owned by the player
     */
    @Test
    void isValidTest4() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        Order l_deployOrder1 = new DeployOrder(l_p1, l_countryFrom, 3);
        l_deployOrder1.execute();

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        List<Integer> l_neighborList = l_countryFrom.getD_neighbourCountryIDList();
        for (int l_i: l_neighborList) {
            Country l_country = d_gameManager.getD_map().getD_countryByID(l_i);
            if(l_country.getD_owner().equals(l_p2)){
                Order l_deployOrder2 = new DeployOrder(l_p2, l_country, 1);
                l_deployOrder2.execute();
                Order l_advanceOrder = new AdvanceOrder(l_p1, l_countryFrom, l_country, 1);
                assertTrue(l_advanceOrder.isValid());
            }
        }



    }
}