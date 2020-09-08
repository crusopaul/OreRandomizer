package io.github.crusopaul.OreRandomizer;

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
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.SetRandomizationSound")
        || !(sender instanceof Player)) {
      final String soundToSet = validityCheckAndErrorMessage(sender, args);

      if (!soundToSet.isEmpty()) {
        this.oreListener.getConfigFile().set("RandomizationSound", soundToSet);
        this.soundList.setSound(this.oreListener.getConfigFile());
        this.oreListener.saveConfigFile();
        sender.sendMessage("Randomization sound set to " + soundToSet + ".");

        ret = true;
      } else {
        ret = false;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

      ret = false;
    }

    return ret;
  }

  private String validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    String soundToPlay = "";

    if (args.length == 1) {
      for (int i = 0; i < this.soundList.list.length; i++) {
        if (this.soundList.list[i].getNormalName().equals(args[0].toLowerCase())) {
          soundToPlay = this.soundList.list[i].getName();
          break;
        }
      }

      if (soundToPlay.isEmpty()) {
        sender.sendMessage(
            ChatColor.RED + "Sound \"" + args[0] + "\" is not a valid sound specifier.");
        sender.sendMessage(ChatColor.RED + "Valid sound specifiers are:");
        sender.sendMessage(ChatColor.RED + this.soundList.getLingualList());
      }
    } else {
      sender.sendMessage(ChatColor.RED + "/SetRandomizationSound takes one argument.");
    }

    return soundToPlay;
  }
}
