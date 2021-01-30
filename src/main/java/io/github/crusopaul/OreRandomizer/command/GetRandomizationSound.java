package io.github.crusopaul.OreRandomizer.command;

import io.github.crusopaul.OreRandomizer.compat.list.RandomizationSoundList;
import io.github.crusopaul.OreRandomizer.listener.OreListener;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class GetRandomizationSound implements CommandExecutor, TabCompleter {
  public GetRandomizationSound(OreListener oreListenerToSet) {
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

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    return new ArrayList<String>();
  }
}
