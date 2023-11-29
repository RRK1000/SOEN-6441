package phases;

import controller.GameManager;
import models.Country;
import models.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.HumanStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests to check the functionality of Execute order
 * @author Rishi Ravikumar
 */
class ExecuteOrderPhaseTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    /**
     * Setup before all tests, initalises game manager, loads map etc.
     */
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("europe.map", d_gameManager);
        String player1Name = "Player1";
        String player2Name = "Player2";
        d_gameManager.addPlayer(player1Name, new HumanStrategy());
        d_gameManager.addPlayer(player2Name, new HumanStrategy());
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    /**
     * Sets the game manager to null after tests are executed
     */
    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    /**
     * Tests the execution of a deploy order
     */
    @Test
    void executeOrder1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_country = l_p1.getD_countryList().get(0);
        d_gameManager.getD_gamePhase().deploy(d_gameManager, l_p1, l_country, 1);

        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        assertEquals(l_country.getD_numArmies(), 1);
    }

    /**
     * Tests the execution of a advance order
     */
    @Test
    void executeOrder2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        d_gameManager.getD_gamePhase().deploy(d_gameManager, l_p1, l_countryFrom, 3);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);

        d_gameManager.getD_gamePhase().advance(d_gameManager, l_p1, l_countryFrom, l_countryTo, 2);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        assertEquals(l_countryTo.getD_owner(), l_p1);
    }

    /**
     * Tests the execution of a advance order, followed by a deploy on the conquered country
     */
    @Test
    void executeOrder3() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        Country l_countryFrom = l_p1.getD_countryList().get(0);
        d_gameManager.getD_gamePhase().deploy(d_gameManager, l_p1, l_countryFrom, 3);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());

        Player l_p2 = d_gameManager.getD_playerList().get(1);
        Country l_countryTo = l_p2.getD_countryList().get(0);

        d_gameManager.getD_gamePhase().advance(d_gameManager, l_p1, l_countryFrom, l_countryTo, 2);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());


        d_gameManager.getD_gamePhase().deploy(d_gameManager, l_p1, l_countryTo, 3);
        d_gameManager.setD_gamePhase(d_gameManager.getD_gamePhase().nextPhase());
        d_gameManager.getD_gamePhase().executeOrder(d_gameManager);
        assertEquals(l_countryTo.getD_numArmies(), 2);
    }

    /**
     * Test case to verify that the nextPhase() method returns the expected next phase.
     */
    @Test
    public void testNextPhase(){
        ExecuteOrderPhase l_executeOrderPhase = ExecuteOrderPhase.getInstance();
        Phase l_nextPhase = l_executeOrderPhase.nextPhase();
        assertEquals(IssueOrderPhase.class,l_nextPhase.getClass());
    }
}