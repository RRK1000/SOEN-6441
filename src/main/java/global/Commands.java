package global;

/**
 * List of valid commands
 *
 * @author Rishi Ravikumar
 */
public class Commands {
    public static final String HELP = "help";

    /**
     * Map Editor phase
     */
    public static final String EDIT_CONTINENT = "editcontinent";
    public static final String EDIT_COUNTRY = "editcountry";
    public static final String EDIT_NEIGHBOR = "editneighbor";
    public static final String SHOW_MAP = "showmap";
    public static final String SAVE_MAP = "savemap";
    public static final String EDIT_MAP = "editmap";
    public static final String VALIDATE_MAP = "validatemap";

    /**
     * Game Startup phase
     */
    public static final String LOAD_MAP = "loadmap";
    public static final String GAME_PLAYER = "gameplayer";
    public static final String ASSIGN_COUNTRIES = "assigncountries";

    /**
     * Issue order phase
     */
    public static final String DEPLOY_ORDER = "deploy";
    public static final String ADVANCE_ORDER = "advance";
    public static final String BOMB_ORDER = "bomb";
    public static final String BLOCKADE_ORDER = "blockade";
    public static final String AIRLIFT_ORDER = "airlift";
    public static final String DIPLOMACY_ORDER = "negotiate";
    public static final String COMMIT = "commit";



}
