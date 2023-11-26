package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Map;

/**
 * Test class for ConquestMapFileReaderAdapter This class performs unit tests on
 * methods of ConquestMapFileReaderAdapter including loading valid and invalid
 * map files and saving map files
 * 
 * @author Yusuke Ishii
 */
public class ConquestMapFileReaderAdapterTest {

	private ConquestMapFileReaderAdapter d_adapter;

	/**
	 * Sets up the test environment before each test Initializes a new instance of
	 * ConquestMapFileReaderAdapter for testing
	 */
	@BeforeEach
	void setUp() {
		d_adapter = new ConquestMapFileReaderAdapter();
	}

	/**
	 * Tests loading a valid map file Ensures that the method correctly loads a map
	 * file and returns a non-null Map object
	 */
	@Test
	void testLoadMapValidFile() {
		try {
			Map l_map = d_adapter.loadMap("ConquestTestMap.txt");
			assertNotNull(l_map, "Map should not be null for a valid file");
		} catch (IOException e) {
			fail("IOException should not be thrown for a valid file");
		}
	}

	/**
	 * Tests saving a map file that has been successfully loaded from a valid file
	 * Validates that the save operation returns true indicating successful save
	 * operation
	 */
	@Test
	public void testSaveMap_ValidMapFromFile() throws IOException {

		String l_fileNameToLoad = "conquestTestMap.txt";
		Map l_loadedMap = d_adapter.loadMap(l_fileNameToLoad);
		assertNotNull(l_loadedMap, "Loaded map should not be null");

		String l_fileNameToSave = "conquestTestMap.conquest";
		boolean l_result = d_adapter.saveMap(l_loadedMap, l_fileNameToSave);
		assertTrue(l_result, "Map should be saved successfully for a valid map");
	}

	/**
	 * Tests loading a map file with an invalid file path Validates that the method
	 * throws an IOException for an invalid file path
	 */

	@Test
	public void testLoadMap_InvalidFile() {
		String l_invalidFileName = "invalidFileName"; // Set an invalid file path
		assertThrows(IOException.class, () -> d_adapter.loadMap(l_invalidFileName),
				"IOException should be thrown for an invalid file");
	}

	/**
	 * Tests saving an invalid map Ensures that the method does not save an invalid
	 * map and returns false
	 */
	@Test
	public void testSaveMap_InvalidMap() {
		Map l_invalidMap = new Map(); // Generate an invalid map
		String l_fileNameToSave = "null";
		boolean result = false;
		try {
			result = d_adapter.saveMap(l_invalidMap, l_fileNameToSave);
		} catch (IOException e) {
			fail("IOException should not be thrown");
		}
		assertFalse(result, "Method should return false for an invalid map");
	}

}
