package game;

import controller.CommandParser;
import controller.GameManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import phases.InitMapPhase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for game engine
 * @author Rishi Ravikumar
 */
class GameEngineTest {
    static GameManager d_gameManager;

    /**
     * Setup before test, initialises game manager
     */
    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
    }

    /**
     * Sets game manager to null after tests are done
     */
    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    /**
     * Tests the full game, as mentioned in scenario1.txt
     */
    @Test
    void ScenarioTest1() {
        GameManager d_gameManager = new GameManager();
        assertTrue(d_gameManager.getD_gamePhase() instanceof InitMapPhase);

        try (BufferedReader l_reader = new BufferedReader(new FileReader("src/test/resources/scenario1.txt"))) {
            String l_inputCommand;
            l_inputCommand = l_reader.readLine();

            //Handles the case where the file is empty
            while (l_inputCommand != null) {
                if (l_inputCommand.isEmpty()) {
                    l_inputCommand = l_reader.readLine();
                    continue;
                }
                CommandParser.inputParser(d_gameManager, l_inputCommand);
                l_inputCommand = l_reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the full game, as mentioned in scenario2.txt
     */
    @Test
    void ScenarioTest2() {
        GameManager d_gameManager = new GameManager();

        try (BufferedReader l_reader = new BufferedReader(new FileReader("src/test/resources/scenario2.txt"))) {
            String l_inputCommand;
            l_inputCommand = l_reader.readLine();

            //Handles the case where the file is empty
            while (l_inputCommand != null) {
                if (l_inputCommand.isEmpty()) {
                    l_inputCommand = l_reader.readLine();
                    continue;
                }
                CommandParser.inputParser(d_gameManager, l_inputCommand);
                l_inputCommand = l_reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests the full game, as mentioned in scenario3.txt (attack followed by deploy)
     */
    @Test
    void ScenarioTest3() {
        GameManager d_gameManager = new GameManager();

        try (BufferedReader l_reader = new BufferedReader(new FileReader("src/test/resources/scenario3.txt"))) {
            String l_inputCommand;
            l_inputCommand = l_reader.readLine();

            //Handles the case where the file is empty
            while (l_inputCommand != null) {
                if (l_inputCommand.isEmpty()) {
                    l_inputCommand = l_reader.readLine();
                    continue;
                }
                CommandParser.inputParser(d_gameManager, l_inputCommand);
                l_inputCommand = l_reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertSame(d_gameManager.getD_map().getD_countryByID(1).getD_owner(), d_gameManager.findPlayerByName("p2"));
    }

    /**
     * Tests the full game, as mentioned in scenario4.txt (game ending)
     */
    @Test
    void ScenarioTest4() {
        GameManager d_gameManager = new GameManager();

        try (BufferedReader l_reader = new BufferedReader(new FileReader("src/test/resources/scenario4.txt"))) {
            String l_inputCommand;
            l_inputCommand = l_reader.readLine();

            //Handles the case where the file is empty
            while (l_inputCommand != null) {
                if (l_inputCommand.isEmpty()) {
                    l_inputCommand = l_reader.readLine();
                    continue;
                }
                CommandParser.inputParser(d_gameManager, l_inputCommand);
                l_inputCommand = l_reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}