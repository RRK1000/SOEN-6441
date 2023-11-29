package global;

/**
 * Displays the messages in the game.
 *
 * @author Rishi Ravikumar
 */
public class Constants {
    /**
     * Displays help to get the commands available
     */
    public static final String HELP_MESSAGE = "Try 'help' to get instructions of available commands";
    /**
     * Displays the usage of the all the commands required to play the game
     */
    public static final String MAP_INIT_HELP = "usage: " +
            "\n\t loadmap <filename> :\tLoads a map from a file " +
            "\n\t loadgame <filename> :\tLoads a game from a file " +
            "\n\t editmap <filename> :\tLoads a map from an existing 'domination' map file, or create a new map from scratch if the file does not exist." +

            "\n\n\t editcontinent -add <continentID> <continentvalue> -remove <continentI>" +
            "\t: Add/Remove continents to the map" +
            "\n\t editcountry -add <countryID> <continentID> -remove <countryID>" +
            "\t\t\t: Add/Remove countries to the map" +
            "\n\t editneighbor -add <countryID> <neighborcountryID> \n\t\t\t\t\t\t-remove <countryID> <neighborcountryID>" +
            "\t\t\t\t: Add/Remove neighbour countries to the map" +
            "\n" +
            "\n\t savemap <filename> :\tSaves the user-created map into a file path given" +
            "\n\t validatemap \t\t:\tVerifies the map correctness" +
            "\n" +
            "\n\t showmap\t: Displays the map that is loaded, if valid"+
            "\n" +
            "\n\t tournament -M <listofmapfiles> -P <listofplayerstrategies> -G <numberofgames> -D <maxnumberofturns>\t: " +
            "Begins a tournament, and displays a report after simulating the tournament\n";
    /**
     * Displayed while exiting or ending the game.
     */
    public static final String GAME_EXIT = "\t exit : Command to end game";

    /**
     * Displays the usage of commands during Game startup phase
     */
    public static final String GAME_STARTUP_HELP = "usage: " +
            "\n\t gameplayer -add <playername> <strategy> -remove <playername>  :\tAdd/Remove player from the game " +
            "\n\t assigncountries\t\t\t\t\t\t\t:\tAssigns countries to the players, and starts the game\n";

    /**
     * Displays the usage of command to issue order from a player
     */
    public static final String ISSUE_ORDER_HELP = "usage: " +
            "\n\t deploy <countryID> <num> \t: deploys armies to owned countries" +
            "\n\t advance <countryfromID> <<countrytoID> <num> \t: attacks an unowned country" +
            "\n\t bomb <countryID> \t: attacks an unowned country which loses half of its army units" +
            "\n\t blockade <countryID> \t: triple the number of armies on an owned country and makes it a neutral territory in the next turn" +
            "\n\t airlift <sourcecountryID> <targetcountryID> <numarmies> \t: move a number of armies from source country to target country, both owned by current player" +
            "\n\t negotiate <playerID> \t: prevent attacks between the current player and another player until the end of the turn" +
            "\n\t showmap \t\t\t\t\t: show all countries and continents, armies on each country, ownership, and connectivity in a way that\n" +
            "\n\t savegame <filename> :\tSaves the current game state to a file " +
            "\t\t\t\t\t\t\t\t enables efficient game play";

    /**
     * Displays the map of the game.
     */
    public static final String IN_GAME_HELP = "\n\t showmap :\tshow all countries and continents, armies on each country, ownership, and connectivity\n";

    /**
     * Displayed when the player enters incorrect input
     */
    public static final String CMD_ERROR = "invalid command/option";

    /**
     * Displayed when incorrect command from a different phase is given.
     */
    public static final String INVALID_PHASE_ERROR = "invalid command for the current phase.";
}
