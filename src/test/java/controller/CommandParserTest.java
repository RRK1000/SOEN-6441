package controller;

import models.Country;
import models.Map;
import models.Order;
import models.Player;
import orders.DeployOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.MapUtil;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
	GameManager d_gameManager;
	private Player d_player;
	private Country d_country;
	private Order d_order;
	private Map d_map = new Map();

	@BeforeEach
	void setUp() {
		d_gameManager = new GameManager();
		d_player = new Player("John", 5, new ArrayList<>(), new ArrayList<>(), null);
		d_country = new Country();
		d_order = new DeployOrder(d_player, d_country, 3);
		d_player.setD_currentOrder(d_order);

		String player1Name = "Player1";
		String player2Name = "Player2";

		d_map = MapUtil.loadMap("europe.map");

		d_gameManager = new GameManager();
		d_gameManager.setD_map(d_map);
		d_gameManager.addPlayer(player1Name);
		d_gameManager.addPlayer(player2Name);
	}

	/**
	 * This test tests the assignReinforcements function. It checks weather each
	 * player is getting reinforcements according to war-zone rules. Expected
	 * Results: return true if it matches the expected value.
	 */
	@Test
	void assignReinforcements() {
		int expectedPlayer1Armies = Math.max((d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
		int expectedPlayer2Armies = Math.max((d_map.getD_countryMapGraph().vertexSet().size() / 3), 3);
		d_gameManager.assignCountries(); // calls assignReinforcements internally

		assertEquals(expectedPlayer1Armies, d_gameManager.getD_playerList().get(0).getD_numArmies());
		assertEquals(expectedPlayer2Armies, d_gameManager.getD_playerList().get(1).getD_numArmies());
	}


	/**
	 * Test switch case "loadmap". Checks whether map object is set, and the country
	 * graph is not empty
	 */
	@Test
	void inputParser1() {
		String l_input = "loadmap validMap2.txt";
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
		String l_input = "editmap validMap2.txt";
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
		String l_input = "editmap InvalidMap1.txt";
		CommandParser.inputParser(d_gameManager, l_input);
		assertNotNull(d_gameManager.getD_map());
		assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
	}
}