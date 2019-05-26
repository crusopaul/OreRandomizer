package io.github.crusopaul.v1_13_1;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOreRatio implements CommandExecutor {

    private OreListener oreListener;

    SetOreRatio(OreListener oreListenerToSet) {

        this.oreListener = oreListenerToSet;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("OreRandomizer.SetOreRatio") || !(sender instanceof Player)) {

            if (!validityCheckAndErrorMessage(sender, args)) {

                return false;

            }

            String oreSpecifier = args[0].substring(0, 1).toUpperCase() +
                    args[0].substring(1).toLowerCase();

            this.oreListener.getConfigFile().set("RandomSpawnRatios." + oreSpecifier, Integer.parseInt(args[1]));
            this.oreListener.SetOreRatio();
            this.oreListener.saveConfigFile();
            sender.sendMessage( oreSpecifier +  " set to " + args[1] + ".");

        }
        else {

            sender.sendMessage(ChatColor.RED + cmd.getPermissionMessage());

        }

        return true;

    }

    public boolean validityCheckAndErrorMessage(CommandSender sender, String[] args) {

        boolean correctNumberOfArgs;
        boolean validOreReference;
        boolean oreRatioIsIntAndInValidRange;
        String normalizedOreSpecifier = args[0].toLowerCase();


        correctNumberOfArgs = (args.length == 2);


        validOreReference = (
                normalizedOreSpecifier.equals("cobblestone") ||
                        normalizedOreSpecifier.equals("coal") ||
                        normalizedOreSpecifier.equals("diamond") ||
                        normalizedOreSpecifier.equals("emerald") ||
                        normalizedOreSpecifier.equals("gold") ||
                        normalizedOreSpecifier.equals("iron") ||
                        normalizedOreSpecifier.equals("lapis") ||
                        normalizedOreSpecifier.equals("redstone")
        );


        if (correctNumberOfArgs) {
            try {

                oreRatioIsIntAndInValidRange = (Integer.parseInt(args[1]) > -1);

            } catch (NumberFormatException e) {

               oreRatioIsIntAndInValidRange = false;

            }
        }
        else {

            oreRatioIsIntAndInValidRange = false;

        }


        if (!correctNumberOfArgs) {

            sender.sendMessage(ChatColor.RED + "/SetOreRatio takes two arguments.");

        }
        else if (!validOreReference) {

            sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid ore specifier.");
            sender.sendMessage(ChatColor.RED + "Valid ore specifiers are:");
            sender.sendMessage(ChatColor.RED + "Cobblestone, Coal, Diamond, Emerald, Gold, Iron, Lapis, or Redstone");

        }
        else if (!oreRatioIsIntAndInValidRange) {

            sender.sendMessage(ChatColor.RED + "Ratio must be a positive integer or zero.");

        }

        return (correctNumberOfArgs && validOreReference && oreRatioIsIntAndInValidRange);

    }

}
