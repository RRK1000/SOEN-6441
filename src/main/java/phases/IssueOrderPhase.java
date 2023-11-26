package phases;

import controller.GameManager;
import gamelog.LogManager;
import models.*;
import orders.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class implements the commands in the map initialization phase
 *
 * @author Anuja Somthankar
 */
public class IssueOrderPhase extends Phase {

    /**
     * Singleton instance
     */
    private static IssueOrderPhase l_instance;

    /**
     * Private constructor to present instantiation
     */
    public IssueOrderPhase(){}

    /**
     * Get the singleton instance for IssueOrderPhase
     * @return IssueOrderPhase instance
     */
    public static IssueOrderPhase getInstance(){
        if(l_instance==null){
            l_instance= new IssueOrderPhase();
        }
        return l_instance;
    }
    /**
     * This method shifts the game phase to the next phase.
     *
     * @return Phase class
     */
    @Override
    public Phase nextPhase() {
        LogManager.logAction("\nPhase changed from IssueOrderPhase to ExecuteOrderPhase");
        return ExecuteOrderPhase.getInstance();
    }

    /**
     * Deploys an order
     *
     * @param p_currentPlayer The current player
     * @param p_country       The country in the order
     * @param p_num           The number of armies to be deployed
     */
    @Override
    public void deploy(GameManager p_gameManager, Player p_currentPlayer, Country p_country, int p_num) {
        if (p_currentPlayer != null) {
            // Create an order using the provided parameters (p_countryID and num)
            Order l_order = new DeployOrder(p_currentPlayer, p_country, p_num);
            if (!l_order.isValid()) {
                return;
            }
            p_currentPlayer.setD_currentOrder(l_order);
            // Call the issue_order() method of the current player to add the order
            p_currentPlayer.issueOrder();
            System.out.println("Issued Deploy Order");

            p_gameManager.updatePlayerTurn();
        } else {
            // Handle the case where there is no current player or it's not their turn
            System.out.println("No current player or it's not their turn to issue orders.");
        }
    }

    /**
     * Advances(attack) armies from an owned country to an opponents
     *
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    @Override
    public void advance(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num) {
        Order l_order = new AdvanceOrder(p_currentPlayer, p_countryFrom, p_countryTo, p_num);
        if (!l_order.isValid()) {
            return;
        }
        p_currentPlayer.setD_currentOrder(l_order);
        p_currentPlayer.issueOrder();
        System.out.println("Issued Advance Order");

        p_gameManager.updatePlayerTurn();
    }

    /**
     * Bomb an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    @Override
    public void bomb(GameManager p_gameManager, Player p_currentPlayer, Country p_country) {
        Order l_order = new BombOrder(p_currentPlayer, p_country);
        if (!l_order.isValid()) {
            return;
        }
        p_currentPlayer.setD_currentOrder(l_order);
        p_currentPlayer.issueOrder();
        System.out.println("Issued Bomb Order");

        p_gameManager.updatePlayerTurn();
    }

    /**
     * Airlifts armies from player's country to another of their owned countries
     *
     * @param p_currentPlayer The current player
     * @param p_countryFrom   Country from where the armies would attack
     * @param p_countryTo     Country on which the attack occurs
     * @param p_num           Number of armies attacking
     */
    @Override
    public void airlift(GameManager p_gameManager, Player p_currentPlayer, Country p_countryFrom, Country p_countryTo, int p_num) {
        Order l_order = new AirliftOrder(p_currentPlayer, p_countryFrom, p_countryTo, p_num);
        if (!l_order.isValid()) {
            return;
        }
        p_currentPlayer.setD_currentOrder(l_order);
        p_currentPlayer.issueOrder();
        System.out.println("Issued Airlift Order");

        p_gameManager.updatePlayerTurn();
    }

    /**
     * Blockade an opponent's country neighbouring the current player
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_country       The opponent's country
     */
    @Override
    public void blockade(GameManager p_gameManager, Player p_currentPlayer, Country p_country) {
        Order l_order = new BlockadeOrder(p_currentPlayer, p_country);
        if (!l_order.isValid()) {
            return;
        }
        p_currentPlayer.setD_currentOrder(l_order);
        p_currentPlayer.issueOrder();
        System.out.println("Issued Blockade Order");

        p_gameManager.updatePlayerTurn();
    }

    /**
     * Enforces negotiation for a turn
     *
     * @param p_gameManager   The game manager
     * @param p_currentPlayer The current player
     * @param p_otherPlayer   The opponent
     */
    @Override
    public void negotiate(GameManager p_gameManager, Player p_currentPlayer, Player p_otherPlayer) {
        Order l_order = new NegotiateOrder(p_currentPlayer, p_otherPlayer);
        if (!l_order.isValid()) {
            return;
        }
        p_currentPlayer.setD_currentOrder(l_order);
        p_currentPlayer.issueOrder();
        System.out.println("Issued Diplomacy Order");

        p_gameManager.updatePlayerTurn();
    }

    /**
     * Saves a game to a file
     *
     * @param p_gameManager {@link GameManager}
     * @param p_filename    file to load the game from
     */
    @Override
    public void saveGame(GameManager p_gameManager, String p_filename) {
        GameState l_gameState = new GameState(p_gameManager.getD_skipTurnList(),
                p_gameManager.getD_gamePhase().getClass().getName(), p_gameManager.getD_playerList(),
                p_gameManager.getD_currentPlayerTurn(), p_gameManager.getD_map());

        try
        {
            FileOutputStream l_file = new FileOutputStream("src/main/resources/games/" + p_filename);
            ObjectOutputStream l_out = new ObjectOutputStream(l_file);
            l_out.writeObject(l_gameState);
            l_out.close();
            l_file.close();

            System.out.println("Game state saved");
        } catch (IOException l_e) {
            throw new RuntimeException(l_e);
        }
    }
}
