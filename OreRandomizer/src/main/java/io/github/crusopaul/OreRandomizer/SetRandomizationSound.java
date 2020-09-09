package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.BadSoundNodeException;
import io.github.crusopaul.VersionHandler.RandomizationSoundList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRandomizationSound implements CommandExecutor {
  SetRandomizationSound(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
    this.soundList = oreListenerToSet.soundList;
  }

  private OreListener oreListener;
  private RandomizationSoundList soundList;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    boolean ret;

    if (sender.hasPermission("OreRandomizer.SetRandomizationSound")
        || !(sender instanceof Player)) {
      if (validityCheckAndErrorMessage(sender, args)) {
        final String oldSound = this.oreListener.getConfigFile().getString("RandomizationSound");
        final String soundSpecifier =
            args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();

        try {
          this.oreListener.getConfigFile().set("RandomizationSound", soundSpecifier);
          this.soundList.setSound(this.oreListener.getConfigFile());
          this.oreListener.saveConfigFile();
          sender.sendMessage("Randomization sound set to " + soundSpecifier + ".");
          ret = true;
        } catch (final NullPointerException e) {
          sender.sendMessage(ChatColor.RED + e.getMessage());
          e.printStackTrace();
          ret = false;
        } catch (final BadSoundNodeException e) {
          sender.sendMessage(ChatColor.RED + e.getMessage());
          this.oreListener.getConfigFile().set("RandomizationSound", oldSound);
          ret = false;
        }
      } else {
        ret = false;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
      ret = false;
    }

    return ret;
  }

  private boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    final boolean ret;

    if (args.length != 1) {
      sender.sendMessage(ChatColor.RED + "/SetRandomizationSound takes one argument.");
      ret = false;
    } else {
      ret = true;
    }

    return ret;
  }
}
