package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.commands.SubCommand;
import me.brotherhong.fishinglife2.fishing.FishingRegion;
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
        // check syntax
        if (args.length < 2) {
            sendUsage(p);
            return;
        }

        // check region
        String regionName = args[1];
        FishingRegion region = regionManager.getRegion(regionName);
        if (region == null) {
            Messages.REGION_NOT_FOUND.send(p, regionName);
            return;
        }

        // show boundary
        final int task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, region::displayBoundary, 0, 5);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> plugin.getServer().getScheduler().cancelTask(task), 60);
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
