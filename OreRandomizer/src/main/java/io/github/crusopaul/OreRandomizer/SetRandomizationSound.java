package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.BadSoundNodeException;
import io.github.crusopaul.VersionHandler.RandomizationSoundList;
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

class SetRandomizationSound implements CommandExecutor, TabCompleter {
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

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    final List<String> completionList = new ArrayList<String>();

    if (args.length == 1) {
      StringUtil.copyPartialMatches(args[0], getSoundNames(), completionList);
      Collections.sort(completionList);
    }

    return completionList;
  }

  private List<String> getSoundNames() {
    final List<String> ret = new ArrayList<String>();

    for (int i = 0; i < this.soundList.list.length; i++) {
      if (!this.soundList.list[i].getName().equals(this.soundList.getSound().getName())) {
        ret.add(this.soundList.list[i].getName());
      }
    }

    return ret;
  }
}
