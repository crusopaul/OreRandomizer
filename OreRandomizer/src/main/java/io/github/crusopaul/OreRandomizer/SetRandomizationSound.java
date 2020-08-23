package io.github.crusopaul.OreRandomizer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRandomizationSound implements CommandExecutor {

  private OreListener oreListener;

  SetRandomizationSound(OreListener oreListenerToSet) {

    this.oreListener = oreListenerToSet;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (sender.hasPermission("OreRandomizer.SetRandomizationSound")
        || !(sender instanceof Player)) {

      if (!validityCheckAndErrorMessage(sender, args)) {

        return false;
      }

      String configuredSound =
          args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

      this.oreListener.getConfigFile().set("RandomizationSound", configuredSound);

      this.oreListener.SetRandomizationSound(configuredSound);
      this.oreListener.saveConfigFile();
      sender.sendMessage("Randomization sound set to " + configuredSound + ".");

    } else {

      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
    }

    return true;
  }

  public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

    boolean validNumberOfArgs;
    String soundToPlay;
    boolean validSound = false;

    validNumberOfArgs = args.length == 1;

    if (validNumberOfArgs) {

      soundToPlay = args[0].toUpperCase();

      if (!soundToPlay.equals("BUZZ")
          && !soundToPlay.equals("SSSS")
          && !soundToPlay.equals("NORMAL")) {

        sender.sendMessage(
            ChatColor.RED
                + "Sound not valid, allowed sounds are \"Buzz\", \"Ssss\", and \"Normal\".");

      } else {

        validSound = true;
      }

    } else {

      sender.sendMessage(ChatColor.RED + "/SetRandomizationSound takes one argument.");
    }

    return validNumberOfArgs && validSound;
  }
}
