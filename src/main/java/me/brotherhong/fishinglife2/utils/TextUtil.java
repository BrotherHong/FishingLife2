package me.brotherhong.fishinglife2.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TextUtil {

    public static String trans(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String fromMaterial(Material material) {
        String str = material.name();
        str = str.toLowerCase().replaceAll("_", " ");
        str = WordUtils.capitalizeFully(str);
        return str;
    }
}
