package util;

import models.Map;

public interface IMapUtil {
    public void loadMap(String p_filename);

    public void editMap();

    public void saveMap();

    public Boolean validateMap(Map p_graphMap);

    public void showMap();

}
