package me.brotherhong.fishinglife2.menu;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int maxItemPerPage = 45;
    protected int maxSize = 0;
    protected int index = 0;

    public PaginatedMenu(FishingLife2 plugin, PlayerMenuUtility playerMenuUtility) {
        super(plugin, playerMenuUtility);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    // type:
    // previous: -1 | next: 1 | current: 0
    protected void handlePageSwitch(int type) {
        Player p = playerMenuUtility.getOwner();
        if (type == -1) {
            if (page == 0) {
                Messages.NO_PREVIOUS_PAGE.send(p);
            } else {
                page = page - 1;
                super.open();
            }
        } else if (type == 1) {
            if ((index + 1) < maxSize) {
                page = page + 1;
                super.open();
            } else {
                Messages.NO_NEXT_PAGE.send(p);
            }
        }
    }

    protected void setPageUtils() {
        // current page
        ItemStack cur = new ItemStack(Material.COMPASS, Math.min(64, page+1));
        ItemMeta cur_meta = cur.getItemMeta();
        assert cur_meta != null;
        cur_meta.setDisplayName(Messages.CURRENT_PAGE.get(String.valueOf(Math.min(64, page+1))));
        cur.setItemMeta(cur_meta);
        inventory.setItem(49, cur);

        // arrow
        ItemStack back = new ItemStack(Material.ARROW, Math.max(1, page));
        ItemMeta back_meta = back.getItemMeta();
        assert back_meta != null;
        back_meta.setDisplayName(Messages.PREVIOUS_PAGE.get());
        back.setItemMeta(back_meta);
        inventory.setItem(45, back);

        ItemStack next = new ItemStack(Material.ARROW, Math.min(64, page+2));
        ItemMeta next_meta = next.getItemMeta();
        assert next_meta != null;
        next_meta.setDisplayName(Messages.NEXT_PAGE.get());
        next.setItemMeta(next_meta);
        inventory.setItem(53, next);
    }
}
