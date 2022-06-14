package me.brotherhong.fishinglife2.menu.menus;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.fishing.FishingDrop;
import me.brotherhong.fishinglife2.listeners.input.InputHandler;
import me.brotherhong.fishinglife2.listeners.input.InputType;
import me.brotherhong.fishinglife2.menu.ConfirmType;
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
    private final String region;
    private final List<FishingDrop> drops;

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
        } else if (e.getSlot() == 49) {
            // nothing to do
        } else {
            int clickedSlot = e.getSlot() + page * maxItemPerPage;
            playerMenuUtility.setRegionName(region);
            playerMenuUtility.setClickedSlot(clickedSlot);
            switch (e.getClick()) {
                case LEFT: // Edit chance
                    Messages.INPUT_QUERY_CHANCE.send(p);
                    InputHandler.handleInputQuery(p, InputType.CHANCE);
                    p.closeInventory();
                    break;
                case MIDDLE: // Edit amount
                    Messages.INPUT_QUERY_AMOUNT.send(p);
                    InputHandler.handleInputQuery(p, InputType.AMOUNT);
                    p.closeInventory();
                    break;
                case RIGHT: // Delete
                    playerMenuUtility.setConfirmType(ConfirmType.DELETE_DROP);
                    new ConfirmMenu(plugin, playerMenuUtility).open();
                    break;
                case SHIFT_LEFT: // Edit broadcast
                    drops.get(clickedSlot).switchBroadcast();
                    regionManager.save();
                    super.open();
                    break;
                case SWAP_OFFHAND:
                    // Player would get the item if it's not cancel
                    e.setCancelled(true);
                    break;
            }
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
            lore.add(Messages.ITEM_DELETE.get());
            lore.add(Messages.EDIT_BROADCAST.get());

            meta.setLore(lore);
            drop.setItemMeta(meta);
            inventory.setItem(i, drop);
        }
    }
}
