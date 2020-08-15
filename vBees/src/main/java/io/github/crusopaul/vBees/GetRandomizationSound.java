package io.github.crusopaul.vBees;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetRandomizationSound implements CommandExecutor {

  private OreListener oreListener;

  GetRandomizationSound(OreListener oreListenerToSet) {

    this.oreListener = oreListenerToSet;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (sender.hasPermission("OreRandomizer.GetRandomizationSound")
        || !(sender instanceof Player)) {

      if (!validityCheckAndErrorMessage(sender, args)) {

        return false;
      }

      this.oreListener.GetRandomizationSound(sender);

    } else {

      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
    }

    return true;
  }

  public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

    boolean validNumberOfArgs;

    validNumberOfArgs = args.length == 0;

    if (!validNumberOfArgs) {

      sender.sendMessage(ChatColor.RED + "/GetRandomizationSound takes no arguments.");
    }

    return validNumberOfArgs;
  }
}
