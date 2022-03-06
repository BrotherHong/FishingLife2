package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.commands.SubCommand;
import me.brotherhong.fishinglife2.menu.ConfirmType;
import me.brotherhong.fishinglife2.menu.MenuManager;
import me.brotherhong.fishinglife2.menu.PlayerMenuUtility;
import me.brotherhong.fishinglife2.menu.menus.ConfirmMenu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand extends SubCommand {

    public DeleteCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete a fishing region.";
    }

    @Override
    public String getUsage() {
        return "/fl delete <region>";
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

        // check region
        if (!regionManager.hasRegion(regionName)) {
            Messages.REGION_NOT_FOUND.send(p, regionName);
            return;
        }

        // send confirm
        PlayerMenuUtility menuUtility = MenuManager.getPlayerMenuUtility(p);
        menuUtility.setRegionName(regionName);
        menuUtility.setConfirmType(ConfirmType.DELETE_REGION);
        new ConfirmMenu(plugin, menuUtility).open();
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
