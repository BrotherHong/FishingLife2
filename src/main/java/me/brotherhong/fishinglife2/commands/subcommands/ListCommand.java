package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.SubCommand;
import me.brotherhong.fishinglife2.fishing.FishingRegion;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class ListCommand extends SubCommand {

    public ListCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "List all fishing region.";
    }

    @Override
    public String getUsage() {
        return "/fl list";
    }

    @Override
    public boolean hasPermission(Player p) {
        return p.hasPermission(Permissions.OP.getPerm());
    }

    @Override
    public void execute(Player p, String[] args) {
        // send list
        List<FishingRegion> regions = regionManager.getRegions();
        int amount = regions.size();
        Messages.LIST_TITLE.send(p, String.valueOf(amount));
        for (FishingRegion region : regions) {
            // hover text
            Location tp = region.getTpLocation();
            Text hoverText = new Text(ChatColor.GRAY + "World: " + region.getWorldName() +
                    "\nLocation: " + tp.getBlockX() + " " + tp.getBlockY() + " " + tp.getBlockZ());
            // main text
            TextComponent text = new TextComponent(" - " + region.getName());
            text.setColor(ChatColor.GRAY);
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fl tp " + region.getName()));
            p.spigot().sendMessage(text);
        }
    }

    @Override
    public List<String> getTabComplete(String[] args) {
        return null;
    }
}
