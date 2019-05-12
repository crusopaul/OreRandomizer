package io.github.crusopaul.OreRandomizer;

import org.bukkit.plugin.java.JavaPlugin;

public class OreRandomizer extends JavaPlugin {

    private OreListener listener;
    private boolean loaded;

    public OreListener getListener() {

        return this.listener;

    }

    @Override
    public void onEnable () {

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

            this.getCommand("ToggleCreeperSound").setExecutor(new ToggleCreeperSound(this));
            this.getCommand("SetOreRatio").setExecutor(new SetOreRatio(this));
            this.getCommand("GetOreRatio").setExecutor(new GetOreRatio(this));
            this.listener = new OreListener(this);
            this.getServer().getPluginManager().registerEvents(this.listener, this);
            this.getLogger().info("OreRandomizer enabled.");
            this.loaded = true;

        } catch (NullPointerException e) {

            this.loaded = false;
            this.getLogger().info("Could not instantiate commands or configuration file is invalid.");
            this.getLogger().info(e.getMessage());
            this.getLogger().info("OreRandomizer not enabled.");

        }

    }

    @Override
    public void onDisable () {

        if (this.loaded) {

            this.getLogger().info("OreRandomizer disabled.");

        }

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
