package me.brotherhong.fishinglife2.commands.subcommands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.regions.Region;
import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateCommand extends SubCommand {

    public CreateCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a fishing region.";
    }

    @Override
    public String getUsage() {
        return "/fl create <name>";
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

        String regionName = args[1];
        String world = "world";
        Region region;

        // check if player has selected a region
        try {
            region = FishingLife2.getWorldEdit().getSession(p).getSelection();
            world = Objects.requireNonNull(region.getWorld()).getName();
        } catch (IncompleteRegionException e) {
            Messages.SELECT_FIRST.send(p);
            return;
        }

        // check if name is repeated
        if (regionManager.hasRegion(regionName)) {
            Messages.REGION_NAME_REPEAT.send(p, regionName);
            return;
        }

        // check if selected region is conflict with other existed region
        String conflict = regionManager.isRegionConflict(region);
        if (conflict != null) {
            Messages.REGION_CONFLICT.send(p, conflict);
            return;
        }

        // create a region
        regionManager.createRegion(regionName, region);

        // send success message
        Messages.SUCCESS_CREATE.send(p, regionName);
    }

    @Override
    public List<String> getTabComplete(String[] args) {
        List<String> result = new ArrayList<>();
        if (args.length == 2) {
            result.add("<name>");
            return result;
        }
        return null;
    }
}
