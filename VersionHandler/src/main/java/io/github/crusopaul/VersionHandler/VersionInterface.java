package io.github.crusopaul.VersionHandler;

import java.io.File;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public abstract class VersionInterface {

  public abstract void instantiate(FileConfiguration configFileToSet, File configToSet);

  public abstract CommandExecutor getGetOreRatio();

  public abstract CommandExecutor getSetOreRatio();

  public abstract CommandExecutor getAddNewWorld();

  public abstract CommandExecutor getGetAllowedWorlds();

  public abstract CommandExecutor getRemoveAllowedWorld();

  public abstract CommandExecutor getToggleCreeperSound();

  public abstract CommandExecutor getGetRandomizationSound();

  public abstract CommandExecutor getSetRandomizationSound();

  public abstract Listener getOreListener();
}
