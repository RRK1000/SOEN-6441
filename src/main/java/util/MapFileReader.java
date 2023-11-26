package util;

import java.io.IOException;

import models.Map;

/**
 * Interface for map file reading and writing functionality
 * @author yusuke ishii
 */
public interface MapFileReader {

    /**
     * Loads a map from a specified file
     *
     * @param p_fileName The name of the file from which the map will be loaded
     * @return A Map object loaded from the file
     * @throws IOException If there is an issue in reading the file
     */
    Map loadMap(String p_fileName) throws IOException;

    /**
     * Saves a map to a specified file
     *
     * @param p_map The Map object to save
     * @param p_fileName The name of the file where the map will be saved
     * @return true if the map is saved successfully, false otherwise
     * @throws IOException If there is an issue in writing to the file
     */
    boolean saveMap(Map p_map, String p_fileName) throws IOException;
}
