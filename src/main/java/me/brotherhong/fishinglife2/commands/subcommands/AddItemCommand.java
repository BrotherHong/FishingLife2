package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.commands.SubCommand;
import me.brotherhong.fishinglife2.fishing.FishingDrop;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AddItemCommand extends SubCommand {

    public AddItemCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "addItem";
    }

    @Override
    public String getDescription() {
        return "Adding an item to region's drop list.";
    }

    @Override
    public String getUsage() {
        return "/fl addItem <region> <chance> [broadcast]";
    }

    @Override
    public boolean hasPermission(Player p) {
        return p.hasPermission(Permissions.OP.getPerm());
    }

    @Override
    public void execute(Player p, String[] args) {
        // check syntax
        if (args.length < 3) {
            sendUsage(p);
            return;
        }

        String regionName = args[1];
        String chanceStr = args[2];
        String bcStr = (args.length == 4 ? args[3] : "false");

        // check region
        if (!regionManager.hasRegion(regionName)) {
            Messages.REGION_NOT_FOUND.send(p, regionName);
            return;
        }

        // check chance and broadcast
        if (!chanceStr.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$")) {
            Messages.WRONG_VALUE_CHANCE.send(p);
            return;
        }
        if (!bcStr.equals("true") && !bcStr.equals("false")) {
            Messages.WRONG_VALUE_BROADCAST.send(p);
            return;
        }
        double chance = Double.parseDouble(chanceStr);
        boolean broadcast = Boolean.parseBoolean(bcStr);

        // check item
        ItemStack item = p.getInventory().getItemInMainHand().clone();
        if (item.getType().isAir()) {
            Messages.ADD_AIR.send(p);
            return;
        }

        // add and save
        FishingDrop drop = new FishingDrop(item, chance, broadcast);
        regionManager.getRegion(regionName).addDrop(drop);
        regionManager.save();

        // send success
        Messages.SUCCESS_ADD_DROP.send(p, drop.getDisplayName(), regionName);
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
        } else if (args.length == 3) {
            result.add("<chance>");
            return result;
        } else if (args.length == 4) {
            List<String> v = new ArrayList<>();
            v.add("true"); v.add("false");
            for (String b : v) {
                if (b.startsWith(args[3])) {
                    result.add(b);
                }
            }
            return result;
        }
        return null;
    }
}
