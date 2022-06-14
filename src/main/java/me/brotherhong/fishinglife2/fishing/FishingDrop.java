package me.brotherhong.fishinglife2.fishing;

import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.utils.TextUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FishingDrop {

    private ItemStack item;
    private double chance;
    private boolean broadcast;

    public FishingDrop(ItemStack item, double chance) {
        this(item, chance, false);
    }

    public FishingDrop(ItemStack item, double chance, boolean broadcast) {
        this.item = item;
        this.chance = chance;
        this.broadcast = broadcast;
    }

    public static FishingDrop fromSection(ConfigurationSection section) {
        return new FishingDrop(
                section.getItemStack("item"),
                section.getDouble("chance"),
                section.getBoolean("broadcast")
        );
    }

    public void setToSection(ConfigurationSection section) {
        section.set("chance", chance);
        section.set("broadcast", broadcast);
        section.set("item", item);
    }

    public String getDisplayName() {
        int amount = item.getAmount();
        String displayName;
        assert item.getItemMeta() != null;
        if (item.getItemMeta().hasDisplayName()) {
            displayName = item.getItemMeta().getDisplayName();
        } else {
            displayName = TextUtil.getLocaleName(item);
        }
        String format = Messages.ITEM_FORMAT.get()
                .replaceAll("\\{display_name}", "%s")
                .replaceAll("\\{amount}", "%d");
        return TextUtil.colorize(String.format(format, displayName, amount));
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public boolean needBroadcast() {
        return broadcast;
    }

    public void switchBroadcast() {
        broadcast = !broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }
}
