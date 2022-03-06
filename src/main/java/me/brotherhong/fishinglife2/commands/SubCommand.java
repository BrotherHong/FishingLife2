package me.brotherhong.fishinglife2.commands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.configs.ConfigHandler;
import me.brotherhong.fishinglife2.configs.ConfigType;
import me.brotherhong.fishinglife2.fishing.FishingRegionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    protected FishingLife2 plugin;
    protected ConfigHandler regionConfig;
    protected FishingRegionManager regionManager;

    public SubCommand(FishingLife2 plugin) {
        this.plugin = plugin;
        this.regionConfig = plugin.getConfigManager().getConfigHandler(ConfigType.REGION);
        this.regionManager = plugin.getFishingRegionManager();
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getUsage();
    public abstract boolean hasPermission(Player p);
    public abstract void execute(Player p, String[] args);
    public abstract List<String> getTabComplete(String[] args);

    public void sendUsage(Player p) {
        p.sendMessage(Messages.PREFIX.get() + ChatColor.RED + " Usage: "  + getUsage());
    }
}
