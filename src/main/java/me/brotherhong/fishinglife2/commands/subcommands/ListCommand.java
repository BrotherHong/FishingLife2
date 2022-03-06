package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.SubCommand;
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

    }

    @Override
    public List<String> getTabComplete(String[] args) {
        return null;
    }
}
