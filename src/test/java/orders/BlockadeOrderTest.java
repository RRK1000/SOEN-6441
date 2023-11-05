package orders;

import controller.GameManager;
import global.Cards;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.Country;
import models.Player;
import models.Map;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockadeOrderTest {
    Map d_GameMap;
    List<Country> d_CountryList1 = new ArrayList<Country>();
    List<Country> d_CountryList2 = new ArrayList<Country>();
    GameManager gameManager = new GameManager();
    private List<String> d_playerCardList;

    @BeforeEach
    void setUp() {
        Map gameMap = new Map();
        d_playerCardList = new ArrayList<String>();
        gameManager.setD_map(gameMap);
        gameManager.addPlayer("Player1");
        gameManager.addPlayer("Player2");
        gameManager.assignCountries();
        Player player1 = gameManager.getD_playerList().get(0);
        Player player2 = gameManager.getD_playerList().get(1);
        d_CountryList1 = player1.getD_countryList();
        d_CountryList2 = player2.getD_countryList();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {
        // Create a new Country
        Country country = new Country();
        country.setD_countryName("India");

        // Create a new Player
        Player player = new Player("TestPlayer");

        // Add the BLOCKADE_CARD to the player's card list
        player.getD_playerCardList().add(Cards.BLOCKADE_CARD);

        // Set the owner of the country to the player
        country.setD_owner(player);

        // Add the country to the player's list of owned countries
        player.addCountry(country);

        // Set the player's initial number of armies
        player.setD_numArmies(3);

        // Create a BlockadeOrder
        BlockadeOrder blockadeOrder = new BlockadeOrder(country, player);

        // Ensuring the initial state of the Player
        assertEquals(3, player.getD_numArmies()); // The player has 3 armies before the blockade

        // Redirect the standard output to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Executing the BlockadeOrder
        blockadeOrder.execute();

        // Restore the original standard output
        System.setOut(originalOut);

        // Converting the captured output to a string
        String printedOutput = outputStream.toString();

        // Verifying the updated state after executing the order
        assertEquals(9, country.getD_numArmies()); // Armies are tripled, but the country is neutralized
        assertFalse(player.getD_countryList().contains(country)); // Country should be removed from the player's list
        assertFalse(player.getD_playerCardList().contains(Cards.BLOCKADE_CARD)); // BLOCKADE_CARD should be removed from the player's card list

        // Verifying the printed output to check the number of armies
        assertTrue(printedOutput.contains("Blockade on India by TestPlayer"));

    }

    @Test
    public void isValid() {
        // Test scenario with a valid blockade order
        Country country1 = new Country();
        country1.setD_countryName("India");
        Player player1 = new Player("TestPlayer");
        player1.setD_playerCardList(Arrays.asList(Cards.BLOCKADE_CARD));
        country1.setD_owner(player1);
        BlockadeOrder validBlockadeOrder = new BlockadeOrder(country1, player1);

        // Test scenario with an invalid player
        Country country2 = new Country();
        country2.setD_countryName("India");
        Player player2 = null; // Player is null
        BlockadeOrder invalidPlayerBlockadeOrder = new BlockadeOrder(country2, player2);

        // Test scenario with invalid ownership
        Country country3 = new Country();
        country3.setD_countryName("India");
        Player player3 = new Player("TestPlayer");
        country3.setD_owner(new Player("OtherPlayer")); // Country is owned by a different player
        BlockadeOrder invalidOwnershipBlockadeOrder = new BlockadeOrder(country3, player3);

        // Test scenario with invalid cards
        Country country4 = new Country();
        country4.setD_countryName("India");
        Player player4 = new Player("TestPlayer");
        player4.setD_playerCardList(Arrays.asList(Cards.BOMB_CARD, Cards.DIPLOMACY_CARD));
        country4.setD_owner(player4);
        BlockadeOrder invalidCardBlockadeOrder = new BlockadeOrder(country4, player4);

        assertTrue(validBlockadeOrder.isValid());
        assertFalse(invalidPlayerBlockadeOrder.isValid());
        assertFalse(invalidOwnershipBlockadeOrder.isValid());
        assertFalse(invalidCardBlockadeOrder.isValid());
    }
}