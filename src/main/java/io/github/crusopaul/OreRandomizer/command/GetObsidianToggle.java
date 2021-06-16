package io.github.crusopaul.OreRandomizer.command;

import io.github.crusopaul.OreRandomizer.listener.OreListener;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class GetObsidianToggle implements CommandExecutor, TabCompleter {
  public GetObsidianToggle(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
  }

  private OreListener oreListener;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.GetObsidianToggle") || !(sender instanceof Player)) {
      final boolean toggle = validityCheckAndErrorMessage(sender, args);

      sender.sendMessage(
          ChatColor.BLUE
              + "Ore randomization is occuring upon obsidian creation: "
              + Boolean.toString(toggle)
              + ".");

      ret = true;
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
      ret = false;
    }

    return ret;
  }

  private boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    return this.oreListener.getRandomizeOnObsidianCreation();
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    return new ArrayList<String>();
  }
}
