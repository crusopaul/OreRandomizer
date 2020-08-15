package io.github.crusopaul.vPreBees;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveAllowedWorld implements CommandExecutor {

  private OreListener oreListener;

  RemoveAllowedWorld(OreListener oreListenerToSet) {

    this.oreListener = oreListenerToSet;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (sender.hasPermission("OreRandomizer.RemoveAllowedWorld") || !(sender instanceof Player)) {

      if (!validityCheckAndErrorMessage(sender, args)) {

        return false;
      }

      List<String> allowedWorlds = this.oreListener.GetAllowedWorlds();

      String normalizedWorldReference = args[0].toLowerCase();
      String normalizedAllowedWorldReference;
      String foundWorldName;

      for (int i = 0; i < allowedWorlds.size(); i++) {

        normalizedAllowedWorldReference = allowedWorlds.get(i).toLowerCase();

        if (normalizedAllowedWorldReference.equals(normalizedWorldReference)) {

          foundWorldName = allowedWorlds.get(i);
          this.oreListener.RemoveAllowedWorld(i);
          this.oreListener
              .getConfigFile()
              .set("AllowedWorlds", this.oreListener.GetAllowedWorlds());
          this.oreListener.saveConfigFile();
          sender.sendMessage(
              ChatColor.BLUE
                  + "OreRandomizer is now inactive for World \""
                  + foundWorldName
                  + "\".");
        }
      }

    } else {

      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
    }

    return true;
  }

  public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

    boolean validNumberOfArgs;
    String normalizedWorldReference;
    String worldName;
    boolean worldExists = false;
    boolean worldAllowed = false;

    validNumberOfArgs = args.length == 1;

    if (validNumberOfArgs) {

      worldName = args[0];
      normalizedWorldReference = args[0].toLowerCase();
      String normalizedAvailableWorldReference;

      for (int i = 0; i < Bukkit.getWorlds().size(); i++) {

        normalizedAvailableWorldReference = Bukkit.getWorlds().get(i).getName().toLowerCase();

        if (normalizedAvailableWorldReference.equals(normalizedWorldReference)) {

          worldName = Bukkit.getWorlds().get(i).getName();
          worldExists = true;
        }
      }

      if (!worldExists) {

        sender.sendMessage(ChatColor.RED + "No such world \"" + args[0] + "\".");
      }

      for (int i = 0; i < this.oreListener.GetAllowedWorlds().size(); i++) {

        normalizedAvailableWorldReference =
            this.oreListener.GetAllowedWorlds().get(i).toLowerCase();

        if (normalizedAvailableWorldReference.equals(normalizedWorldReference)) {

          worldAllowed = true;
        }
      }

      if (!worldAllowed) {

        sender.sendMessage(
            ChatColor.RED + "OreRandomizer is already inactive for World \"" + worldName + "\".");
      }

    } else {

      sender.sendMessage(ChatColor.RED + "/RemoveAllowedWorld takes one argument.");
    }

    return validNumberOfArgs && worldAllowed && worldExists;
  }
}
