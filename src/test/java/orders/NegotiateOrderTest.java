package orders;

import controller.GameManager;
import global.Cards;
import models.Order;
import models.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.Phase;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NegotiateOrderTest {
    static GameManager d_gameManager;
    static Phase d_gamePhase;

    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
        d_gamePhase = new InitMapPhase();

        d_gamePhase.loadMap("validMap2.txt", d_gameManager);
        String player1Name = "Player1";
        String player2Name = "Player2";
        d_gameManager.addPlayer(player1Name);
        d_gameManager.addPlayer(player2Name);
        d_gameManager.getD_gamePhase().assignCountries(d_gameManager);
    }

    @AfterEach
    void tearDown() {
        d_gameManager = null;
    }

    @Test
    void execute() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.DIPLOMACY_CARD)));

        Player l_p2 = d_gameManager.getD_playerList().get(1);

        Order l_negotiate = new NegotiateOrder(l_p1, l_p2);
        l_negotiate.execute();
        assertTrue(l_p1.isInNegotiationWith(l_p2));
    }

    @Test
    void isValid1() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.DIPLOMACY_CARD)));

        Player l_p2 = d_gameManager.getD_playerList().get(1);

        Order l_negotiate = new NegotiateOrder(l_p1, l_p2);
        assertTrue(l_negotiate.isValid());
    }
    @Test
    void isValid2() {
        Player l_p1 = d_gameManager.getD_playerList().get(0);
        l_p1.setD_playerCardList(new ArrayList<>(Arrays.asList(Cards.DIPLOMACY_CARD)));

        Player l_p2 = null;

        Order l_negotiate = new NegotiateOrder(l_p1, l_p2);
        assertFalse(l_negotiate.isValid());
    }
}
