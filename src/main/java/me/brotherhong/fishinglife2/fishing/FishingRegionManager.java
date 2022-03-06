package me.brotherhong.fishinglife2.fishing;

import com.sk89q.worldedit.regions.Region;
import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.configs.ConfigHandler;
import me.brotherhong.fishinglife2.configs.ConfigType;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class FishingRegionManager {

    private final FishingLife2 plugin;

    private final Map<String, FishingRegion> regions;

    public FishingRegionManager(FishingLife2 plugin) {
        this.plugin = plugin;
        regions = new HashMap<>();
    }

    public void loadRegions() {
        FileConfiguration config = plugin.getConfigManager().getConfigHandler(ConfigType.REGION).getConfig();
        ConfigurationSection regionsSection = config.getConfigurationSection("regions");
        if (regionsSection == null) {
            regionsSection = config.createSection("regions");
        }
        for (String name : regionsSection.getKeys(false)) {
            addRegion(FishingRegion.fromSection(Objects.requireNonNull(regionsSection.getConfigurationSection(name))));
        }
    }

    public void save() {
        ConfigHandler configHandler = plugin.getConfigManager().getConfigHandler(ConfigType.REGION);
        configHandler.getConfig().set("regions", null);
        for (FishingRegion region : regions.values()) {
            ConfigurationSection section = configHandler.getConfig().getConfigurationSection("regions." + region.getName());
            if (section == null) {
                section = configHandler.getConfig().createSection("regions." + region.getName());
            }
            region.setToSection(section);
        }
        configHandler.save();
    }

    public boolean hasRegion(String name) {
        return (getRegion(name) != null);
    }

    public FishingRegion getRegion(String name) {
        if (!regions.containsKey(name)) {
            return null;
        }
        return regions.get(name);
    }

    public void deleteRegion(String region) {
        if (!regions.containsKey(region)) {
            return;
        }
        regions.remove(region);
        save();
    }

    public void addRegion(FishingRegion region) {
        regions.put(region.getName(), region);
        save();
    }

    public void createRegion(String regionName, Region selection) {
        addRegion(new FishingRegion(regionName, selection));
    }

    public String isRegionConflict(Region region) {
        if (getRegions().isEmpty()) {
            return null;
        }
        for (FishingRegion reg : getRegions()) {
            if (reg.isConflict(region)) {
                return reg.getName();
            }
        }
        return null;
    }

    public FishingRegion getInside(Location location) {
        for (FishingRegion region : getRegions()) {
            if (!region.getWorldName().equals(Objects.requireNonNull(location.getWorld()).getName()))
                continue;
            if (region.contains(location)) {
                return region;
            }
        }
        return null;
    }

    public List<FishingRegion> getRegions() {
        return new ArrayList<>(regions.values());
    }
}

/*
 * regions:
 *   name:
 *     world:
 *     pos1:
 *       x:
 *       y:
 *       z:
 *     pos2:
 *       x:
 *       y:
 *       z:
 *     drops:
 *       '0':
 *         chance:
 *         broadcast:
 *         item:
 * */
