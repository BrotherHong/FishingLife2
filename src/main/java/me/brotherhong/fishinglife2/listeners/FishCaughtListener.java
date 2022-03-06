package me.brotherhong.fishinglife2.listeners;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.fishing.FishingDrop;
import me.brotherhong.fishinglife2.fishing.FishingRegion;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

public class FishCaughtListener extends ListenerTemplate {

    public FishCaughtListener(FishingLife2 plugin) {
        super(plugin);
    }

    @EventHandler
    public void onFishCaught(PlayerFishEvent e) {
        if (e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        if (e.getCaught() == null || !(e.getCaught() instanceof Item)) return;
        Player p = e.getPlayer();

        // which fishing region
        FishingRegion region = regionManager.getInside(e.getCaught().getLocation());
        if (region == null) { // not inside fishing region
            return;
        }

        // get / set item
        FishingDrop reward = region.getReward();
        Item drop = (Item) e.getCaught();
        drop.setItemStack(reward.getItem());

        // send / broadcast message
        Messages.CAUGHT_MESSAGE.send(p, reward.getDisplayName());
        if (reward.needBroadcast()) {
            Messages.BROADCAST_MESSAGE.broadcast(p.getDisplayName(), reward.getDisplayName(), region.getName());
        }
    }
}
