package me.brotherhong.fishinglife2.configs;

import me.brotherhong.fishinglife2.FishingLife2;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private FishingLife2 plugin;

    private Map<ConfigType, ConfigHandler> configs;

    public ConfigManager(FishingLife2 plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
    }

    public void loadFiles() {
        configs.put(ConfigType.REGION, new ConfigHandler(plugin, "region"));
        configs.put(ConfigType.MESSAGES, new ConfigHandler(plugin, "messages"));

        configs.values().forEach(ConfigHandler::saveDefaultConfig);
    }

    public void reloadFiles() {
        configs.values().forEach(ConfigHandler::reload);
    }

    public ConfigHandler getConfigHandler(ConfigType type) {
        return configs.get(type);
    }


}
