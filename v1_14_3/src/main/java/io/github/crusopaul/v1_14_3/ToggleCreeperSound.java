package io.github.crusopaul.v1_14_3;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCreeperSound implements CommandExecutor {

    private OreListener oreListener;

    ToggleCreeperSound(OreListener oreListenerToSet) {

        this.oreListener = oreListenerToSet;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.ToggleCreeperSound") || !(sender instanceof Player)) {

            this.oreListener.getConfigFile().set(
                    "RandomizationSound.PlayCreeperPrimingSound",
                    !this.oreListener.getConfigFile().getBoolean("RandomizationSound.PlayCreeperPrimingSound")
            );
            this.oreListener.ToggleCreeperSound();
            this.oreListener.saveConfigFile();
            sender.sendMessage( "Creeper sound toggled.");

        }
        else {

            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

        }

        return true;

    }

}