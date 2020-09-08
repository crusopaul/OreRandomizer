package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.RandomizationSoundList;
import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class OreListener implements Listener {
  OreListener(
      FileConfiguration configFileToSet,
      File configToSet,
      RandomizedMaterialList randomizedMaterialList,
      RandomizationSoundList randomizationSoundList) {
    this.configFile = configFileToSet;
    this.config = configToSet;
    this.materialList = randomizedMaterialList;
    this.soundList = randomizationSoundList;
    this.AllowedWorlds = this.configFile.getStringList("AllowedWorlds");
  }

  private FileConfiguration configFile;
  private File config;
  private List<String> AllowedWorlds;

  public RandomizedMaterialList materialList;
  public RandomizationSoundList soundList;

  public FileConfiguration getConfigFile() {
    return this.configFile;
  }

  public void saveConfigFile() {
    try {
      this.configFile.save(this.config);
    } catch (IOException e) {
      Bukkit.getLogger().info("Could not save to config file.");
    }
  }

  public List<String> GetAllowedWorlds() {
    return this.AllowedWorlds;
  }

  public void RemoveAllowedWorld(int i) {
    this.AllowedWorlds.remove(i);
  }

  public void AddNewWorld(String worldToAdd) {
    this.AllowedWorlds.add(worldToAdd);
  }

  @EventHandler
  public void onBlockFormEvent(BlockFormEvent event) {
    Block involvedBlock = event.getBlock();

    boolean validWorld = false;
    for (int i = 0; i < AllowedWorlds.size(); i++) {
      if (AllowedWorlds.get(i).equals(involvedBlock.getWorld().getName())) {
        validWorld = true;
        break;
      }
    }

    if (!validWorld) {
      return;
    }

    Material newBlockType = event.getNewState().getType();

    switch (newBlockType) {
      case COBBLESTONE:
        event.setCancelled(true);
        involvedBlock.setType(this.materialList.getRandomOre());
        involvedBlock
            .getWorld()
            .playSound(involvedBlock.getLocation(), this.soundList.getSound(), 1, (float) 1);
        break;
      case STONE:
      case OBSIDIAN:
        event.setCancelled(true);
        involvedBlock.setType(newBlockType);
        involvedBlock
            .getWorld()
            .playSound(involvedBlock.getLocation(), this.soundList.getSound(), 1, (float) 1);
        break;
      default:
        break;
    }
  }
}
