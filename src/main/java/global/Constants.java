package global;

/**
 * Displays the messages in the game.
 */
public class Constants {
    /**
     * Displays help to get the commands available
     */
    public static String HELP_MESSAGE = "Try 'help' to get instructions of available commands";
    /**
     * Displays the usage of the all the commands required to play the game
     */
    public static String MAP_INIT_HELP = "usage: " +
            "\n\t loadmap <filename> :\tLoads a map from the given file path " +
            "\n\t editmap <filename> :\tloads a map from an existing “domination” map file, or create a new map from scratch if the file does not exist." +

            "\n\t editcontinent -add <continentID> <continentvalue> -remove <continentID" +
            ":\t Add/Remove continents to the map" +
            "\n\t editcountry -add <countryID> <continentID> -remove <countryID>" +
            ":\t Add/Remove countries to the map" +
            "\n\t editneighbor -add <countryID> <neighborcountryID> -remove <countryID> <neighborcountryID>" +
            ":\t Add/Remove neighbour countries to the map" +
            "\n" +
            "\n\t savemap <filename> :\tsaves the user-created map into a file path given" +
            "\n\t validatemap :\tverifies the map correctness" +
            "\n" +
            "\n\t showmap :\tdisplays the map that is loaded, if valid";
    /**
     * Displayed while exiting or ending the game.
     */
    public static String GAME_EXIT = "\t exit:\tcommand to end game";

    /**
     * Displays the usage of commands during Game startup phase
     */
    public static String GAME_STARTUP_HELP = "usage: " +
            "\n\t gameplayer -add <playername> -remove <playername> :\tAdd/Remove player from the game " +
            "\n\t assigncountries :\tassigns countries to the players, and begins the game";

    /**
     * Displays the usage of command to issue order from a player
     */
    public static String ISSUE_ORDER_HELP = "usage: " +
            "\n\t deploy <countryID> <num> :\tdeploys armies to owned countries" +
            "\n\t showmap :\tshow all countries and continents, armies on each country, ownership, and connectivity in a way that\n" +
                    "\t\t\t enables efficient game play";

    /**
     * Displays the map of the game.
     */
    public static String IN_GAME_HELP = "\n\t showmap :\tshow all countries and continents, armies on each country, ownership, and connectivity\n";

    /**
     * Displayed when the player enters incorrect input
     */
    public static String CMD_ERROR = "unknown command/option";
}
