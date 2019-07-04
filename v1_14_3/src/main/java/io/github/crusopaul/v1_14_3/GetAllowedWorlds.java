package io.github.crusopaul.v1_14_3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class GetAllowedWorlds implements CommandExecutor {

    private FileConfiguration config;

    GetAllowedWorlds(OreListener oreListenerToSet) {

        this.config = oreListenerToSet.getConfigFile();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.GetAllowedWorld") || !(sender instanceof Player)) {

            if (!validityCheckAndErrorMessage(sender, args)) {

                return false;

            }

            List<String> allowedWorlds = this.config.getStringList("AllowedWorlds");

            if (args.length > 0) {

                String normalizedWorldReference = args[0].toLowerCase();
                String normalizedAllowedWorldReference;
                boolean foundActiveWorld = false;
                String foundWorldName = "";

                for (int i = 0; i < allowedWorlds.size(); i++) {

                    normalizedAllowedWorldReference = allowedWorlds.get(i).toLowerCase();

                    if (normalizedAllowedWorldReference.equals(normalizedWorldReference)) {

                        foundWorldName = allowedWorlds.get(i);
                        foundActiveWorld = true;

                    }

                }

                if (foundActiveWorld) {

                    sender.sendMessage(ChatColor.BLUE + "OreRandomizer is active in World \"" + foundWorldName + "\".");

                }
                else {

                    sender.sendMessage(ChatColor.BLUE + "OreRandomizer is inactive in World \"" + args[0] + "\".");

                }


            }
            else {

                sender.sendMessage(ChatColor.BLUE + "OreRandomizer is active in Worlds:");

                for (int i = 0; i < allowedWorlds.size(); i++) {

                    sender.sendMessage(ChatColor.BLUE + allowedWorlds.get(i));

                }

            }

        }
        else {

            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

        }

        return true;

    }

    public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

        String normalizedWorldReference;
        boolean worldExists = false;

        if (args.length > 0) {

            normalizedWorldReference = args[0].toLowerCase();
            String normalizedAvailableWorldReference;

            for (int i = 0; i < Bukkit.getWorlds().size(); i++) {

                normalizedAvailableWorldReference = Bukkit.getWorlds().get(i).getName().toLowerCase();

                if (normalizedAvailableWorldReference.equals(normalizedWorldReference)) {

                    worldExists = true;

                }

            }

            if (!worldExists) {

                sender.sendMessage(ChatColor.RED + "No such world \"" + args[0] + "\".");

            }

            return worldExists;

        }

        return true;

    }

}
