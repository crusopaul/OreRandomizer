package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.OreRandomizer.command.AddNewWorld;
import io.github.crusopaul.OreRandomizer.command.GetAllowedWorlds;
import io.github.crusopaul.OreRandomizer.command.GetObsidianToggle;
import io.github.crusopaul.OreRandomizer.command.GetOreRatio;
import io.github.crusopaul.OreRandomizer.command.GetRandomizationSound;
import io.github.crusopaul.OreRandomizer.command.GetStoneToggle;
import io.github.crusopaul.OreRandomizer.command.RemoveAllowedWorld;
import io.github.crusopaul.OreRandomizer.command.SetObsidianToggle;
import io.github.crusopaul.OreRandomizer.command.SetOreRatio;
import io.github.crusopaul.OreRandomizer.command.SetRandomizationSound;
import io.github.crusopaul.OreRandomizer.command.SetStoneToggle;
import io.github.crusopaul.OreRandomizer.compat.VersionEngine;
import io.github.crusopaul.OreRandomizer.exception.BadMaterialNodeException;
import io.github.crusopaul.OreRandomizer.exception.BadSoundNodeException;
import io.github.crusopaul.OreRandomizer.exception.BadVersion;
import io.github.crusopaul.OreRandomizer.exception.NegativeRatioException;
import io.github.crusopaul.OreRandomizer.listener.OreListener;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OreRandomizer extends JavaPlugin {
  private VersionEngine versionEngine;
  private OreListener oreListener;

  @Override
  public void onEnable() {
    try {
      this.versionEngine = new VersionEngine(Bukkit.getVersion(), this.getConfig());
      this.saveDefaultConfig();

      this.oreListener =
          new OreListener(
              this.getConfig(),
              new File(this.getDataFolder(), "config.yml"),
              this.versionEngine.randomizedMaterialList,
              this.versionEngine.randomizationSoundList);
      this.getCommand("GetOreRatio").setExecutor(new GetOreRatio(this.oreListener));
      this.getCommand("SetOreRatio").setExecutor(new SetOreRatio(this.oreListener));
      this.getCommand("AddNewWorld").setExecutor(new AddNewWorld(this.oreListener));
      this.getCommand("GetAllowedWorlds").setExecutor(new GetAllowedWorlds(this.oreListener));
      this.getCommand("RemoveAllowedWorld").setExecutor(new RemoveAllowedWorld(this.oreListener));
      this.getCommand("GetRandomizationSound")
          .setExecutor(new GetRandomizationSound(this.oreListener));
      this.getCommand("SetRandomizationSound")
          .setExecutor(new SetRandomizationSound(this.oreListener));
      this.getCommand("GetObsidianToggle").setExecutor(new GetObsidianToggle(this.oreListener));
      this.getCommand("SetObsidianToggle").setExecutor(new SetObsidianToggle(this.oreListener));
      this.getCommand("GetStoneToggle").setExecutor(new GetStoneToggle(this.oreListener));
      this.getCommand("SetStoneToggle").setExecutor(new SetStoneToggle(this.oreListener));
      this.getServer().getPluginManager().registerEvents(this.oreListener, this);
      this.getLogger().info("OreRandomizer enabled.");
    } catch (final BadVersion e) {
      this.getLogger().severe(e.getMessage());
      this.setEnabled(false);
    } catch (final BadMaterialNodeException e) {
      this.getLogger().severe(e.getMessage());
      this.setEnabled(false);
    } catch (final NegativeRatioException e) {
      this.getLogger().severe(e.getMessage());
      this.setEnabled(false);
    } catch (final BadSoundNodeException e) {
      this.getLogger().severe(e.getMessage());
      this.setEnabled(false);
    } catch (final Exception e) {
      this.getLogger().severe("Could not instantiate commands or configuration file is invalid.");
      e.printStackTrace();
      this.setEnabled(false);
    }

    return;
  }

  @Override
  public void onDisable() {
    this.getLogger().info("OreRandomizer disabled.");
  }
}
