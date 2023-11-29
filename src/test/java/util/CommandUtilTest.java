package util;

import org.junit.jupiter.api.Test;
import phases.InitMapPhase;
import phases.IssueOrderPhase;
import phases.StartupPhase;
import global.Commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.CommandUtil.isValidGameCommand;

/**
 * Tests for command validation utility
 * @author Rishi Ravikumar
 */
class CommandUtilTest {

    /**
     * Valid test cases for Map_Init phase
     */
    @Test
    void isValidCmd1() {
        String[] l_cmdList = {
                "editcontinent -add 2 1",
                "editcountry -add 1 1 -add 2 1 -add 3 1 -add 4 1",
                "editcountry -remove 4",
                "editneighbor -add 1 2 -add 1 2 -add 2 3 -add 3 1"
        };

        for (String command: l_cmdList) {
            assertTrue(CommandUtil.isValidCmd(command, new InitMapPhase()));
        }
    }

    /**
     * Invalid test cases for Map_Init phase
     */
    @Test
    void isValidCmd2() {
        String[] l_cmdList = {
                "editcontinent -add 2 ",
                "editcountry -remove ",
                "editneighbor -add 1 2 3"
        };

        for (String command: l_cmdList) {
            assertFalse(CommandUtil.isValidCmd(command, new InitMapPhase()));
        }
    }


    /**
     * Valid test cases for Issue Order command
     */
    @Test
    void isValidCmd3() {
        String[] cmdList = {
                "deploy 3 3",
                "advance 1 2 3",
                "airlift 1 2 3",
                "blockade 1",
                "negotiate 1",
        };

        for (String command: cmdList) {
            assertTrue(CommandUtil.isValidCmd(command, IssueOrderPhase.getInstance()));
        }
    }

    /**
     * Invalid test cases for deploy command
     */
    @Test
    void isValidCmd4() {
        String[] cmdList = {
                "deploy 3 3 3",
                "deploy 3",
                "deplo 3 3"
        };

        for (String command: cmdList) {
            assertFalse(CommandUtil.isValidCmd(command, IssueOrderPhase.getInstance()));
        }
    }

    /**
     * Invalid test cases for advance command
     */
    @Test
    void isValidCmd5() {
        String[] cmdList = {
                "advance 1 2 3 4",
                "advance 1 2",
                "advance 1"
        };

        for (String command: cmdList) {
            assertFalse(CommandUtil.isValidCmd(command, IssueOrderPhase.getInstance()));
        }
    }

    /**
     * Invalid test cases for incorrect InitMap Phase commands
     */
    @Test
    void isValidCmd6() {
        String[] cmdList = {
                "deploy 3 3",
                "advance 1 2 3",
                "airlift 1 2 3",
                "blockade 1",
                "negotiate 1",
        };

        for (String command: cmdList) {
            assertFalse(CommandUtil.isValidCmd(command, StartupPhase.getInstance()));
        }
    }

    /**
     * Invalid test cases for incorrect Issue Order Phase commands
     */
    @Test
    void isValidCmd7() {
        String[] cmdList = {
                "editcontinent -add 2 1",
                "editcountry -add 1 1 -add 2 1 -add 3 1 -add 4 1",
                "editcountry -remove 4",
                "editneighbor -add 1 2 -add 1 2 -add 2 3 -add 3 1",
                "gameplayer -add p1 -remove p2"
        };

        for (String command: cmdList) {
            assertFalse(CommandUtil.isValidCmd(command, IssueOrderPhase.getInstance()));
        }
    }

    /**
     * Test case to verify that the {@code isValidGameCommand} method correctly identifies a valid input
     */
    @Test
    public void testIsValidGameCommand(){
        String l_input = "EDIT_COUNTRY";
        String[] validCommands = {
                "EDIT_CONTINENT",
                "EDIT_COUNTRY",
                "EDIT_NEIGHBOR",
                "SHOW_MAP",
                "SAVE_MAP",
                "EDIT_MAP",
                "VALIDATE_MAP",
                "LOAD_GAME",
                "LOAD_GAME"
        };
        boolean l_result = CommandUtil.isValidGameCommand(l_input, validCommands);
        assertTrue(l_result);
    }

    /**
     * Test case to verify that the {@code isValidGameCommand} method correctly identifies an invalid input
     */
    @Test
    public void testIsValidGameCommandInvalidInput(){
        String l_input = "INVALID_COMMAND";
        String[] validCommands = {
                "EDIT_CONTINENT",
                "EDIT_COUNTRY",
                "EDIT_NEIGHBOR",
                "SHOW_MAP",
                "SAVE_MAP",
                "EDIT_MAP",
                "VALIDATE_MAP",
                "LOAD_GAME",
                "LOAD_GAME"
        };
        boolean l_result = CommandUtil.isValidGameCommand(l_input, validCommands);
        assertFalse(l_result);
    }
}