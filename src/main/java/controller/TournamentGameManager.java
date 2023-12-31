package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import gamelog.LogManager;
import global.Strategies;
import models.Map;
import models.Order;
import models.Player;
import strategy.AggressiveStrategy;
import strategy.BenevolentStrategy;
import strategy.CheaterStrategy;
import strategy.RandomStrategy;
import util.MapUtil;

/**
 * The {@code TournamentGameManager} class simulates a tournament scenario for the game.
 * @author Rishi Ravikumar
 */
public class TournamentGameManager {

    private final SortedMap<String, ArrayList<String>> d_resultMap;
    private List<Map> d_mapList;
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

    /**
     * Sets the list of maps for tournament
     * @param p_mapList list of maps
     */
    public void setD_mapList(List<Map> p_mapList) {
        this.d_mapList = p_mapList;
    }

    /**
     * Sets the list of strategies for tournament
     * @param p_strategyList list of strategies
     */
    public void setD_strategyList(List<String> p_strategyList) {
        this.d_strategyList = p_strategyList;
    }

    /**
     * Sets the number of games for tournament
     * @param p_numGames number of games
     */
    public void setD_numGames(int p_numGames) {
        this.d_numGames = p_numGames;
    }

    /**
     * Sets the max turns to be played in the tournament
     * @param p_maxTurns maximum turns
     */
    public void setD_maxTurns(int p_maxTurns) {
        this.d_maxTurns = p_maxTurns;
    }

    /**
     * Methods to simulate the tournament, over all the maps
     */
    public void runTournament() {
        int l_mapIndex = 0;
        for (Map l_map : d_mapList) {
            d_resultMap.put("Map " + l_mapIndex, new ArrayList<>());
            for (int l_i = 0; l_i < d_numGames; l_i++) {
                GameManager l_gameManager = setUpGameManager(l_map);

                boolean l_hasWinner = false;
                for (int l_j = 0; l_j < d_maxTurns; l_j++) {
                    Player l_currentPlayer = l_gameManager.getD_playerList().get(l_gameManager.getD_currentPlayerTurn());

                    // generates and issues orders based on the player strategy
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

                    // handles the case where all players have completed issuing orders
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
                            l_hasWinner = true;
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
                l_gameManager.setD_gamePhase(l_gameManager.getD_gamePhase().nextPhase());
                l_gameManager.getD_gamePhase().executeOrder(l_gameManager);
                l_gameManager.setD_gamePhase(l_gameManager.getD_gamePhase().nextPhase());

                l_gameManager.showMap();
                if (!l_hasWinner) d_resultMap.get("Map " + l_mapIndex).add("Draw");
            }
            l_mapIndex++;
        }

        System.out.println("\n\nTournament Report");
        for (String l_key : d_resultMap.keySet()) {
            System.out.println(l_key + ": " + d_resultMap.get(l_key));
        }
    }

    /**
     * Method to set up a game manager with the players and the map
     * @param p_map {@link Map} with which a {@link GameManager} object is initialised
     * @return initialised {@link GameManager} object
     */
    private GameManager setUpGameManager(Map p_map) {
        GameManager l_gameManager = new GameManager();
        l_gameManager.setD_map(p_map);
        l_gameManager.setD_isTournamentGame(true);
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
