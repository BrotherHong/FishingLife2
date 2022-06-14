package me.brotherhong.fishinglife2.commands.subcommands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.regions.Region;
import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.commands.SubCommand;
import me.brotherhong.fishinglife2.fishing.FishingRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReselectCommand extends SubCommand {

    public ReselectCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "reselect";
    }

    @Override
    public String getDescription() {
        return "Reselect a fishing region";
    }

    @Override
    public String getUsage() {
        return "/fl reselect <region>";
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

        // check select
        Region selection;
        try {
            selection = FishingLife2.getWorldEdit().getSession(p).getSelection();
        } catch (IncompleteRegionException e) {
            Messages.SELECT_FIRST.send(p);
            return;
        }

        // execute
        region.setRegionPosition(selection);
        regionManager.save();
        region.displayBoundary();

        // send message
        Messages.SUCCESS_RESELECT.send(p, regionName);
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
