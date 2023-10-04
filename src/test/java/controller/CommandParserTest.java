package controller;

import models.Country;
import models.Order;
import models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
	GameManager d_gameManager;
	private Player d_player;
	private Country d_country;
	private Order d_order;

	@BeforeEach
	void setUp() {
		d_gameManager = new GameManager();
		d_player = new Player("John", 5, new ArrayList<>(), new ArrayList<>(), null);
		d_country = new Country();
		d_order = new Order(d_country, 3);
		d_player.setD_currentOrder(d_order);
	}


	/**
	 * Tests the scenario where a player attempts to deploy more armies than
	 * available in their reinforcement pool.
	 * The test first ensures that the player starts with a specific number of
	 * armies. After issuing an order to deploy a certain number of armies, the test
	 * checks that the player's remaining armies are as expected
	 *
	 * The test then tries to issue another order to deploy more armies than the
	 * player has available. This should result in an IllegalArgumentException. The
	 * test checks that the exception message matches the expected message
	 *
	 */

	@Test
	void testCannotDeployMoreArmiesThanInReinforcementPool() {
		assertEquals(5, d_player.getD_numArmies());

		d_player.issueOrder();

		assertEquals(2, d_player.getD_numArmies());

		Order l_anotherOrder = new Order(d_country, 4);
		d_player.setD_currentOrder(l_anotherOrder);

		Exception l_exception = assertThrows(IllegalArgumentException.class, () -> {
			d_player.issueOrder();
		});

		String l_expectedMessage = "Cannot deploy more armies than available in reinforcement pool.";
		String l_actualMessage = l_exception.getMessage();

		assertEquals(l_actualMessage, l_expectedMessage);
	}

	/**
	 * Test switch case "loadmap". Checks whether map object is set, and the country
	 * graph is not empty
	 */
	@Test
	void inputParser1() {
		String l_input = "loadmap src/test/resources/validMap2.txt";
		CommandParser.inputParser(d_gameManager, l_input);
		assertNotNull(d_gameManager.getD_map());
		assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
	}

	/**
	 * Test switch case "editmap" on a valid map file. Checks whether map object is
	 * set, and the country graph is not empty
	 */
	@Test
	void inputParse2() {
		String l_input = "editmap src/test/resources/validMap2.txt";
		CommandParser.inputParser(d_gameManager, l_input);
		assertNotNull(d_gameManager.getD_map());
		assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
	}

	/**
	 * Test switch case "editmap" on an invalid file. Checks whether map object is
	 * set, and the country graph is not empty
	 */
	@Test
	void inputParse3() {
		String l_input = "editmap src/test/resources/InvalidMap1.txt";
		CommandParser.inputParser(d_gameManager, l_input);
		assertNotNull(d_gameManager.getD_map());
		assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
	}
}