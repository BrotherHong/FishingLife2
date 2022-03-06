package me.brotherhong.fishinglife2.menu.menus;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.fishing.FishingDrop;
import me.brotherhong.fishinglife2.menu.PaginatedMenu;
import me.brotherhong.fishinglife2.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditMenu extends PaginatedMenu {

    private Player p;
    private String region;
    private List<FishingDrop> drops;

    public EditMenu(FishingLife2 plugin, PlayerMenuUtility playerMenuUtility) {
        super(plugin, playerMenuUtility);
        this.region = playerMenuUtility.getRegionName();
        this.drops = plugin.getFishingRegionManager().getRegion(region).getDrops();
        this.p = playerMenuUtility.getOwner();
        super.maxSize = drops.size();
    }

    @Override
    public String getMenuName() {
        return Messages.MENU_DISPLAY_NAME.get(region);
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        if (e.getSlot() == 45) {
            super.handlePageSwitch(-1);
        } else if (e.getSlot() == 53) {
            super.handlePageSwitch(1);
        }

    }

    @Override
    public void setMenuItem() {
        super.setPageUtils();
        for (int i = 0;i < super.maxItemPerPage;i++) {
            index = super.maxItemPerPage * page + i;
            if (index >= drops.size()) break;

            FishingDrop fd = drops.get(index);
            ItemStack drop = fd.getItem().clone();
            ItemMeta meta = drop.getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            double chance = fd.getChance();
            boolean broadcast = fd.needBroadcast();

            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(Messages.ITEM_CHANCE.get(Double.toString(chance)));
            lore.add(Messages.ITEM_BROADCAST.get((broadcast ? Messages.YES.get() : Messages.NO.get())));
            lore.add(Messages.EDIT_CHANCE.get());
            lore.add(Messages.EDIT_AMOUNT.get());
            lore.add(Messages.EDIT_BROADCAST.get());
            lore.add(Messages.ITEM_DELETE.get());

            meta.setLore(lore);
            drop.setItemMeta(meta);
            inventory.setItem(i, drop);
        }
    }
}
