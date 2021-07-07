package io.github.crusopaul.OreRandomizer.listener;

import io.github.crusopaul.OreRandomizer.list.RandomizationSoundList;
import io.github.crusopaul.OreRandomizer.list.RandomizedMaterialList;
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
  public OreListener(
      FileConfiguration configFileToSet,
      File configToSet,
      RandomizedMaterialList randomizedMaterialList,
      RandomizationSoundList randomizationSoundList) {
    this.configFile = configFileToSet;
    this.config = configToSet;
    this.materialList = randomizedMaterialList;
    this.soundList = randomizationSoundList;
    this.AllowedWorlds = this.configFile.getStringList("AllowedWorlds");
    this.RandomizeOnStoneCreation = this.configFile.getBoolean("RandomizeOnStoneCreation");
    this.RandomizeOnObsidianCreation = this.configFile.getBoolean("RandomizeOnObsidianCreation");
  }

  private FileConfiguration configFile;
  private File config;
  private List<String> AllowedWorlds;
  private boolean RandomizeOnStoneCreation;
  private boolean RandomizeOnObsidianCreation;

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

  public List<String> getAllowedWorlds() {
    return this.AllowedWorlds;
  }

  public void removeAllowedWorld(int i) {
    this.AllowedWorlds.remove(i);
  }

  public void addNewWorld(String worldToAdd) {
    this.AllowedWorlds.add(worldToAdd);
  }

  public boolean getRandomizeOnStoneCreation() {
    return this.RandomizeOnStoneCreation;
  }

  public void setRandomizeOnStoneCreation(boolean b) {
    this.RandomizeOnStoneCreation = b;
  }

  public boolean getRandomizeOnObsidianCreation() {
    return this.RandomizeOnObsidianCreation;
  }

  public void setRandomizeOnObsidianCreation(boolean b) {
    this.RandomizeOnObsidianCreation = b;
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
      case COBBLED_DEEPSLATE:
        event.setCancelled(true);
        randomizeAndPlaySound(involvedBlock);
        break;
      case STONE:
      case DEEPSLATE:
        event.setCancelled(true);
        if (this.RandomizeOnStoneCreation) {
          randomizeAndPlaySound(involvedBlock);
        } else {
          setTypeAndPlaySound(involvedBlock, newBlockType);
        }
        break;
      case OBSIDIAN:
        event.setCancelled(true);
        if (this.RandomizeOnObsidianCreation) {
          randomizeAndPlaySound(involvedBlock);
        } else {
          setTypeAndPlaySound(involvedBlock, newBlockType);
        }
        break;
      default:
        break;
    }
  }

  private void randomizeAndPlaySound(Block block) {
    Material newType;

    if (block.getLocation().getBlockY() < 17) {
      newType = this.materialList.getRandomOre(true);
    } else {
      newType = this.materialList.getRandomOre(false);
    }

    setTypeAndPlaySound(block, newType);
  }

  private void setTypeAndPlaySound(Block block, Material mat) {
    block.setType(mat);
    block
        .getWorld()
        .playSound(block.getLocation(), this.soundList.getSound().getEnum(), 1, (float) 1);
  }
}
