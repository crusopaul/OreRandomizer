package io.github.crusopaul.OreRandomizer.command;

import io.github.crusopaul.OreRandomizer.listener.OreListener;
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

public class SetObsidianToggle implements CommandExecutor, TabCompleter {
  public SetObsidianToggle(OreListener oreListenerToSet) {
    this.oreListener = oreListenerToSet;
  }

  private OreListener oreListener;

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    final boolean ret;

    if (sender.hasPermission("OreRandomizer.SetObsidianToggle") || !(sender instanceof Player)) {
      final int toggle = validityCheckAndErrorMessage(sender, args);
      final boolean toggleBoolean = toggle == 1;

      switch (toggle) {
        case 0:
        case 1:
          this.oreListener.getConfigFile().set("RandomizeOnObsidianCreation", toggleBoolean);
          this.oreListener.setRandomizeOnObsidianCreation(toggleBoolean);
          this.oreListener.saveConfigFile();

          sender.sendMessage(
              ChatColor.BLUE
                  + "Ore randomization upon obsidian creation has been set to: "
                  + Boolean.toString(toggleBoolean)
                  + ".");

          ret = true;
          break;
        default:
          ret = false;
          break;
      }
    } else {
      sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());
      ret = false;
    }

    return ret;
  }

  private int validityCheckAndErrorMessage(CommandSender sender, String[] args) {
    int toggle = -1;

    if (args.length > 0) {
      final String userInput = args[0].toLowerCase();

      if (userInput.equals("true")) {
        toggle = 1;
      } else if (userInput.equals("false")) {
        toggle = 0;
      } else {
        sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid boolean.");
        sender.sendMessage(ChatColor.RED + "Valid booleans are: true or false.");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "/SetObsidianToggle takes one argument.");
    }

    return toggle;
  }

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    final List<String> completionList = new ArrayList<String>();
    final List<String> validBooleans = new ArrayList<String>();

    if (this.oreListener.getRandomizeOnStoneCreation()) {
      validBooleans.add("false");
    } else {
      validBooleans.add("true");
    }

    if (args.length == 1) {
      StringUtil.copyPartialMatches(args[0], validBooleans, completionList);
      Collections.sort(completionList);
    }

    return completionList;
  }
}
