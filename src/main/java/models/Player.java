package models;

import gamelog.LogEntryBuffer;
import gamelog.LogFileWriter;
import global.Cards;
import orders.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents a Player in the game.
 * Manages player-related functionalities.
 * This class is used in relationship with the GameController class to manage player actions.
 *
 * @author Anuja Somthankar
 * @author Rishi Ravikumar
 */
public class Player implements Serializable {
    private static final LogEntryBuffer d_logBuffer;
    private static final LogFileWriter d_logWriter;

    static {
        Path l_logPath = Paths.get(System.getProperty("user.dir"), "game.log");
        d_logBuffer = new LogEntryBuffer();
        d_logWriter = new LogFileWriter(l_logPath);
        d_logBuffer.addObserver(d_logWriter);
    }

    private String d_playerName;
    private int d_numArmies;
    private List<Country> d_countryList;
    private List<Continent> d_continentList;
    private List<Order> d_orderList;
    private Order d_currentOrder;
    private List<String> d_playerCardList;
    private List<Player> d_negotiationList;


    /**
     * Default constructor for Player class
     *
     * @param p_playerName Name of the player
     */
    public Player(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_countryList = new ArrayList<>();
        this.d_continentList = new ArrayList<>();
        this.d_orderList = new ArrayList<>();
        this.d_playerCardList = new ArrayList<>();
        this.d_negotiationList = new ArrayList<>();
    }

    /**
     * Constructor with parameters for Player class
     *
     * @param p_playerName   Name of the player
     * @param p_numArmies    Number of armies the player has
     * @param p_countryList  List of countries the player owns
     * @param p_orderList    List of orders the player has issued
     * @param p_currentOrder Current Order issued by the player
     */
    public Player(String p_playerName, int p_numArmies, List<Country> p_countryList, List<Order> p_orderList, Order p_currentOrder) {
        this.d_playerName = p_playerName;
        this.d_numArmies = p_numArmies;
        this.d_countryList = p_countryList;
        this.d_orderList = p_orderList;
        this.d_currentOrder = p_currentOrder;
    }

    /**
     * Notifies log about action
     *
     * @param p_action Action done by user
     */
    private static void logAction(String p_action) {
        d_logBuffer.setActionInfo(p_action);
        d_logBuffer.notifyObservers();
    }

    /**
     * Returns the card list of the player
     *
     * @return Card list
     */
    public List<String> getD_playerCardList() {
        return d_playerCardList;
    }

    /**
     * Sets the card list
     *
     * @param p_playerCardList Card list
     */
    public void setD_playerCardList(List<String> p_playerCardList) {
        this.d_playerCardList = p_playerCardList;
    }

    /**
     * Adds a player to this player's negotiation list
     *
     * @param p_player Player object
     */
    public void addPlayerNegotiation(Player p_player) {
        d_negotiationList.add(p_player);
    }

    /**
     * Empties the negotiation list
     */
    public void clearPlayerNegotiation() {
        d_negotiationList.clear();
    }

    /**
     * Adds a country to the player's list of countries
     *
     * @param p_country The country to be added
     */
    public void addCountry(Country p_country) {
        this.d_countryList.add(p_country);
    }

    /**
     * Issues an order Adds the current order issued by the player to their order list
     */
    public void issueOrder() {

        if (d_currentOrder instanceof AdvanceOrder) {
            AdvanceOrder l_advanceOrder = (AdvanceOrder) d_currentOrder;

            Country l_countryFrom = l_advanceOrder.getD_countryfrom();
            l_countryFrom.setD_numArmies(l_countryFrom.getD_numArmies() - l_advanceOrder.getD_num());
            d_orderList.add(l_advanceOrder);
        } else if (d_currentOrder instanceof AirliftOrder) {
            AirliftOrder l_airliftOrder = (AirliftOrder) d_currentOrder;
            d_playerCardList.remove(Cards.AIRLIFT_CARD);
            d_orderList.add(l_airliftOrder);
        } else if (d_currentOrder instanceof BlockadeOrder) {
            BlockadeOrder l_blockadeOrder = (BlockadeOrder) d_currentOrder;
            d_playerCardList.remove(Cards.BLOCKADE_CARD);
            d_orderList.add(l_blockadeOrder);
        } else if (d_currentOrder instanceof BombOrder) {
            BombOrder l_bombOrder = (BombOrder) d_currentOrder;
            d_playerCardList.remove(Cards.BOMB_CARD);
            d_orderList.add(l_bombOrder);
        } else if (d_currentOrder instanceof DeployOrder) {
            DeployOrder l_deployOrder = (DeployOrder) d_currentOrder;
            d_numArmies -= l_deployOrder.getD_num();
            d_orderList.add(l_deployOrder);
        } else if (d_currentOrder instanceof NegotiateOrder) {
            NegotiateOrder l_negotiateOrder = (NegotiateOrder) d_currentOrder;
            d_playerCardList.remove(Cards.DIPLOMACY_CARD);
            d_orderList.add(l_negotiateOrder);
        }
        logAction(this.d_playerName + " issued an order: " + d_currentOrder.getClass().getSimpleName());

    }


    /**
     * Moves to the next order
     *
     * @return The next order, or null if no orders are left
     */
    public Order nextOrder() {
        Order l_order = null;
        if (!d_orderList.isEmpty()) {
            l_order = d_orderList.get(0);
            d_orderList.remove(0);
        }
        return l_order;
    }

    /**
     * Gets the player name
     *
     * @return The player name
     */
    public String getD_playerName() {
        return d_playerName;
    }

    /**
     * Sets the player name
     *
     * @param p_playerName The new player name
     */
    public void setD_playerName(String p_playerName) {
        this.d_playerName = p_playerName;
    }

    /**
     * Gets the number of armies a player holds
     *
     * @return d_numArmies Number of armies of a player
     */
    public int getD_numArmies() {
        return d_numArmies;
    }

    /**
     * Sets the number of armies a player holds
     *
     * @param p_numArmies Number of armies of a player
     */
    public void setD_numArmies(int p_numArmies) {
        this.d_numArmies = p_numArmies;
    }

    /**
     * Gets the list of countries held by the player
     *
     * @return d_countryList List of Country objects under player's ownership
     */
    public List<Country> getD_countryList() {
        return d_countryList;
    }

    /**
     * Sets the list of countries held by the player
     *
     * @param p_countryList List of Country objects under player's ownership
     */
    public void setD_countryList(List<Country> p_countryList) {
        this.d_countryList = p_countryList;
    }

    /**
     * Gets the list of orders issued by the player
     *
     * @return d_orderList List of orders issued by the player
     */
    public List<Order> getD_orderList() {
        return d_orderList;
    }

    /**
     * Sets the list of orders issued by the player
     *
     * @param p_orderList List of orders issued by the player
     */
    public void setD_orderList(List<Order> p_orderList) {
        this.d_orderList = p_orderList;
    }

    /**
     * Gets the current Order of the current player that is being issued
     *
     * @return current Order of the current player that is being issued
     */
    public Order getD_currentOrder() {
        return d_currentOrder;
    }

    /**
     * Sets the current Order of the current player that is being issued
     *
     * @param p_currentOrder current Order of the current player that is being issued
     */
    public void setD_currentOrder(Order p_currentOrder) {
        this.d_currentOrder = p_currentOrder;
    }

    /**
     * Gets the list of continents held by the player
     *
     * @return List of continents the player owns
     */

    public List<Continent> getD_continentList() {
        return d_continentList;
    }

    /**
     * Sets the list of continents held by the player
     *
     * @param p_continentList List of Continent objects under player's ownership
     */
    public void setD_continentList(List<Continent> p_continentList) {
        this.d_continentList = p_continentList;
    }

    /**
     * Adds a random card to the player's card list
     */
    public void addRandomCard() {
        List<String> l_cardsList = Arrays.asList(Cards.BOMB_CARD, Cards.BLOCKADE_CARD, Cards.BOMB_CARD, Cards.DIPLOMACY_CARD);
        Random l_rndm = new Random();
        int l_randomIndex = l_rndm.nextInt(l_cardsList.size());
        String l_card = l_cardsList.get(l_randomIndex);
        this.d_playerCardList.add(l_card);
        System.out.println(l_card + " gained by " + this.d_playerName);
        logAction(this.d_playerName + " gained a " + l_card + " card.");

    }

    /**
     * Checks if 2 players are in negotiation with each other
     *
     * @param p_otherPlayer Player object
     * @return true if players are in negotiation, false otherwise
     */
    public boolean isInNegotiationWith(Player p_otherPlayer) {
        return d_negotiationList.contains(p_otherPlayer);
    }

    public List<Player> getD_negotiationList() {
        return d_negotiationList;
    }

    public void setD_negotiationList(List<Player> d_negotiationList) {
        this.d_negotiationList = d_negotiationList;
    }
}