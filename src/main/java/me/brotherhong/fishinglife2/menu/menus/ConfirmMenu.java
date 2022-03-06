package me.brotherhong.fishinglife2.menu.menus;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.fishing.FishingRegionManager;
import me.brotherhong.fishinglife2.menu.ConfirmType;
import me.brotherhong.fishinglife2.menu.Menu;
import me.brotherhong.fishinglife2.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class ConfirmMenu extends Menu {

    private Player p;
    private String region;
    private int targetSlot;
    private ConfirmType confirmType;

    public ConfirmMenu(FishingLife2 plugin, PlayerMenuUtility playerMenuUtility) {
        super(plugin, playerMenuUtility);
        this.p = playerMenuUtility.getOwner();
        this.region = playerMenuUtility.getRegionName();
        this.targetSlot = playerMenuUtility.getClickedSlot();
        this.confirmType = playerMenuUtility.getConfirmType();
    }

    @Override
    public String getMenuName() {
        return "Confirm";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleClick(InventoryClickEvent e) {
        switch (Objects.requireNonNull(e.getCurrentItem()).getType()) {
            case GREEN_WOOL:
                runDelete();
                break;
            case RED_WOOL:
                // cancel deletion
                Messages.CANCEL_DELETION.send(p);
                break;
        }
        p.closeInventory();
    }

    @Override
    public void setMenuItem() {
        ItemStack yes = new ItemStack(Material.GREEN_WOOL, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        assert yes_meta != null;
        yes_meta.setDisplayName(Messages.YES.get());
        yes.setItemMeta(yes_meta);

        ItemStack no = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta no_meta = no.getItemMeta();
        assert no_meta != null;
        no_meta.setDisplayName(Messages.NO.get());
        no.setItemMeta(no_meta);

        inventory.setItem(3, yes);
        inventory.setItem(5, no);
    }

    private void runDelete() {
        FishingRegionManager manager = plugin.getFishingRegionManager();
        if (confirmType == ConfirmType.DELETE_DROP) {
            String dropName = manager.getRegion(region).getDrops().get(targetSlot).getDisplayName();
            manager.getRegion(region).deleteDrop(targetSlot);
            manager.save();
            Messages.SUCCESS_DELETE_DROP.send(p, dropName);
        } else if (confirmType == ConfirmType.DELETE_REGION) {
            manager.deleteRegion(region);
            Messages.SUCCESS_DELETE_REGION.send(p, region);
        }
    }
}
