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

public class TeleportCommand extends SubCommand {

    public TeleportCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return "Teleport to fishing region.";
    }

    @Override
    public String getUsage() {
        return "/fl tp <region>";
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

        // execute tp
        p.teleport(region.getTpLocation());

        // send message
        Messages.SUCCESS_TP.send(p, regionName);
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
