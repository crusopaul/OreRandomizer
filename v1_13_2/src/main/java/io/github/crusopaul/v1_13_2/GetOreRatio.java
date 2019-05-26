package io.github.crusopaul.v1_13_2;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GetOreRatio implements CommandExecutor {

    private FileConfiguration config;

    GetOreRatio(OreListener oreListenerToSet) {

        this.config = oreListenerToSet.getConfigFile();

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

                if (this.config.getString("RandomSpawnRatios." + oreSpecifier) != null) {

                    sender.sendMessage(
                            ChatColor.BLUE +
                                    oreSpecifier + ": " +
                                    this.config.getInt("RandomSpawnRatios." + oreSpecifier)
                    );

                } else {

                    sender.sendMessage(ChatColor.RED + "The following configuration is missing:");
                    sender.sendMessage(ChatColor.RED + "RandomSpawnRatios." + oreSpecifier);

                }

            }
            else {

                sender.sendMessage(
                        ChatColor.BLUE +
                                "Cobblestone: " + this.config.getInt("RandomSpawnRatios.Cobblestone")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Coal: " + this.config.getInt("RandomSpawnRatios.Coal")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Diamond: " + this.config.getInt("RandomSpawnRatios.Diamond")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Emerald: " + this.config.getInt("RandomSpawnRatios.Emerald")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Gold: " + this.config.getInt("RandomSpawnRatios.Gold")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Iron: " + this.config.getInt("RandomSpawnRatios.Iron")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Lapis: " + this.config.getInt("RandomSpawnRatios.Lapis")
                );
                sender.sendMessage(
                        ChatColor.BLUE +
                                "Redstone: " + this.config.getInt("RandomSpawnRatios.Redstone")
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
