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
    	System.out.println("This file is Domination Format.");
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
    	
		if (p_fileName.endsWith(".conquest")) {
			p_fileName = p_fileName.substring(0, p_fileName.length() - 9) + ".domination";
		}

		else if (!p_fileName.endsWith(".domination")) {
			p_fileName += ".domination";
		} 
		
        return MapUtil.saveMap(p_map, p_fileName);
    }
}
