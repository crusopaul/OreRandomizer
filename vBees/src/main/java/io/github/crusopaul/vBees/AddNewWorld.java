package io.github.crusopaul.vBees;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddNewWorld implements CommandExecutor {

    private OreListener oreListener;

    AddNewWorld(OreListener oreListenerToSet) {

        this.oreListener = oreListenerToSet;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.AddNewWorld") || !(sender instanceof Player)) {

            if (!validityCheckAndErrorMessage(sender, args)) {

                return false;

            }

            List<World> worlds = Bukkit.getWorlds();
            String normalizedWorldReference = args[0].toLowerCase();
            String normalizedAvailableWorldReference;
            String worldName = "";

            for (int i = 0; i < worlds.size(); i++) {

                normalizedAvailableWorldReference = worlds.get(i).getName().toLowerCase();

                if (normalizedAvailableWorldReference.equals(normalizedWorldReference)) {

                    worldName = worlds.get(i).getName();

                }

            }

            sender.sendMessage(ChatColor.BLUE + "OreRandomizer is now active for World \"" + worldName + "\".");

            this.oreListener.AddNewWorld(worldName);
            this.oreListener.getConfigFile().set("AllowedWorlds", this.oreListener.GetAllowedWorlds());
            this.oreListener.saveConfigFile();

        }
        else {

            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

        }

        return true;

    }

    public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

        boolean validNumberOfArgs;
        String normalizedWorldReference;
        boolean worldExists = false;

        validNumberOfArgs = args.length == 1;

        if (validNumberOfArgs) {

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

            List<String> allowedWorlds = this.oreListener.GetAllowedWorlds();
            String normalizedAllowedWorldReference;
            boolean worldActive = false;
            String activeWorldName = "";

            for (int i = 0; i < allowedWorlds.size(); i++) {

                normalizedAllowedWorldReference = allowedWorlds.get(i);

                if (normalizedAllowedWorldReference.equals(normalizedWorldReference)) {

                    activeWorldName = allowedWorlds.get(i);
                    worldActive = true;

                }

            }

            if (worldActive) {

                sender.sendMessage(ChatColor.RED + "OreRandomizer is already active in World \"" + activeWorldName + "\".");
                return false;

            }

            return worldExists;

        }

        if (!validNumberOfArgs) {

            sender.sendMessage(ChatColor.RED + "/AddNewWorld takes one argument.");
            return false;

        }

        return validNumberOfArgs && worldExists;

    }

}