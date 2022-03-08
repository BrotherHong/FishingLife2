package me.brotherhong.fishinglife2.listeners.input;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.fishing.FishingDrop;
import me.brotherhong.fishinglife2.fishing.FishingRegion;
import me.brotherhong.fishinglife2.listeners.ListenerTemplate;
import me.brotherhong.fishinglife2.menu.MenuManager;
import me.brotherhong.fishinglife2.menu.PlayerMenuUtility;
import me.brotherhong.fishinglife2.menu.menus.EditMenu;
import me.brotherhong.fishinglife2.utils.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputHandler extends ListenerTemplate {

    private static final Map<UUID, InputType> waiting = new HashMap<>();

    public InputHandler(FishingLife2 plugin) {
        super(plugin);
    }

    public static void handleInputQuery(Player p, InputType type) {
        waiting.put(p.getUniqueId(), type);
    }

    @EventHandler
    public void onPlayerInput(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!waiting.containsKey(p.getUniqueId())) return;
        e.setCancelled(true);

        InputType type = waiting.get(p.getUniqueId());
        PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(p);
        int clickedSlot = playerMenuUtility.getClickedSlot();
        FishingRegion region = regionManager.getRegion(playerMenuUtility.getRegionName());
        FishingDrop target = region.getDrops().get(clickedSlot);
        String input = e.getMessage();

        waiting.remove(p.getUniqueId());

        if (input.equals("-")) {
            Messages.CANCEL_EDIT.send(p);
            reopenMenu(playerMenuUtility);
            return;
        }

        if (type == InputType.CHANCE) {
            // check input
            if (!input.matches(TextUtil.POSITIVE_DOUBLE)) {
                Messages.WRONG_VALUE_CHANCE.send(p);
                reopenMenu(playerMenuUtility);
                return;
            }
            double newChance = Double.parseDouble(input);
            // set
            target.setChance(newChance);
            regionManager.save();
            // send message
            Messages.SUCCESS_EDIT_CHANCE.send(p, target.getDisplayName());
            reopenMenu(playerMenuUtility);
        } else if (type == InputType.AMOUNT) {
            // check input
            if (!input.matches(TextUtil.POSITIVE_INTEGER)) {
                Messages.WRONG_VALUE_AMOUNT.send(p);
                reopenMenu(playerMenuUtility);
                return;
            }
            int newAmount = Integer.parseInt(input);
            if (newAmount > target.getItem().getType().getMaxStackSize()) {
                Messages.AMOUNT_OVERFLOW.send(p);
                reopenMenu(playerMenuUtility);
                return;
            }
            // set
            target.getItem().setAmount(newAmount);
            regionManager.save();
            // send message
            Messages.SUCCESS_EDIT_AMOUNT.send(p, target.getDisplayName());
            reopenMenu(playerMenuUtility);
        }
    }

    private void reopenMenu(PlayerMenuUtility playerMenuUtility) {
        plugin.getServer().getScheduler().runTask(plugin, () -> new EditMenu(plugin, playerMenuUtility).open());
    }

}
