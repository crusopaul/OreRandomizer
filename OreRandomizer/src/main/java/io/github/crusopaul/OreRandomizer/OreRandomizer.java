package io.github.crusopaul.OreRandomizer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.crusopaul.VersionHandler.VersionInterface;

import java.io.File;
import java.io.IOException;

public class OreRandomizer extends JavaPlugin {

    private VersionInterface versionHandler;

    @Override
    public void onEnable() {

        String spigotAPIVersion = Bukkit.getVersion();
        spigotAPIVersion = spigotAPIVersion.substring(spigotAPIVersion.indexOf("1."), spigotAPIVersion.indexOf(')'));

        if (
            spigotAPIVersion.equals("1.14.4") ||
            spigotAPIVersion.equals("1.14.3") ||
            spigotAPIVersion.equals("1.14.2") ||
            spigotAPIVersion.equals("1.14.1") ||
            spigotAPIVersion.equals("1.14") ||
            spigotAPIVersion.equals("1.13.2") ||
            spigotAPIVersion.equals("1.13.1") ||
            spigotAPIVersion.equals("1.13") ||
            spigotAPIVersion.equals("1.12.2") ||
            spigotAPIVersion.equals("1.12.1") ||
            spigotAPIVersion.equals("1.12") ||
            spigotAPIVersion.equals("1.11.2") ||
            spigotAPIVersion.equals("1.11")
        ) {

            spigotAPIVersion = "vPreBees";

        }
        else if (
                spigotAPIVersion.equals("1.16.1") ||
                spigotAPIVersion.equals("1.16") ||
                spigotAPIVersion.equals("1.15.2") ||
                spigotAPIVersion.equals("1.15.1") ||
                spigotAPIVersion.equals("1.15")
        ) {

            spigotAPIVersion = "vBees";

        }

        try {

            final Class<?> versionHandlerInstance = Class.forName("io.github.crusopaul." + spigotAPIVersion + ".VersionHandler");

            this.versionHandler = (VersionInterface) versionHandlerInstance.getConstructor().newInstance();


        } catch (final Exception e) {

            this.getLogger().severe("This version is not yet supported");
            e.printStackTrace();
            this.setEnabled(false);
            return;

        }

        this.saveDefaultConfig();

        try {

            switch (validateConfig(spigotAPIVersion)) {
                case -2:
                    throw new NullPointerException("The config key \"RandomizationSound\" is missing or does not match a known sound.");
                case -1:
                    throw new NullPointerException("One or more of the config keys is missing or cannot be cast as the expected type.");
                case 0:
                    throw new NullPointerException("One or more of the ratios is negative and preventing OreRandomizer from loading.");
                default:
                    break;
            }

            this.versionHandler.instantiate(this.getConfig(), new File(this.getDataFolder(), "config.yml"));
            this.getCommand("GetOreRatio").setExecutor(this.versionHandler.getGetOreRatio());
            this.getCommand("SetOreRatio").setExecutor(this.versionHandler.getSetOreRatio());
            this.getCommand("AddNewWorld").setExecutor(this.versionHandler.getAddNewWorld());
            this.getCommand("GetAllowedWorlds").setExecutor(this.versionHandler.getGetAllowedWorlds());
            this.getCommand("RemoveAllowedWorld").setExecutor(this.versionHandler.getRemoveAllowedWorld());
            this.getCommand("ToggleCreeperSound").setExecutor(this.versionHandler.getToggleCreeperSound());
            this.getCommand("GetRandomizationSound").setExecutor(this.versionHandler.getGetRandomizationSound());
            this.getCommand("SetRandomizationSound").setExecutor(this.versionHandler.getSetRandomizationSound());
            this.getServer().getPluginManager().registerEvents(this.versionHandler.getOreListener(), this);
            this.getLogger().info("OreRandomizer enabled.");

        } catch (final Exception e) {

            this.getLogger().severe("Could not instantiate commands or configuration file is invalid.");
            e.printStackTrace();
            this.setEnabled(false);

        }

    }

    @Override
    public void onDisable() {

        this.getLogger().info("OreRandomizer disabled.");

    }

    public int validateConfig(String spigotAPIVersion) {

        int[] ratios = new int[8];
        boolean playCreeperSound;
        String soundToPlay;

        playCreeperSound = this.getConfig().getBoolean("RandomizationSound.PlayCreeperPrimingSound");

        if ( spigotAPIVersion.equals("vPreBees") ) {

            if ( !this.getConfig().isSet("RandomizationSound.PlayCreeperPrimingSound") ) {

                soundToPlay = this.getConfig().getString("RandomizationSound");

                if (soundToPlay == null) {

                    return -2;

                }
                else if (
                       !soundToPlay.toUpperCase().equals("SSSS") &&
                                !soundToPlay.toUpperCase().equals("NORMAL")
                ) {

                    return -2;

                }

            }
            else if (playCreeperSound) {

                this.getLogger().warning("Old config.yml detected, backing up and updating accordingly.");

                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml.bak"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not backup config file, attempting to overwrite.");

                }

                this.getConfig().set("RandomizationSound.PlayCreeperPrimingSound", null);
                this.getConfig().set("RandomizationSound", "Ssss");

                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not save to config file.");

                }

            }
            else {

                this.getLogger().warning("Old config.yml detected, backing up and updating accordingly.");
                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml.bak"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not backup config file, attempting to overwrite.");

                }
                this.getConfig().set("RandomizationSound.PlayCreeperPrimingSound", null);
                this.getConfig().set("RandomizationSound", "normal");
                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not save to config file.");

                }

            }

        }
        else if ( spigotAPIVersion.equals("vBees") ) {

            if ( !this.getConfig().isSet("RandomizationSound.PlayCreeperPrimingSound") ) {

                soundToPlay = this.getConfig().getString("RandomizationSound");

                if (soundToPlay == null) {

                    return -2;

                }
                else if (
                    !soundToPlay.toUpperCase().equals("BUZZ") &&
                            !soundToPlay.toUpperCase().equals("SSSS") &&
                            !soundToPlay.toUpperCase().equals("NORMAL")
                ) {

                    return -2;

                }

            }
            else if (playCreeperSound) {

                this.getLogger().warning("Old config.yml detected, backing up and updating accordingly.");
                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml.bak"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not backup config file, attempting to overwrite.");

                }
                this.getConfig().set("RandomizationSound.PlayCreeperPrimingSound", null);
                this.getConfig().set("RandomizationSound", "Ssss");
                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not save to config file.");

                }

            }
            else {

                this.getLogger().warning("Old config.yml detected, backing up and updating accordingly.");
                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml.bak"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not backup config file, attempting to overwrite.");

                }
                this.getConfig().set("RandomizationSound.PlayCreeperPrimingSound", null);
                this.getConfig().set("RandomizationSound", "normal");
                try {

                    this.getConfig().save(new File(this.getDataFolder(), "config.yml"));

                } catch (IOException e) {

                    Bukkit.getLogger().info("Could not save to config file.");

                }

            }

        }

        try {

            ratios[0] = this.getConfig().getInt("RandomSpawnRatios.Cobblestone");
            ratios[1] = this.getConfig().getInt("RandomSpawnRatios.Coal");
            ratios[2] = this.getConfig().getInt("RandomSpawnRatios.Diamond");
            ratios[3] = this.getConfig().getInt("RandomSpawnRatios.Emerald");
            ratios[4] = this.getConfig().getInt("RandomSpawnRatios.Gold");
            ratios[5] = this.getConfig().getInt("RandomSpawnRatios.Iron");
            ratios[6] = this.getConfig().getInt("RandomSpawnRatios.Lapis");
            ratios[7] = this.getConfig().getInt("RandomSpawnRatios.Redstone");

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

            } else {

                return 0;

            }

        } catch (NullPointerException e) {

            return -1;

        }

    }

}
