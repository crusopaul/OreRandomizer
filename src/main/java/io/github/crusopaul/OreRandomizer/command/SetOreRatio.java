package io.github.crusopaul.OreRandomizer.command;

import io.github.crusopaul.OreRandomizer.exception.BadMaterialNodeException;
import io.github.crusopaul.OreRandomizer.exception.NegativeRatioException;
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

public class SetOreRatio implements CommandExecutor, TabCompleter {
  public SetOreRatio(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
    this.materialList = oreListenerToSet.materialList;
  }

  private OreListener oreListener;
  private RandomizedMaterialList materialList;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    boolean ret;

    if (sender.hasPermission("OreRandomizer.SetOreRatio") || !(sender instanceof Player)) {
      final String oreNode = validityCheckAndErrorMessage(sender, args);

      if (!oreNode.isEmpty()) {
        final String oreSpecifier =
            args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
        this.oreListener.getConfigFile().set(oreNode, Integer.parseInt(args[1]));

        try {
          this.materialList.populateRatios(this.oreListener.getConfigFile());
          this.materialList.populateThresholds();
          this.oreListener.saveConfigFile();
          ret = true;
        } catch (final NullPointerException e) {
          sender.sendMessage(ChatColor.RED + e.getMessage());
          e.printStackTrace();
          ret = false;
        } catch (final NegativeRatioException e) {
          sender.sendMessage(ChatColor.RED + e.getMessage());
          ret = false;
        } catch (final BadMaterialNodeException e) {
          sender.sendMessage(ChatColor.RED + e.getMessage());
          ret = false;
        }

        sender.sendMessage(args[0] + " set to " + args[1] + ".");
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
    final boolean correctNumberOfArgs = (args.length == 2);
    String validOreReference = "";
    boolean oreRatioIsIntAndInValidRange = false;

    if (correctNumberOfArgs) {
      final String normalizedOreSpecifier = args[0].toLowerCase();

      for (int i = 0; i < this.materialList.list.length; i++) {
        if (normalizedOreSpecifier.equals(this.materialList.list[i].getNormalName())) {
          validOreReference = this.materialList.list[i].getNode();
          break;
        }
      }

      try {
        oreRatioIsIntAndInValidRange = (Integer.parseInt(args[1]) > -1);
      } catch (NumberFormatException e) {
        oreRatioIsIntAndInValidRange = false;
      }
    }

    if (!correctNumberOfArgs) {
      sender.sendMessage(ChatColor.RED + "/SetOreRatio takes two arguments.");
    } else if (validOreReference.isEmpty()) {
      sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid ore specifier.");
      sender.sendMessage(ChatColor.RED + "Valid ore specifiers are:");
      sender.sendMessage(ChatColor.RED + this.materialList.getLingualList());
    } else if (!oreRatioIsIntAndInValidRange) {
      validOreReference = "";
      sender.sendMessage(ChatColor.RED + "Ratio must be a positive integer or zero.");
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
