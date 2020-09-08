package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetOreRatio implements CommandExecutor {
  GetOreRatio(OreListener oreListenerToSet) {
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
}
