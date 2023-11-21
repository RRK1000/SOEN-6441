package controller;

import models.Map;
import org.junit.jupiter.api.Test;
import strategy.*;
import util.MapUtil;

import java.util.ArrayList;
import java.util.List;

class TournamentGameManagerTest {

    @Test
    void runTournament() {
        List<Map> l_mapList = new ArrayList<>();
        l_mapList.add(MapUtil.loadMap("europe.map"));
        l_mapList.add(MapUtil.loadMap("validMap2.txt"));

        List<Strategy> l_strategyList = new ArrayList<>();
        l_strategyList.add(new AggressiveStrategy());
        l_strategyList.add(new BenevolentStrategy());
        l_strategyList.add(new RandomStrategy());
        l_strategyList.add(new CheaterStrategy());

        int l_numGames = 1;
        int l_maxTurns = 300;

        TournamentGameManager l_tournamentManager = new TournamentGameManager();
        l_tournamentManager.setD_mapList(l_mapList);
        l_tournamentManager.setD_strategyList(l_strategyList);
        l_tournamentManager.setD_numGames(l_numGames);
        l_tournamentManager.setD_maxTurns(l_maxTurns);
        l_tournamentManager.runTournament();

    }
}