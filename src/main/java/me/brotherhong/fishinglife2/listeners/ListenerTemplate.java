package me.brotherhong.fishinglife2.listeners;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.fishing.FishingRegionManager;
import org.bukkit.event.Listener;

public class ListenerTemplate implements Listener {

    protected FishingLife2 plugin;
    protected FishingRegionManager regionManager;

    public ListenerTemplate(FishingLife2 plugin) {
        this.plugin = plugin;
        this.regionManager = plugin.getFishingRegionManager();
    }
}
