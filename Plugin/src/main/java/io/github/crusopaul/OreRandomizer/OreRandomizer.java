package io.github.crusopaul.OreRandomizer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.crusopaul.VersionHandler.VersionInterface;

import java.io.File;

public class OreRandomizer extends JavaPlugin {

    private VersionInterface versionHandler;

    @Override
    public void onEnable () {

        String spigotAPIVersion = Bukkit.getVersion();
        spigotAPIVersion = spigotAPIVersion.substring(spigotAPIVersion.indexOf("1."), spigotAPIVersion.indexOf(')'));

        if (spigotAPIVersion.equals("1.14.1")) {
            spigotAPIVersion = "v1_14_1";
        }
        else if (spigotAPIVersion.equals("1.14")) {
            spigotAPIVersion = "v1_14";
        }
        else if (spigotAPIVersion.equals("1.13.2")) {
            spigotAPIVersion = "v1_13_2";
        }
        else if (spigotAPIVersion.equals("1.13.1")) {
            spigotAPIVersion = "v1_13_1";
        }
        else if (spigotAPIVersion.equals("1.13")) {
            spigotAPIVersion = "v1_13";
        }
        else if (spigotAPIVersion.equals("1.8.8")) {
            spigotAPIVersion = "v1_8_8";
        }

        try {

            final Class<?> versionHandlerInstance = Class.forName("io.github.crusopaul." + spigotAPIVersion + ".VersionHandler");

            this.versionHandler = (VersionInterface) versionHandlerInstance.getConstructor().newInstance();


        } catch (final Exception e) {

            this.getLogger().severe("This version is not yet supported");
            this.getLogger().severe("Tried to load class io.github.crusopaul." + spigotAPIVersion + ".VersionHandler");
            e.printStackTrace();
            this.setEnabled(false);
            return;

        }

        this.saveDefaultConfig();

        try {

            switch(validateConfig()) {
                case -1:
                    throw new NullPointerException("One or more of the config keys is missing or cannot be cast as the expected type.");
                case 0:
                    throw new NullPointerException("One or more of the ratios is negative and preventing OreRandomizer from loading.");
                default:
                    break;
            }

            this.getLogger().info("Class loaded: " + this.versionHandler);

            this.versionHandler.instantiate(this.getConfig(), new File(this.getDataFolder(), "config.yml"));
            this.getCommand("GetOreRatio").setExecutor(this.versionHandler.getGetOreRatio());
            this.getCommand("SetOreRatio").setExecutor(this.versionHandler.getSetOreRatio());
            this.getCommand("ToggleCreeperSound").setExecutor(this.versionHandler.getToggleCreeperSound());
            this.getServer().getPluginManager().registerEvents(this.versionHandler.getOreListener(), this);
            this.getLogger().info("OreRandomizer enabled.");

        } catch (final Exception e) {

            this.getLogger().severe("Could not instantiate commands or configuration file is invalid.");
            e.printStackTrace();
            this.getLogger().info("OreRandomizer not enabled.");
            this.setEnabled(false);

        }

    }

    @Override
    public void onDisable () {

        this.getLogger().info("OreRandomizer disabled.");

    }

    public int validateConfig() {

        int [] ratios = new int[8];

        try {

            ratios[0] = this.getConfig().getInt("RandomSpawnRatios.Cobblestone");
            ratios[1] = this.getConfig().getInt("RandomSpawnRatios.Coal");
            ratios[2] = this.getConfig().getInt("RandomSpawnRatios.Diamond");
            ratios[3] = this.getConfig().getInt("RandomSpawnRatios.Emerald");
            ratios[4] = this.getConfig().getInt("RandomSpawnRatios.Gold");
            ratios[5] = this.getConfig().getInt("RandomSpawnRatios.Iron");
            ratios[6] = this.getConfig().getInt("RandomSpawnRatios.Lapis");
            ratios[7] = this.getConfig().getInt("RandomSpawnRatios.Redstone");

            this.getConfig().getBoolean("RandomizationSound.PlayCreeperPrimingSound");

            if (
                ratios[0] > -1 &&
                ratios[1] > -1 &&
                ratios[2] > -1 &&
                ratios[3] > -1 &&
                ratios[4] > -1 &&
                ratios[5] > -1 &&
                ratios[6] > -1 &&
                ratios[7] > -1
            ) {

              return 1;

            }
            else {

                return 0;

            }

        } catch (NullPointerException e) {

            return -1;

        }

    }

}
