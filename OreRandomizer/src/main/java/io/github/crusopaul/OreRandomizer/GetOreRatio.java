package io.github.crusopaul.OreRandomizer;

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
        final String oreSpecified =
            args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

        if (oreNode.isEmpty()) {
          return false;
        }

        sender.sendMessage(ChatColor.BLUE + oreSpecifier + ": " + this.config.getInt(oreNode));
      } else {
        for (RandomizedMaterial m : this.materialList) {
          sender.sendMessage(ChatColor.BLUE + m.getName() + ": " + this.config.getInt(m.getNode()));
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

      for (RandomizedMaterial m : this.materialList) {
        if (normalizedOreSpecifier.equals(m.getNormalName())) {
          validOreReference = m.getNode();
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
