package util;

import java.io.IOException;

import models.Map;

/**
 * File reader for Domination map format
 * @author Yusuke Ishii
 */
public class DominationMapFileReader implements MapFileReader {

    /**
     * Loads a map from a specified Domination file
     * 
     * @param p_fileName Name of the file from which the map will be loaded
     * @return A Map object loaded from the file
     * @throws IOException If there is an issue in reading the file
     */
    @Override
    public Map loadMap(String p_fileName) throws IOException {
        return MapUtil.loadMap(p_fileName);
    }

    /**
     * Saves a map to a specified Domination file
     * If the filename ends with .conquest, it's replaced with .domination
     *
     * @param p_map      The Map object to be saved
     * @param p_fileName Name of the file where the map will be saved
     * @return true if the map is saved successfully, false otherwise
     * @throws IOException If there is an issue in writing to the file
     */
    @Override
    public boolean saveMap(Map p_map, String p_fileName) throws IOException {
        // Local variable to store modified filename if needed
        String l_fileNameModified = p_fileName;

        // Check if the filename ends with .conquest and replace it with .domination
        if (p_fileName.toLowerCase().endsWith(".conquest")) {
            l_fileNameModified = p_fileName.substring(0, p_fileName.length() - ".conquest".length()) + ".domination";
        }
        
        return MapUtil.saveMap(p_map, l_fileNameModified);
    }
}
