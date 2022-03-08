package me.brotherhong.fishinglife2.menu;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.fishing.FishingRegionManager;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    protected FishingLife2 plugin;
    protected FishingRegionManager regionManager;
    protected PlayerMenuUtility playerMenuUtility;

    protected Inventory inventory;

    public Menu(FishingLife2 plugin, PlayerMenuUtility playerMenuUtility) {
        this.plugin = plugin;
        this.playerMenuUtility = playerMenuUtility;
        this.regionManager = plugin.getFishingRegionManager();
    }

    public abstract String getMenuName();
    public abstract int getSlots();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        setMenuItem();
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    public abstract void handleClick(InventoryClickEvent e);
    public abstract void setMenuItem();

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
