package io.github.crusopaul.OreRandomizer.command;

import io.github.crusopaul.OreRandomizer.list.RandomizedMaterialList;
import io.github.crusopaul.OreRandomizer.listener.OreListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class GetOreRatio implements CommandExecutor, TabCompleter {
  public GetOreRatio(OreListener oreListenerToSet) {
    this.materialList = oreListenerToSet.materialList;
  }

  private RandomizedMaterialList materialList;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.SetOreRatio") || !(sender instanceof Player)) {
      if (args.length > 0) {
        final int oreIndex = validityCheckAndErrorMessage(sender, args);

        if (oreIndex != -1) {
          sender.sendMessage(
              ChatColor.BLUE
                  + this.materialList.list[oreIndex].getName()
                  + ": "
                  + this.materialList.list[oreIndex].getRatio());

          ret = true;
        } else {
          ret = false;
        }
      } else {
        for (int i = 0; i < this.materialList.list.length; i++) {
          sender.sendMessage(
              ChatColor.BLUE
                  + this.materialList.list[i].getName()
                  + ": "
                  + this.materialList.list[i].getRatio());
        }

        ret = true;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

      ret = false;
    }

    return ret;
  }

  private int validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    int validOreReference = -1;

    if (args.length > 0) {
      final String normalizedOreSpecifier = args[0].toLowerCase();

      for (int i = 0; i < this.materialList.list.length; i++) {
        if (normalizedOreSpecifier.equals(this.materialList.list[i].getNormalName())) {
          validOreReference = i;
          break;
        }
      }

      if (validOreReference == -1) {
        sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid ore specifier.");
        sender.sendMessage(ChatColor.RED + "Valid ore specifiers are:");
        sender.sendMessage(ChatColor.RED + this.materialList.getLingualList());
      }
    }

    return validOreReference;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    final List<String> completionList = new ArrayList<String>();

    if (args.length == 1) {
      StringUtil.copyPartialMatches(args[0], getMaterialNames(), completionList);
      Collections.sort(completionList);
    }

    return completionList;
  }

  private List<String> getMaterialNames() {
    final List<String> ret = new ArrayList<String>();

    for (int i = 0; i < this.materialList.list.length; i++) {
      ret.add(this.materialList.list[i].getName());
    }

    return ret;
  }
}
