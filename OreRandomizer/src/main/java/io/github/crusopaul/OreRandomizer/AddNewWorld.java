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

class AddNewWorld implements CommandExecutor, TabCompleter {
  AddNewWorld(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
  }

  private OreListener oreListener;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.AddNewWorld") || !(sender instanceof Player)) {
      final String worldSpecifier = validityCheckAndErrorMessage(sender, args);

      if (!worldSpecifier.isEmpty()) {
        this.oreListener.AddNewWorld(worldSpecifier);
        this.oreListener.getConfigFile().set("AllowedWorlds", this.oreListener.GetAllowedWorlds());
        this.oreListener.saveConfigFile();
        sender.sendMessage(
            ChatColor.BLUE + "OreRandomizer is now active for " + worldSpecifier + ".");
        ret = true;
      } else {
        ret = false;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
      ret = false;
    }

    return ret;
  }

  private String validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    String worldReference = "";

    if (args.length == 1) {
      final String normalizedWorldSpecifier = args[0].toLowerCase();
      String normalizedWorldReference;

      for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
        normalizedWorldReference = Bukkit.getWorlds().get(i).getName().toLowerCase();

        if (normalizedWorldReference.equals(normalizedWorldSpecifier)) {
          worldReference = Bukkit.getWorlds().get(i).getName();
          break;
        }
      }

      if (!worldReference.isEmpty()) {
        boolean worldActive = false;

        for (int i = 0; i < this.oreListener.GetAllowedWorlds().size(); i++) {
          normalizedWorldReference = this.oreListener.GetAllowedWorlds().get(i);

          if (normalizedWorldReference.equals(normalizedWorldSpecifier)) {
            worldActive = true;
            break;
          }
        }

        if (worldActive) {
          sender.sendMessage(
              ChatColor.RED + "OreRandomizer is already active in " + worldReference + ".");
          worldReference = "";
        }
      } else {
        sender.sendMessage(ChatColor.RED + "No such world \"" + args[0] + "\".");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "/AddNewWorld takes one argument.");
    }

    return worldReference;
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
    String worldReference;
    String normalizedWorldReference;
    boolean worldActive;

    for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
      worldReference = Bukkit.getWorlds().get(i).getName();
      normalizedWorldReference = worldReference.toLowerCase();
      worldActive = false;

      for (int j = 0; j < this.oreListener.GetAllowedWorlds().size(); j++) {
        if (normalizedWorldReference.equals(
            this.oreListener.GetAllowedWorlds().get(j).toLowerCase())) {
          worldActive = true;
          break;
        }
      }

      if (!worldActive) {
        ret.add(worldReference);
      }
    }

    return ret;
  }
}
