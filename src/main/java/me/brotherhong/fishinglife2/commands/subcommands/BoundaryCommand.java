package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BoundaryCommand extends SubCommand {

    public BoundaryCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "boundary";
    }

    @Override
    public String getDescription() {
        return "Show the region's boundary";
    }

    @Override
    public String getUsage() {
        return "/fl boundary <region>";
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
        List<String> result = new ArrayList<>();
        if (args.length == 2) {
            for (String name : CommandManager.getRegionNames()) {
                if (name.startsWith(args[1])) {
                    result.add(name);
                }
            }
            return result;
        }
        return null;
    }
}
