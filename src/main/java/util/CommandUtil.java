package util;

import global.Commands;
import phases.*;

import java.util.HashMap;

/**
 * Represents the Command Utility class.
 * This class provides utility functions for the commands
 *
 * @author Rishi Ravikumar
 */
public class CommandUtil {
    /**
     * Checks the given input against a HashMap of valid option types and valid parameter lengths
     *
     * @param p_input      input string from the CommandParser
     * @param p_optionSpec HashMap of valid option types and the accepted parameter length
     * @return boolean result of option validity check
     */
    private static Boolean hasValidOptions(String p_input, HashMap<String, Integer> p_optionSpec) {
        String[] l_inputOptionList = p_input.split(" -");

        for (String l_option : p_optionSpec.keySet()) {
            for (int l_i = 1; l_i < l_inputOptionList.length; l_i++) {
                if (!l_inputOptionList[l_i].startsWith(l_option))
                    continue;

                if (l_inputOptionList[l_i].startsWith(l_option) &&
                        l_inputOptionList[l_i].split(" ").length != p_optionSpec.get(l_option) + 1
                ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks the given input semantics
     *
     * @param p_input input string from the CommandParser
     * @return boolean result of semantic validity check
     */
    private static Boolean isValidSemantic(String p_input) {
        String[] l_cmdSplit = p_input.split(" ");
        HashMap<String, Integer> l_optionSpec;

        switch (l_cmdSplit[0]) {
            case Commands.EDIT_CONTINENT:

            case Commands.EDIT_COUNTRY:

            case Commands.GAME_PLAYER:
                l_optionSpec = new HashMap<>();
                l_optionSpec.put("add", 2);
                l_optionSpec.put("remove", 1);

                return hasValidOptions(p_input, l_optionSpec);

            case Commands.EDIT_NEIGHBOR:
                l_optionSpec = new HashMap<>();
                l_optionSpec.put("add", 2);
                l_optionSpec.put("remove", 2);

                return hasValidOptions(p_input, l_optionSpec);

            case Commands.SAVE_MAP:
            case Commands.EDIT_MAP:
            case Commands.BOMB_ORDER:
            case Commands.BLOCKADE_ORDER:
            case Commands.LOAD_GAME:
            case Commands.SAVE_GAME:

            case Commands.DIPLOMACY_ORDER:
                return l_cmdSplit.length == 2;

            case Commands.AIRLIFT_ORDER:
            case Commands.ADVANCE_ORDER:
                return l_cmdSplit.length == 4;

            case Commands.DEPLOY_ORDER:
                return l_cmdSplit.length == 3;

            case Commands.TOURNAMENT:
                l_optionSpec = new HashMap<>();
                l_optionSpec.put("M", 1);
                l_optionSpec.put("P", 1);
                l_optionSpec.put("G", 1);
                l_optionSpec.put("D", 1);

                return hasValidOptions(p_input, l_optionSpec) && l_cmdSplit.length == 9;

            default:
                return true;
        }
    }

    /**
     * Checks whether the given input command is applicable against the given game phase
     *
     * @param p_input     input string from the CommandParser
     * @param p_gamePhase current GamePhase set by the GameManager
     * @return boolean result of the GamePhase command validation check
     */
    private static Boolean isValidGamePhaseCmd(String p_input, Phase p_gamePhase) {
        if (p_gamePhase instanceof InitMapPhase) {
            return isValidGameCommand(p_input, new String[] {
                    Commands.EDIT_CONTINENT,
                    Commands.EDIT_COUNTRY,
                    Commands.EDIT_NEIGHBOR,
                    Commands.SHOW_MAP,
                    Commands.SAVE_MAP,
                    Commands.EDIT_MAP,
                    Commands.VALIDATE_MAP,
                    Commands.LOAD_MAP,
                    Commands.LOAD_GAME,
                    Commands.TOURNAMENT
            });

        } else if (p_gamePhase instanceof StartupPhase) {
            return isValidGameCommand(p_input, new String[]{
                    Commands.GAME_PLAYER,
                    Commands.ASSIGN_COUNTRIES,
                    Commands.SHOW_MAP,
                    Commands.LOAD_GAME
            });

        } else if (p_gamePhase instanceof IssueOrderPhase) {
            return isValidGameCommand(p_input, new String[]{
                    Commands.DEPLOY_ORDER,
                    Commands.ADVANCE_ORDER,
                    Commands.BOMB_ORDER,
                    Commands.AIRLIFT_ORDER,
                    Commands.BLOCKADE_ORDER,
                    Commands.DIPLOMACY_ORDER,
                    Commands.SAVE_GAME,
                    Commands.COMMIT,
                    Commands.SHOW_MAP
            });

        } else if (p_gamePhase instanceof ExecuteOrderPhase) {
            return isValidGameCommand(p_input, new String[]{
                    Commands.SHOW_MAP
            });
        }
        return false;
    }

    /**
     * Checks the validity of the input command
     *
     * @param p_input     input string from the CommandParser
     * @param p_gamePhase current gamePhase set by the GameManager
     * @return boolean result of the command validation check
     */
    public static Boolean isValidCmd(String p_input, Phase p_gamePhase) {
        return p_input.equals(Commands.HELP) ||
                (isValidSemantic(p_input) && isValidGamePhaseCmd(p_input, p_gamePhase));
    }

    /**
     * This method checks if the given input is valid against the list of valid commands
     * @param p_input input command given by the player
     * @param p_commands list of the valid commands
     * @return boolean result of the command validation
     */
    public static boolean isValidGameCommand(String p_input, String[] p_commands){
        for(String l_command:p_commands){
            if(p_input.startsWith(l_command)){
                return true;
            }
        }
        return false;
    }
}
