package models;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private List<Integer> d_skipTurnList;
    private String d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;

    public GameState(List<Integer> p_skipTurnList, String p_gamePhase, List<Player> p_playerList, int p_currentPlayerTurn, Map p_map) {
        this.d_skipTurnList = p_skipTurnList;
        this.d_gamePhase = p_gamePhase;
        this.d_playerList = p_playerList;
        this.d_currentPlayerTurn = p_currentPlayerTurn;
        this.d_map = p_map;
    }

    public List<Integer> getD_skipTurnList() {
        return d_skipTurnList;
    }

    public void setD_skipTurnList(List<Integer> d_skipTurnList) {
        this.d_skipTurnList = d_skipTurnList;
    }

    public String getD_gamePhase() {
        return d_gamePhase;
    }

    public void setD_gamePhase(String d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }

    public List<Player> getD_playerList() {
        return d_playerList;
    }

    public void setD_playerList(List<Player> d_playerList) {
        this.d_playerList = d_playerList;
    }

    public int getD_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    public void setD_currentPlayerTurn(int d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
    }

    public Map getD_map() {
        return d_map;
    }

    public void setD_map(Map d_map) {
        this.d_map = d_map;
    }
}
