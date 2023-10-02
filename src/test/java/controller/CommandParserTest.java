package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
    GameManager d_gameManager;
    @BeforeEach
    void setUp() {
        d_gameManager = new GameManager();
    }

    /**
     * Test switch case "loadmap". Checks whether map object is set,
     * and the country graph is not empty
     *
     */
    @Test
    void inputParser1() {
        String l_input = "loadmap src/test/resources/validMap2.txt";
        CommandParser.inputParser(d_gameManager, l_input);
        assertNotNull(d_gameManager.getD_map());
        assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
    }

    /**
     * Test switch case "editmap" on a valid map file. Checks whether map object is set,
     * and the country graph is not empty
     *
     */
    @Test
    void inputParse2() {
        String l_input = "editmap src/test/resources/validMap2.txt";
        CommandParser.inputParser(d_gameManager, l_input);
        assertNotNull(d_gameManager.getD_map());
        assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
    }

    /**
     * Test switch case "editmap" on an invalid file. Checks whether map object is set,
     * and the country graph is not empty
     *
     */
    @Test
    void inputParse3() {
        String l_input = "editmap src/test/resources/InvalidMap1.txt";
        CommandParser.inputParser(d_gameManager, l_input);
        assertNotNull(d_gameManager.getD_map());
        assertNotEquals(0, d_gameManager.getD_map().getD_countryMapGraph().vertexSet().size());
    }
}