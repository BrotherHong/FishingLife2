package me.brotherhong.fishinglife2.configs;

import me.brotherhong.fishinglife2.FishingLife2;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    private FishingLife2 plugin;
    private String fileName;
    private File file;
    private FileConfiguration config;

    public ConfigHandler(FishingLife2 plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName + ".yml";
        this.file = new File(plugin.getDataFolder(), this.fileName);
        this.config = new YamlConfiguration();
    }

    public void save() {
        if (file == null || config == null)
            return;
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            plugin.getLogger().severe("Something wrong with " + fileName);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
