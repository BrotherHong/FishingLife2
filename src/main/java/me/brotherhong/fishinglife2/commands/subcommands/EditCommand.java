package me.brotherhong.fishinglife2.commands.subcommands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.Permissions;
import me.brotherhong.fishinglife2.commands.CommandManager;
import me.brotherhong.fishinglife2.commands.SubCommand;
import me.brotherhong.fishinglife2.menu.MenuManager;
import me.brotherhong.fishinglife2.menu.PlayerMenuUtility;
import me.brotherhong.fishinglife2.menu.menus.EditMenu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EditCommand extends SubCommand {

    public EditCommand(FishingLife2 plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "Edit fishing region's drops.";
    }

    @Override
    public String getUsage() {
        return "/fl edit <region>";
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

        // open menu
        PlayerMenuUtility utility = MenuManager.getPlayerMenuUtility(p);
        utility.setRegionName(regionName);
        new EditMenu(plugin, utility).open();
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
