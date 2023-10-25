package util;

import controller.GamePhase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandUtilTest {

    /**
     * Valid test cases for Map_Init phase
     */
    @Test
    void isValidCmd1() {
        String[] cmdList = {
                "editcontinent -add 2 1",
                "editcountry -add 1 1 -add 2 1 -add 3 1 -add 4 1",
                "editcountry -remove 4",
                "editneighbor -add 1 2 -add 1 2 -add 2 3 -add 3 1"
        };

        for (String command: cmdList) {
            assertTrue(CommandUtil.isValidCmd(command, GamePhase.Map_Init));
        }
    }

    /**
     * Invalid test cases for Map_Init phase
     */
    @Test
    void isValidCmd2() {
        String[] cmdList = {
                "editcontinent -add 2 ",
                "editcountry -remove ",
                "editneighbor -add 1 2 3"
        };

        for (String command: cmdList) {
            assertFalse(CommandUtil.isValidCmd(command, GamePhase.Map_Init));
        }
    }
}