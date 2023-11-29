package controller;

import models.Map;
import org.junit.jupiter.api.Test;
import util.DominationMapFileReader;
import util.MapFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TournamentGameManagerTest {

    @Test
    void runTournament() {
        List<Map> l_mapList = new ArrayList<>();
        List<String> l_strategyList = new ArrayList<>();
        Map l_loadedMap1 = new Map();
        Map l_loadedMap2 = new Map();
        l_strategyList.add("Aggressive");
        l_strategyList.add("Benevolent");
        l_strategyList.add("Random");
        l_strategyList.add("Cheater");

        int l_numGames = 4;
        int l_maxTurns = 300;

        TournamentGameManager l_tournamentManager = new TournamentGameManager();
        MapFileReader l_loadfileReader = new DominationMapFileReader();
        try {
            l_loadedMap1 = l_loadfileReader.loadMap("europe.map");
            l_loadedMap2 = l_loadfileReader.loadMap("validMap2.txt");
        } catch (IOException e) {
            System.out.println("Map not loaded");
            return;
        }
        l_mapList.add(l_loadedMap1);
        l_mapList.add(l_loadedMap2);
        l_tournamentManager.setD_mapList(l_mapList);
        l_tournamentManager.setD_strategyList(l_strategyList);
        l_tournamentManager.setD_numGames(l_numGames);
        l_tournamentManager.setD_maxTurns(l_maxTurns);
        l_tournamentManager.runTournament();

    }
}