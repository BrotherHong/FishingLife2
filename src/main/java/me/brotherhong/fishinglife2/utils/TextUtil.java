package me.brotherhong.fishinglife2.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TextUtil {

    public final static String POSITIVE_DOUBLE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    public final static String POSITIVE_INTEGER = "^[1-9]+[0-9]*$";

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private static String fromMaterial(Material material) {
        String str = material.name();
        str = str.toLowerCase().replaceAll("_", " ");
        str = WordUtils.capitalizeFully(str);
        return str;
    }

    public static String getLocaleName(ItemStack item) {
        return fromMaterial(item.getType());
    }
}
