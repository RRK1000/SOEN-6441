package util;

import models.Map;

public interface IMapUtil {
    Map loadMap(String p_filename);

    Map editMap(String p_filename);

    Boolean saveMap(Map p_map);

    Boolean isValidMap(Map p_map);

    void showMap(Map p_map);

    void addContinent(Map p_map, int p_continentID, int p_continentValue);

    void removeContinent(Map p_map, int p_continentID);

    void addCountry(Map p_map, int p_countryID, int p_continentID);

    void removeCountry(Map p_map, int p_countryID);

    void addNeighbour(Map p_map, int p_countryID, int p_neighbourCountryID);

    void removeNeighbour(Map p_map, int p_countryID, int p_neighbourCountryID);

}
