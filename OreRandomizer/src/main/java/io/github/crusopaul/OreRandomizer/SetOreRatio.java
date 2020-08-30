package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOreRatio implements CommandExecutor {

  SetOreRatio(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
    this.materialList = oreListenerToSet.materialList;
  }

  private OreListener oreListener;
  private RandomizedMaterialList materialList;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (sender.hasPermission("OreRandomizer.SetOreRatio") || !(sender instanceof Player)) {
      final String oreNode = validityCheckAndErrorMessage(sender, args);

      if (oreNode.isEmpty()) {
        return false;
      }

      final String oreSpecifier =
          args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

      this.oreListener.getConfigFile().set(oreNode, Integer.parseInt(args[1]));
      this.oreListener.SetOreRatio();
      this.oreListener.saveConfigFile();
      sender.sendMessage(oreSpecifier + " set to " + args[1] + ".");
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
    }

    return true;
  }

  public String validityCheckAndErrorMessage(CommandSender sender, String[] args) {

    boolean correctNumberOfArgs = (args.length == 2);
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
}
