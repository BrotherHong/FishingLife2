package me.brotherhong.fishinglife2.menu;

import me.brotherhong.fishinglife2.FishingLife2;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MenuManager {

    private FishingLife2 plugin;
    private static final HashMap<Player, PlayerMenuUtility> map = new HashMap<>();

    public MenuManager(FishingLife2 plugin) {
        this.plugin = plugin;
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        if (!map.containsKey(p)) {
            map.put(p, new PlayerMenuUtility(p));
        }
        return map.get(p);
    }
}
