package controller;
import static controller.GameEngine.d_playerList;
import static models.Map.d_map;

import models.Continent;
import models.Country;
import models.Player;
public class AssignReinforcements {
/**
 * check if enough armies are
 * left to be assigned for the player
 */

public static boolean check_armies(){
    for(Player l_player : d_playerList){
        if(l_player.getD_Armies()!=0){
            return false;
        }
    }
    return true;
}
    public static void assignArmies() {
        for (Player l_player : d_playerList) {
            int l_ownedCountries = l_player.getAssignedCountries().size();
            int l_ArmyCount, l_flag;
            String l_playerName = l_player.getPlayerName();

            l_ArmyCount = Math.max((l_ownedCountries / 3), 3);

            for (Continent l_continent : d_map.getD_Continents()) {
                l_flag = 0;
                for (Country l_country : l_continent.getCountries()) {
                    if (!l_country.getD_Player().getD_PlayerName().equals(l_playerName)) {
                        l_flag = 1;
                        break;
                    }
                }
                if (l_flag == 0) {
                    l_ArmyCount += l_continent.getContinentValue();
                }
            }

            l_player.setD_Armies(l_player.getD_Armies() + l_ArmyCount);
            l_player.d_armiesForNextCountry = l_player.getD_Armies();
        }
    }

}
