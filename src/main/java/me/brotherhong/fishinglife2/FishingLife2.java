package me.brotherhong.fishinglife2;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.configs.ConfigManager;
import me.brotherhong.fishinglife2.fishing.FishingRegionManager;
import me.brotherhong.fishinglife2.listeners.FishCaughtListener;
import me.brotherhong.fishinglife2.listeners.MenuClickListener;
import me.brotherhong.fishinglife2.listeners.input.InputHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FishingLife2 extends JavaPlugin {

    private static FishingLife2 instance;
    private static WorldEditPlugin worldEditPlugin;
    private ConfigManager configManager;
    private FishingRegionManager fishingRegionManager;
    private CommandManager commandManager;

    public FishingLife2() {
        configManager = new ConfigManager(this);
        fishingRegionManager = new FishingRegionManager(this);
        commandManager = new CommandManager(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        configManager.loadFiles();
        fishingRegionManager.loadRegions();
        commandManager.loadCommand();

        worldEditPlugin = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldEditPlugin == null) {
            getServer().getLogger().severe("WorldEdit is required to enable this plugin!");
            getServer().getLogger().severe("Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Objects.requireNonNull(getCommand("fishinglife")).setExecutor(commandManager);
        getServer().getPluginManager().registerEvents(new MenuClickListener(this), this);
        getServer().getPluginManager().registerEvents(new FishCaughtListener(this), this);
        getServer().getPluginManager().registerEvents(new InputHandler(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static WorldEditPlugin getWorldEdit() {
        return worldEditPlugin;
    }

    public static FishingLife2 getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public FishingRegionManager getFishingRegionManager() {
        return fishingRegionManager;
    }
}
