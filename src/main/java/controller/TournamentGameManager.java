package controller;

import gamelog.LogManager;
import global.Strategies;
import models.Map;
import models.Order;
import models.Player;
import org.apache.commons.lang.StringUtils;
import strategy.AggressiveStrategy;
import strategy.BenevolentStrategy;
import strategy.CheaterStrategy;
import strategy.RandomStrategy;
import util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class TournamentGameManager {

    private final SortedMap<String, ArrayList<String>> d_resultMap;
    private List<String> d_mapList;
    private List<String> d_strategyList;
    private int d_numGames;
    private int d_maxTurns;

    /**
     * Default constructor for GameManager.
     */
    public TournamentGameManager() {
        d_mapList = new ArrayList<>();
        d_strategyList = new ArrayList<>();
        d_resultMap = new TreeMap<>();
    }

    public void setD_mapList(List<String> d_mapList) {
        this.d_mapList = d_mapList;
    }

    public void setD_strategyList(List<String> d_strategyList) {
        this.d_strategyList = d_strategyList;
    }

    public void setD_numGames(int d_numGames) {
        this.d_numGames = d_numGames;
    }

    public void setD_maxTurns(int d_maxTurns) {
        this.d_maxTurns = d_maxTurns;
    }

    public void runTournament() {
        int l_mapIndex = 0;
        for (String l_map : d_mapList) {
            d_resultMap.put("Map " + l_mapIndex, new ArrayList<>());
            for (int l_i = 0; l_i < d_numGames; l_i++) {
                GameManager l_gameManager = setUpGameManager(MapUtil.loadMap(l_map));

                boolean hasWinner = false;
                for (int l_j = 0; l_j < d_maxTurns; l_j++) {
                    Player l_currentPlayer = l_gameManager.getD_playerList().get(l_gameManager.getD_currentPlayerTurn());
                    if (!l_gameManager.getD_skipTurnList().contains(l_gameManager.getD_currentPlayerTurn())) {
                        Order l_order = l_currentPlayer.getD_playerStrategy().createOrder(l_gameManager);
                        if (null == l_order) {
                            l_gameManager.addPlayerToSkipList(l_gameManager.getD_currentPlayerTurn());
                            continue;
                        }
                        l_currentPlayer.setD_currentOrder(l_order);
                        l_currentPlayer.issueOrder();
                        LogManager.logAction("[" + l_currentPlayer.getD_playerName() + "] Order issued: " + StringUtils.remove(l_order.getClass().getName(), "orders."));


                        String l_currentPlayerName = l_currentPlayer.getD_playerName();
                        System.out.println("Player " + l_currentPlayerName + " turn over. ");
                        System.out.println();
                    }

                    if (l_gameManager.getD_skipTurnList().toArray().length == l_gameManager.getD_playerList().toArray().length) {
                        l_gameManager.updateNeutralCountriesOnRoundEnd();
                        l_gameManager.updatePlayerDiplomacyOnRoundEnd();

                        l_gameManager.setD_gamePhase(l_gameManager.getD_gamePhase().nextPhase());
                        l_gameManager.getD_gamePhase().executeOrder(l_gameManager);
                        l_gameManager.setD_gamePhase(l_gameManager.getD_gamePhase().nextPhase());

                        l_gameManager.assignReinforcements();
                        l_gameManager.getD_skipTurnList().clear();
                        l_gameManager.setD_currentPlayerTurn(0);
                        LogManager.logAction("Round end. \n");
                        l_gameManager.updatePlayerList();

                        // Case where game has a winner
                        if (l_gameManager.getD_playerList().size() == 1) {
                            d_resultMap.get("Map " + l_mapIndex).add(l_gameManager.getD_playerList().get(0).getD_playerName());
                            String l_op = "Player " + l_gameManager.getD_playerList().get(0).getD_playerName() + " wins!";
                            System.out.println(l_op);
                            LogManager.logAction(l_op);
                            hasWinner = true;
                            break;
                        }
                    } else {
                        do {
                            l_gameManager.setD_currentPlayerTurn((l_gameManager.getD_currentPlayerTurn() + 1) % l_gameManager.getD_playerList().size());
                        } while (l_gameManager.getD_skipTurnList().contains(l_gameManager.getD_currentPlayerTurn()));
                    }

                    l_currentPlayer = l_gameManager.getD_playerList().get(l_gameManager.getD_currentPlayerTurn());
                    LogManager.logAction("Player turn updated to " + l_currentPlayer.getD_playerName());
                    System.out.println("Player " + l_currentPlayer.getD_playerName() + "'s turn ");
                    System.out.println("available reinforcement armies: " + l_currentPlayer.getD_numArmies());
                    if (!l_currentPlayer.getD_playerCardList().isEmpty()) {
                        System.out.println("Cards available for Player " + l_currentPlayer.getD_playerName() + ": " + l_currentPlayer.getD_playerCardList());
                    } else {
                        System.out.println("No cards are available to Player " + l_currentPlayer.getD_playerName());
                    }
                }
                l_gameManager.showMap();
                if (!hasWinner) d_resultMap.get("Map " + l_mapIndex).add("Draw");
            }
            l_mapIndex++;
        }

        System.out.println("\n\nTournament Report");
        for (String l_key : d_resultMap.keySet()) {
            System.out.println(l_key + ": " + d_resultMap.get(l_key));
        }
    }

    private GameManager setUpGameManager(Map p_map) {
        GameManager l_gameManager = new GameManager();
        l_gameManager.setD_map(p_map);
        int l_pid = 1;
        for (String l_strategy : d_strategyList) {
            String l_playerName = l_strategy + "Player-p" + l_pid++;

            switch (l_strategy) {
                case Strategies.AGGRESSIVE_STRATEGY:
                    l_gameManager.addPlayer(l_playerName, new AggressiveStrategy());
                    break;
                case Strategies.BENEVOLENT_STRATEGY:
                    l_gameManager.addPlayer(l_playerName, new BenevolentStrategy());
                    break;
                case Strategies.RANDOM_STRATEGY:
                    l_gameManager.addPlayer(l_playerName, new RandomStrategy());
                    break;
                case Strategies.CHEATER_STRATEGY:
                    l_gameManager.addPlayer(l_playerName, new CheaterStrategy());
                    break;
            }
        }
        l_gameManager.setD_gamePhase(l_gameManager.getD_gamePhase().nextPhase());
        l_gameManager.getD_gamePhase().assignCountries(l_gameManager);
        return l_gameManager;
    }
}
