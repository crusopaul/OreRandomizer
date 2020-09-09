package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.BadMaterialNodeException;
import io.github.crusopaul.VersionHandler.BadSoundNodeException;
import io.github.crusopaul.VersionHandler.NegativeRatioException;
import io.github.crusopaul.VersionHandler.VersionInterface;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class OreRandomizer extends JavaPlugin {
  private VersionInterface versionHandler;
  private OreListener oreListener;

  @Override
  public void onEnable() {
    if (this.setVersionHandler()) {
      this.saveDefaultConfig();

      if (prepareVersionHandler()) {
        try {
          this.oreListener =
              new OreListener(
                  this.getConfig(),
                  new File(this.getDataFolder(), "config.yml"),
                  this.versionHandler.randomizedMaterialList,
                  this.versionHandler.randomizationSoundList);
          this.getCommand("GetOreRatio").setExecutor(new GetOreRatio(this.oreListener));
          this.getCommand("SetOreRatio").setExecutor(new SetOreRatio(this.oreListener));
          this.getCommand("AddNewWorld").setExecutor(new AddNewWorld(this.oreListener));
          this.getCommand("GetAllowedWorlds").setExecutor(new GetAllowedWorlds(this.oreListener));
          this.getCommand("RemoveAllowedWorld")
              .setExecutor(new RemoveAllowedWorld(this.oreListener));
          this.getCommand("ToggleCreeperSound").setExecutor(new ToggleCreeperSound());
          this.getCommand("GetRandomizationSound")
              .setExecutor(new GetRandomizationSound(this.oreListener));
          this.getCommand("SetRandomizationSound")
              .setExecutor(new SetRandomizationSound(this.oreListener));
          this.getServer().getPluginManager().registerEvents(this.oreListener, this);
          this.getLogger().info("OreRandomizer enabled.");
        } catch (final Exception e) {
          this.getLogger()
              .severe("Could not instantiate commands or configuration file is invalid.");
          e.printStackTrace();
          this.setEnabled(false);
        }
      } else {
        this.setEnabled(false);
      }
    } else {
      this.setEnabled(false);
    }

    return;
  }

  @Override
  public void onDisable() {
    this.getLogger().info("OreRandomizer disabled.");
  }

  private boolean setVersionHandler() {
    final String spigotAPIVersion =
        Bukkit.getVersion()
            .substring(Bukkit.getVersion().indexOf("1."), Bukkit.getVersion().indexOf(')'));
    String versionHandler = "";
    boolean ret;

    try {
      if (spigotAPIVersion.equals("1.12.2")
          || spigotAPIVersion.equals("1.12.1")
          || spigotAPIVersion.equals("1.12")
          || spigotAPIVersion.equals("1.11.2")
          || spigotAPIVersion.equals("1.11.1")
          || spigotAPIVersion.equals("1.11")) {
        versionHandler = "vLegacy";
      } else if (spigotAPIVersion.equals("1.14.4")
          || spigotAPIVersion.equals("1.14.3")
          || spigotAPIVersion.equals("1.14.2")
          || spigotAPIVersion.equals("1.14.1")
          || spigotAPIVersion.equals("1.14")
          || spigotAPIVersion.equals("1.13.2")
          || spigotAPIVersion.equals("1.13.1")
          || spigotAPIVersion.equals("1.13")) {
        versionHandler = "vPreBees";
      } else if (spigotAPIVersion.equals("1.16.2")
          || spigotAPIVersion.equals("1.16.1")
          || spigotAPIVersion.equals("1.16")
          || spigotAPIVersion.equals("1.15.2")
          || spigotAPIVersion.equals("1.15.1")
          || spigotAPIVersion.equals("1.15")) {
        versionHandler = "vBees";
      }

      final Class<?> versionHandlerInstance =
          Class.forName("io.github.crusopaul." + versionHandler + ".VersionHandler");
      this.versionHandler =
          (VersionInterface) versionHandlerInstance.getConstructor().newInstance();
      ret = true;
    } catch (final Exception e) {
      this.getLogger().severe("This version is not yet supported");
      ret = false;
    }

    return ret;
  }

  private boolean prepareVersionHandler() {
    boolean materialListSuccess;
    boolean soundListSuccess;

    try {
      this.versionHandler.randomizedMaterialList.populateRatios(this.getConfig());
      this.versionHandler.randomizedMaterialList.populateThresholds();
      materialListSuccess = true;
    } catch (final NullPointerException e) {
      this.getLogger().severe(e.getMessage());
      e.printStackTrace();
      materialListSuccess = false;
    } catch (final NegativeRatioException e) {
      this.getLogger().warning(e.getMessage());
      materialListSuccess = false;
    } catch (final BadMaterialNodeException e) {
      this.getLogger().warning(e.getMessage());
      materialListSuccess = false;
    }

    try {
      this.versionHandler.randomizationSoundList.setSound(this.getConfig());
      soundListSuccess = true;
    } catch (final NullPointerException e) {
      this.getLogger().severe(e.getMessage());
      e.printStackTrace();
      soundListSuccess = false;
    } catch (final BadSoundNodeException e) {
      this.getLogger().warning(e.getMessage());
      soundListSuccess = false;
    }

    return materialListSuccess && soundListSuccess;
  }
}
