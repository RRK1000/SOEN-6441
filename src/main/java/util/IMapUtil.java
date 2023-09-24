package util;

import models.Map;

public interface IMapUtil {
    public Map loadMap(String p_filename);

    public Map editMap(String p_filename);

    public Boolean saveMap(Map p_map);

    public Boolean isValidMap(Map p_graphMap);

    public void showMap(Map p_graphMap);

}
