package controller;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TournamentGameManagerTest {

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