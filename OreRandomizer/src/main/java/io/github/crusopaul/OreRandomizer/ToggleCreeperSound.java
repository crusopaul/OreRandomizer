package io.github.crusopaul.OreRandomizer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCreeperSound implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.ToggleCreeperSound") || !(sender instanceof Player)) {
      sender.sendMessage(
          ChatColor.RED
              + "This command has been deprecated, use /GetRandomizationSound or /SetRandomizationSound.");

      ret = true;
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

      ret = false;
    }

    return ret;
  }
}
