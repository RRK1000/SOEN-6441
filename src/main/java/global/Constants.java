package global;

public class Constants {
    public static String HELP_MESSAGE = "Try 'help' to get instructions of available commands";

    public static String MAP_INIT_HELP = "usage: " +
            "\n\t loadmap <filename> :\tLoads a map from the given file path " +
            "\n\t editmap <filename> :\tLoads a map from an existing “domination” map file, or create a new map from scratch if the file does not exist." +

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
            "\n\t showmap\t: Displays the map that is loaded, if valid";

    public static String GAME_EXIT = "\t exit : Command to end game";

    public static String GAME_STARTUP_HELP = "usage: " +
            "\n\t gameplayer -add <playername> -remove <playername>  :\tAdd/Remove player from the game " +
            "\n\t assigncountries\t\t\t\t\t\t\t:\tAssigns countries to the players, and starts the game\n";

    public static String ISSUE_ORDER_HELP = "usage: " +
            "\n\t deploy <countryID> <num> \t: deploys armies to owned countries" +
            "\n\t showmap \t\t\t\t\t: show all countries and continents, armies on each country, ownership, and connectivity in a way that\n" +
            "\t\t\t\t\t\t\t\t enables efficient game play";

    public static String IN_GAME_HELP = "\n\t showmap :\tshow all countries and continents, armies on each country, ownership, and connectivity\n";
    public static String CMD_ERROR = "unknown command/option";
}
