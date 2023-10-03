package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Country;
import models.Order;
import models.Player;

/**
 * Tests the deploy function.
 * @author Yusuke Ishii
 */
public class GameEngineTest {

    // Data members
    private Player d_player;
    private Country d_country;
    private Order d_order;

    /**
     * Setup method to initialize the required objects before each test
     */
    @BeforeEach
    public void setup() {
        d_player = new Player("John", 5, new ArrayList<>(), new ArrayList<>(), null);
        d_country = new Country();
        d_order = new Order(d_country, 3); // Deploying 3 armies
        d_player.setD_currentOrder(d_order);
    }

    /**
     * Test to ensure that a player cannot deploy more armies than available in the reinforcement pool.
     */
    @Test
    public void testCannotDeployMoreArmiesThanInReinforcementPool() {
        assertEquals(5, d_player.getD_numArmies());

        d_player.issueOrder();

        assertEquals(2, d_player.getD_numArmies());

        Order l_anotherOrder = new Order(d_country, 4); // Trying to deploy 4 armies
        d_player.setD_currentOrder(l_anotherOrder);

        Exception l_exception = assertThrows(IllegalArgumentException.class, () -> {
            d_player.issueOrder();
        });

        String l_expectedMessage = "Cannot deploy more armies than available in reinforcement pool.";
        String l_actualMessage = l_exception.getMessage();

        assertTrue(l_actualMessage.contains(l_expectedMessage));
    }
}