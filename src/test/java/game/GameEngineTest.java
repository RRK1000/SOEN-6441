package game;

import controller.CommandParser;
import controller.GameManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

class GameEngineTest {
    static GameManager d_gameManager;

    @BeforeAll
    static void setUp() {
        d_gameManager = new GameManager();
    }

    @AfterAll
    static void tearDown() {
        d_gameManager = null;
    }

    @Test
    void ScenarioTest1() {
        GameManager d_gameManager = new GameManager();

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
     * The purpose of this test case is to ensure that when the user inputs
     * "exit" the game loop exists.
     */
    @Test
    void testGameLoopExit(){
        String input = "exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        GameEngine.main(new String[0]);
    }
}