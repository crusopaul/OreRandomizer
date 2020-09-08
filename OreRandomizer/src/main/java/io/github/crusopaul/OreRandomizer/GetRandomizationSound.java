package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.RandomizationSoundList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetRandomizationSound implements CommandExecutor {
  GetRandomizationSound(OreListener oreListenerToSet) {
    this.soundList = oreListenerToSet.soundList;
  }

  private RandomizationSoundList soundList;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.GetRandomizationSound")
        || !(sender instanceof Player)) {
      if (!validityCheckAndErrorMessage(sender, args)) {
        ret = false;
      } else {
        sender.sendMessage("Randomization sound is: " + this.soundList.getSoundName() + ".");

        ret = true;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

      ret = false;
    }

    return ret;
  }

  private boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    final boolean validNumberOfArgs = args.length == 0;

    if (!validNumberOfArgs) {
      sender.sendMessage(ChatColor.RED + "/GetRandomizationSound takes no arguments.");
    }

    return validNumberOfArgs;
  }
}
