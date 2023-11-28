package controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
/**
 * The {@code TournamentGameManagerTest} class contains JUnit tests for the
 * {@link controller.TournamentGameManager} class.
 * It focuses on testing the functionality of running a tournament with different maps
 * and player strategies.
 *
 * @author Abhigyan Singh
 */

class TournamentGameManagerTest {

    /**
     * Tests the functionality of running a tournament with specified maps,
     * player strategies, number of games, and maximum turns per game.
     */
    @Test
    void runTournament() {
        List<String> l_mapList = new ArrayList<>();
        l_mapList.add("europe.map");
        l_mapList.add("validMap2.txt");

        List<String> l_strategyList = new ArrayList<>();
        l_strategyList.add("Aggressive");
        l_strategyList.add("Benevolent");
        l_strategyList.add("Random");
        l_strategyList.add("Cheater");

        // Set the number of games and maximum turns per game
        int l_numGames = 4;
        int l_maxTurns = 300;

        TournamentGameManager l_tournamentManager = new TournamentGameManager();
        l_tournamentManager.setD_mapList(l_mapList);
        l_tournamentManager.setD_strategyList(l_strategyList);
        l_tournamentManager.setD_numGames(l_numGames);
        l_tournamentManager.setD_maxTurns(l_maxTurns);
        l_tournamentManager.runTournament();

    }
}