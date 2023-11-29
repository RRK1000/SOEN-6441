package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Map;

/**
 * Tests the functionality of the DominationMapFileReader class
 * @author Yusuke Ishii
 */
public class DominationMapFileReaderTest {

    private DominationMapFileReader d_reader;

    /**
     * Initializes the DominationMapFileReader object before each test
     */
    @BeforeEach
    public void setUp() {
        d_reader = new DominationMapFileReader();
    }

    /**
     * Tests if a valid map file is correctly loaded by DominationMapFileReader
     */
    @Test
    public void testLoadMap_ValidFile() {
        String l_validFileName = "europe.map"; // Set a valid file path
        try {
            Map l_map = d_reader.loadMap(l_validFileName);
            assertNotNull(l_map, "Map should not be null for a valid file");
        } catch (Exception l_e) {
            fail("Exception should not be thrown for a valid file");
        }
    }

    /**
     * Tests if the saveMap method correctly returns false when trying to save an invalid map
     */
    @Test
    public void testSaveMap_InvalidMap() throws IOException {
        Map l_invalidMap = new Map(); // Generate an invalid map
        String l_fileNameToSave = "src/test/resources/invalidDominationMap.map";
        boolean l_result = d_reader.saveMap(l_invalidMap, l_fileNameToSave);
        assertFalse(l_result, "Should return false for an invalid map");
    }

    /**
     * Tests if a valid map is correctly saved by DominationMapFileReader
     */
    @Test
    public void testSaveMap_ValidFile() {
        String l_fileNameToLoad = "europe.map"; 
        Map l_loadedMap;
        try {
            l_loadedMap = d_reader.loadMap(l_fileNameToLoad);
        } catch (IOException e) {
            fail("Exception should not be thrown while loading a valid file");
            return;
        }
        
        String l_fileNameToSave = "savedMapForTestSaveMap_ValidFile";
        boolean l_result;
        try {
            l_result = d_reader.saveMap(l_loadedMap, l_fileNameToSave);
            assertTrue(l_result, "Map should be saved successfully");
        } catch (IOException l_e) {
            fail("Exception should not be thrown while saving the map");
        }
    }
}
