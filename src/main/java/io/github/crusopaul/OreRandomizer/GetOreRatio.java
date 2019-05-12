package io.github.crusopaul.OreRandomizer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetOreRatio implements CommandExecutor {

    private OreRandomizer plugin;

    GetOreRatio(OreRandomizer pluginToSet) {

        this.plugin = pluginToSet;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.SetOreRatio") || !(sender instanceof Player)) {

            if (!validityCheckAndErrorMessage(sender, args)) {

                return false;

            }

            if (args.length > 0) {

                String oreSpecifier = args[0].substring(0, 1).toUpperCase() +
                    args[0].substring(1).toLowerCase();

                if (this.plugin.getConfig().getString("RandomSpawnRatios." + oreSpecifier) != null) {

                    sender.sendMessage(
                        ChatColor.BLUE +
                        oreSpecifier + ": " +
                        this.plugin.getConfig().getString("RandomSpawnRatios." + oreSpecifier)
                    );

                } else {

                    sender.sendMessage(ChatColor.RED + "The following configuration is missing:");
                    sender.sendMessage(ChatColor.RED + "RandomSpawnRatios." + oreSpecifier);

                }

            }
            else {

                sender.sendMessage(
                    ChatColor.BLUE +
                    "Cobblestone: " + this.plugin.getConfig().getString("RandomSpawnRatios.Cobblestone")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Coal: " + this.plugin.getConfig().getString("RandomSpawnRatios.Coal")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Diamond: " + this.plugin.getConfig().getString("RandomSpawnRatios.Diamond")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Emerald: " + this.plugin.getConfig().getString("RandomSpawnRatios.Emerald")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Gold: " + this.plugin.getConfig().getString("RandomSpawnRatios.Gold")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Iron: " + this.plugin.getConfig().getString("RandomSpawnRatios.Iron")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Lapis: " + this.plugin.getConfig().getString("RandomSpawnRatios.Lapis")
                );
                sender.sendMessage(
                    ChatColor.BLUE +
                    "Redstone: " + this.plugin.getConfig().getString("RandomSpawnRatios.Redstone")
                );

            }

        }
        else {

            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

        }

        return true;

    }

    public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

        boolean validOreReference = true;

        if (args.length > 0) {
            String normalizedOreSpecifier = args[0].toLowerCase();
            validOreReference = (
                normalizedOreSpecifier.equals("cobblestone") ||
                normalizedOreSpecifier.equals("coal") ||
                normalizedOreSpecifier.equals("diamond") ||
                normalizedOreSpecifier.equals("emerald") ||
                normalizedOreSpecifier.equals("gold") ||
                normalizedOreSpecifier.equals("iron") ||
                normalizedOreSpecifier.equals("lapis") ||
                normalizedOreSpecifier.equals("redstone")
            );
        }

        if (!validOreReference) {

            sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid ore specifier.");
            sender.sendMessage(ChatColor.RED + "Valid ore specifiers are:");
            sender.sendMessage(ChatColor.RED + "Cobblestone, Coal, Diamond, Emerald, Gold, Iron, Lapis, or Redstone");

        }

        return (validOreReference);

    }

}
