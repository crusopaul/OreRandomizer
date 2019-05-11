package io.github.crusopaul.OreRandomizer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCreeperSound implements CommandExecutor {

    private OreRandomizer oreRandomizer;

    ToggleCreeperSound(OreRandomizer oreRandomizerToSet) {

        this.oreRandomizer = oreRandomizerToSet;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.ToggleCreeperSound") || !(sender instanceof Player)) {
            this.oreRandomizer.getListener().ToggleCreeperSound();
        }
        else {
            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
        }

        return true;

    }

}
