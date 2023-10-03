package controller;

/**
 * Represents the different phases of the game.
 */
enum GamePhase {
    /**
     * Initialize the map
     */
    Map_Init,
    /**
     * Game startup phase
     */
    Game_Startup,
    /**
     * Assigning armies to the player
     */
    AssignReinforcements,
    /**
     * Issuing orders from the players
     */
    IssueOrder,
    /**
     * Execute the order issued by the player
     */
    ExecuteOrder
}
