package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Continent;
import models.Country;
import models.Map;

public class ConquestMapFileReaderAdapterTest {

    private ConquestMapFileReaderAdapter d_adapter;

    @BeforeEach
    public void setUp() {
        d_adapter = new ConquestMapFileReaderAdapter();
    }
    
    @Test
    public void testLoadMap_ValidFile() {
        String l_validFileName = "src/test/resources/conquestTestMap.txt"; // Set a valid file path
        try {
            Map l_map = d_adapter.loadMap(l_validFileName);
            assertNotNull(l_map, "Map should not be null for a valid file");
        } catch (Exception l_e) {
            fail("Exception should not be thrown for a valid file");
        }
    }

    @Test
    public void testLoadMap_InvalidFile() {
        String l_invalidFileName = "invalidFileName"; // Set an invalid file path
        assertThrows(IOException.class, () -> d_adapter.loadMap(l_invalidFileName), "IOException should be thrown for an invalid file");
    }

    @Test
    public void testSaveMap_ValidMap() throws IOException {
        Map l_validMap = createValidMapForTesting();
        String l_fileNameToSave = "src/test/resources/conquestSaveTestMap.txt"; // Set an appropriate output file path
        boolean l_result = d_adapter.saveMap(l_validMap, l_fileNameToSave);
        assertTrue(l_result, "Map should be saved successfully for a valid map");
    }

    @Test
    public void testSaveMap_InvalidMap() {
        Map l_invalidMap = new Map(); // Generate an invalid map
        String l_fileNameToSave = "src/test/resources/conquestTestMap.txt"; // Set an appropriate output file path
        assertThrows(IOException.class, () -> d_adapter.saveMap(l_invalidMap, l_fileNameToSave), "IOException should be thrown for an invalid map");
    }

    private Map createValidMapForTesting() {
        Map l_validMap = new Map();

        // Create and add continents
        Continent l_asia = new Continent();
        l_asia.setD_continentID(1);
        l_asia.setD_continentName("Asia");
        l_asia.setD_continentValue(5);
        l_validMap.getD_continentMapGraph().addVertex(l_asia);

        Continent l_europe = new Continent();
        l_europe.setD_continentID(2);
        l_europe.setD_continentName("Europe");
        l_europe.setD_continentValue(3);
        l_validMap.getD_continentMapGraph().addVertex(l_europe);

        // Create and add countries
        Country l_japan = new Country();
        l_japan.setD_countryID(1);
        l_japan.setD_countryName("Japan");
        l_japan.setD_continentID(l_asia.getD_continentID());
        l_validMap.getD_countryMapGraph().addVertex(l_japan);

        Country l_germany = new Country();
        l_germany.setD_countryID(2);
        l_germany.setD_countryName("Germany");
        l_germany.setD_continentID(l_europe.getD_continentID());
        l_validMap.getD_countryMapGraph().addVertex(l_germany);

        Country l_china = new Country();
        l_china.setD_countryID(3);
        l_china.setD_countryName("China");
        l_china.setD_continentID(l_asia.getD_continentID());
        l_validMap.getD_countryMapGraph().addVertex(l_china);

        // Set neighboring relationships for countries
        l_japan.getD_neighbourCountryIDList().add(l_germany.getD_countryID());
        l_japan.getD_neighbourCountryIDList().add(l_china.getD_countryID());
        l_germany.getD_neighbourCountryIDList().add(l_japan.getD_countryID());
        l_germany.getD_neighbourCountryIDList().add(l_china.getD_countryID());
        l_china.getD_neighbourCountryIDList().add(l_japan.getD_countryID());
        l_china.getD_neighbourCountryIDList().add(l_germany.getD_countryID());

        // Add edges to the graph to set neighboring relationships
        l_validMap.getD_countryMapGraph().addEdge(l_japan, l_germany);
        l_validMap.getD_countryMapGraph().addEdge(l_japan, l_china);
        l_validMap.getD_countryMapGraph().addEdge(l_germany, l_japan);
        l_validMap.getD_countryMapGraph().addEdge(l_germany, l_china);
        l_validMap.getD_countryMapGraph().addEdge(l_china, l_japan);
        l_validMap.getD_countryMapGraph().addEdge(l_china, l_germany);

        return l_validMap;
    }

}
