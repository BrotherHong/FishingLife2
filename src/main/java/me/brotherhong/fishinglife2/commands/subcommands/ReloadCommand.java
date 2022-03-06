package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin.";
    }

    @Override
    public String getUsage() {
        return "/fl reload";
    }

    @Override
    public boolean hasPermission(Player p) {
        return p.hasPermission(Permissions.OP.getPerm());
    }

    @Override
    public void execute(Player p, String[] args) {
        plugin.getConfigManager().reloadFiles();
        Messages.RELOAD.send(p);
    }

    @Override
    public List<String> getTabComplete(String[] args) {
        return null;
    }
}
