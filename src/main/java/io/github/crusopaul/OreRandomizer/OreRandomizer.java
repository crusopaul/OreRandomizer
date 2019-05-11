package io.github.crusopaul.OreRandomizer;

import org.bukkit.plugin.java.JavaPlugin;

public class OreRandomizer extends JavaPlugin {

    private OreListener listener;

    public OreListener getListener() {

        return this.listener;

    }

    @Override
    public void onEnable () {

        this.saveDefaultConfig();
        this.listener = new OreListener(this);
        getServer().getPluginManager().registerEvents(this.listener, this);
        this.getCommand("ToggleCreeperSound").setExecutor(new ToggleCreeperSound(this));
        getLogger().info("OreRandomizer enabled");

    }

    @Override
    public void onDisable () {

        getLogger().info("OreRandomizer disabled");

    }

}
