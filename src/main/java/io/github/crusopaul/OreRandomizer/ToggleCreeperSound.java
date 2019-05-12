package io.github.crusopaul.OreRandomizer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCreeperSound implements CommandExecutor {

    private OreRandomizer plugin;

    ToggleCreeperSound(OreRandomizer pluginToSet) {

        this.plugin = pluginToSet;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.ToggleCreeperSound") || !(sender instanceof Player)) {

            this.plugin.getConfig().set(
                "RandomizationSound.PlayCreeperPrimingSound",
                !this.plugin.getConfig().getBoolean("RandomizationSound.PlayCreeperPrimingSound")
            );
            this.plugin.getListener().ToggleCreeperSound();
            this.plugin.saveConfig();

        }
        else {

            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

        }

        return true;

    }

}
