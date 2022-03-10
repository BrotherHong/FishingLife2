package me.brotherhong.fishinglife2.commands;

import me.brotherhong.fishinglife2.FishingLife2;
import me.brotherhong.fishinglife2.Messages;
import me.brotherhong.fishinglife2.commands.subcommands.*;
import me.brotherhong.fishinglife2.fishing.FishingRegion;
import me.brotherhong.fishinglife2.utils.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements TabExecutor {

    private FishingLife2 plugin;
    private static List<SubCommand> subCommands;

    public CommandManager(FishingLife2 plugin) {
        this.plugin = plugin;
        subCommands = new ArrayList<>();
    }

    public void loadCommand() {
        registerCommand(new AddItemCommand(plugin));
        registerCommand(new BoundaryCommand(plugin));
        registerCommand(new CreateCommand(plugin));
        registerCommand(new DeleteCommand(plugin));
        registerCommand(new EditCommand(plugin));
        registerCommand(new ListCommand(plugin));
        registerCommand(new ReloadCommand(plugin));
        registerCommand(new ReselectCommand(plugin));
        registerCommand(new ShowCommand(plugin));
        registerCommand(new TeleportCommand(plugin));
    }

    private void registerCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is not for console.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            sendHelp(p);
        } else {
            for (SubCommand subCommand : subCommands) {
                String cmdName = subCommand.getName();
                if (cmdName.equalsIgnoreCase(args[0])) {
                    if (!subCommand.hasPermission(p)) {
                        Messages.NO_PERMISSION.send(p);
                        return true;
                    }
                    subCommand.execute(p, args);
                    return true;
                }
            }
            sendHelp(p);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommand subCommand : subCommands) {
                String cmdName = subCommand.getName();
                if (cmdName.toLowerCase().startsWith(args[0])) {
                    result.add(cmdName);
                }
            }
            return result;
        } else if (args.length >= 2) {
            for (SubCommand subCommand : subCommands) {
                String cmdName = subCommand.getName();
                if (cmdName.equalsIgnoreCase(args[0])) {
                    return subCommand.getTabComplete(args);
                }
            }
        }
        return null;
    }

    private void sendHelp(Player p) {
        p.sendMessage(TextUtil.colorize("&b----------" + Messages.PREFIX.get() + "&b----------"));
        for (SubCommand subCommand : subCommands) {
            p.sendMessage(TextUtil.colorize("&b" + subCommand.getUsage() + "&7 - " + subCommand.getDescription()));
        }
    }

    public static List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public static List<String> getRegionNames() {
        return FishingLife2.getInstance().getFishingRegionManager().getRegions().stream().map(FishingRegion::getName).collect(Collectors.toList());
    }
}
