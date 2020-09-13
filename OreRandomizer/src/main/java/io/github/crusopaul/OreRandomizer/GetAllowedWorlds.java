package io.github.crusopaul.OreRandomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

class GetAllowedWorlds implements CommandExecutor, TabCompleter {
  GetAllowedWorlds(OreListener oreListenerToSet) {
    this.allowedWorlds = oreListenerToSet.GetAllowedWorlds();
  }

  private List<String> allowedWorlds;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.GetAllowedWorld") || !(sender instanceof Player)) {
      if (args.length > 0) {
        final String worldSpecifier = validityCheckAndErrorMessage(sender, args);

        if (!worldSpecifier.isEmpty()) {
          boolean worldActive = false;

          for (int i = 0; i < allowedWorlds.size(); i++) {
            if (worldSpecifier.equals(allowedWorlds.get(i))) {
              worldActive = true;
              break;
            }
          }

          if (worldActive) {
            sender.sendMessage(
                ChatColor.BLUE + "OreRandomizer is active in " + worldSpecifier + ".");
          } else {
            sender.sendMessage(
                ChatColor.BLUE + "OreRandomizer is inactive in " + worldSpecifier + ".");
          }

          ret = true;
        } else {
          ret = false;
        }
      } else {
        sender.sendMessage(ChatColor.BLUE + "OreRandomizer is active in worlds:");

        for (int i = 0; i < allowedWorlds.size(); i++) {
          sender.sendMessage(ChatColor.BLUE + allowedWorlds.get(i));
        }

        ret = true;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
      ret = false;
    }

    return ret;
  }

  private String validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    String validWorldReference = "";

    if (args.length > 0) {
      final String normalizedWorldSpecifier = args[0].toLowerCase();
      String worldReference;
      String normalizedWorldReference;

      for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
        worldReference = Bukkit.getWorlds().get(i).getName();
        normalizedWorldReference = worldReference.toLowerCase();

        if (normalizedWorldReference.equals(normalizedWorldSpecifier)) {
          validWorldReference = worldReference;
          break;
        }
      }

      if (validWorldReference.isEmpty()) {
        sender.sendMessage(ChatColor.RED + "No such world \"" + args[0] + "\".");
      }
    }

    return validWorldReference;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    final List<String> completionList = new ArrayList<String>();

    if (args.length == 1) {
      StringUtil.copyPartialMatches(args[0], getWorldNames(), completionList);
      Collections.sort(completionList);
    }

    return completionList;
  }

  private List<String> getWorldNames() {
    final List<String> ret = new ArrayList<String>();

    for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
      ret.add(Bukkit.getWorlds().get(i).getName());
    }

    return ret;
  }
}
