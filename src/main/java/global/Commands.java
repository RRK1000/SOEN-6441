package global;

/**
 * List of valid commands
 *
 * @author Rishi Ravikumar
 */
public class Commands {
    /**
     * help command
     */
    public static final String HELP = "help";

    // Map Editor phase

    /**
     * editcontinent command
     */
    public static final String EDIT_CONTINENT = "editcontinent";
    /**
     * editcountry command
     */
    public static final String EDIT_COUNTRY = "editcountry";
    /**
     * editneighbor command
     */
    public static final String EDIT_NEIGHBOR = "editneighbor";
    /**
     * showmap command
     */
    public static final String SHOW_MAP = "showmap";
    /**
     * savemap command
     */
    public static final String SAVE_MAP = "savemap";
    /**
     * editmap command
     */
    public static final String EDIT_MAP = "editmap";
    /**
     * validatemap command
     */
    public static final String VALIDATE_MAP = "validatemap";
    /**
     * loadgame command
     */
    public static final String LOAD_GAME = "loadgame";

    // Game Startup phase

    /**
     * loadmap command
     */
    public static final String LOAD_MAP = "loadmap";
    /**
     * gameplayer command
     */
    public static final String GAME_PLAYER = "gameplayer";
    /**
     * assigncountries command
     */
    public static final String ASSIGN_COUNTRIES = "assigncountries";

    // Issue order phase

    /**
     * deploy command
     */
    public static final String DEPLOY_ORDER = "deploy";
    /**
     * advance command
     */
    public static final String ADVANCE_ORDER = "advance";
    /**
     * bomb command
     */
    public static final String BOMB_ORDER = "bomb";
    /**
     * blockade command
     */
    public static final String BLOCKADE_ORDER = "blockade";
    /**
     * airlift command
     */
    public static final String AIRLIFT_ORDER = "airlift";
    /**
     * negotiate command
     */
    public static final String DIPLOMACY_ORDER = "negotiate";
    /**
     * commit command
     */
    public static final String COMMIT = "commit";
    /**
     * savegame command
     */
    public static final String SAVE_GAME = "savegame";

    public static final String TOURNAMENT = "tournament";
}
