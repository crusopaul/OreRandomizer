package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GetOreRatio implements CommandExecutor {

  GetOreRatio(OreListener oreListenerToSet) {
    this.config = oreListenerToSet.getConfigFile();
    this.materialList = oreListenerToSet.materialList;
  }

  private FileConfiguration config;
  private RandomizedMaterialList materialList;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender.hasPermission("OreRandomizer.SetOreRatio") || !(sender instanceof Player)) {
      if (args.length > 0) {
        final String oreNode = validityCheckAndErrorMessage(sender, args);

        if (oreNode.isEmpty()) {
          return false;
        }

        final String oreSpecifier =
            args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

        sender.sendMessage(ChatColor.BLUE + oreSpecifier + ": " + this.config.getInt(oreNode));
      } else {
        for (int i = 0; i < this.materialList.list.length; i++) {
          sender.sendMessage(
              ChatColor.BLUE
                  + this.materialList.list[i].getName()
                  + ": "
                  + this.config.getInt(this.materialList.list[i].getNode()));
        }
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
    }

    return true;
  }

  public String validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    String validOreReference = "";

    if (args.length > 0) {
      final String normalizedOreSpecifier = args[0].toLowerCase();

      for (int i = 0; i < this.materialList.list.length; i++) {
        if (normalizedOreSpecifier.equals(this.materialList.list[i].getNormalName())) {
          validOreReference = this.materialList.list[i].getNode();
          break;
        }
      }

      if (validOreReference.isEmpty()) {
        sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid ore specifier.");
        sender.sendMessage(ChatColor.RED + "Valid ore specifiers are:");
        sender.sendMessage(ChatColor.RED + this.materialList.getLingualList());
      }
    }

    return validOreReference;
  }
}
