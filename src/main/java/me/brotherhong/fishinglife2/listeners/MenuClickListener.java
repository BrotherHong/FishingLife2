package me.brotherhong.fishinglife2.listeners;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuClickListener extends ListenerTemplate {

    public MenuClickListener(FishingLife2 plugin) {
        super(plugin);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getClickedInventory() == null) return;
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.handleClick(e);
        }
    }
}
