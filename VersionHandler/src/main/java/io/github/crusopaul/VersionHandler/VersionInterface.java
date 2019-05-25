package io.github.crusopaul.VersionHandler;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.File;

public abstract class VersionInterface {

    public abstract void instantiate(FileConfiguration configFileToSet, File configToSet);

    public abstract CommandExecutor getGetOreRatio();
    public abstract CommandExecutor getSetOreRatio();
    public abstract CommandExecutor getToggleCreeperSound();
    public abstract Listener getOreListener();

}