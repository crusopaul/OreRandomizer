package io.github.crusopaul.OreRandomizer.command;

import io.github.crusopaul.OreRandomizer.listener.OreListener;
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

public class RemoveAllowedWorld implements CommandExecutor, TabCompleter {
  public RemoveAllowedWorld(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
  }

  private OreListener oreListener;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.RemoveAllowedWorld") || !(sender instanceof Player)) {
      final int worldIndex = validityCheckAndErrorMessage(sender, args);

      if (worldIndex != -1) {
        this.oreListener.RemoveAllowedWorld(worldIndex);
        this.oreListener.getConfigFile().set("AllowedWorlds", this.oreListener.GetAllowedWorlds());
        this.oreListener.saveConfigFile();
        sender.sendMessage(
            ChatColor.BLUE
                + "OreRandomizer is now inactive for "
                + Bukkit.getWorlds().get(worldIndex).getName()
                + ".");
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

  private int validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    int worldIndex = -1;

    if (args.length == 1) {
      final String normalizedWorldSpecifier = args[0].toLowerCase();
      String normalizedWorldReference;

      for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
        normalizedWorldReference = Bukkit.getWorlds().get(i).getName().toLowerCase();

        if (normalizedWorldReference.equals(normalizedWorldSpecifier)) {
          worldIndex = i;
          break;
        }
      }

      if (worldIndex == -1) {
        sender.sendMessage(ChatColor.RED + "No such world \"" + args[0] + "\".");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "/RemoveAllowedWorld takes one argument.");
    }

    return worldIndex;
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

    for (int i = 0; i < this.oreListener.GetAllowedWorlds().size(); i++) {
      ret.add(this.oreListener.GetAllowedWorlds().get(i));
    }

    return ret;
  }
}
