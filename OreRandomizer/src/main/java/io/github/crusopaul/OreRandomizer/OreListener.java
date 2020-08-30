package io.github.crusopaul.OreRandomizer;

import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class OreListener implements Listener {

  OreListener(
      FileConfiguration configFileToSet,
      File configToSet,
      RandomizedMaterialList randomizedMaterialList) {

    this.configFile = configFileToSet;
    this.config = configToSet;
    this.materialList = randomizedMaterialList;
    String soundToSet = this.configFile.getString("RandomizationSound");

    if (soundToSet.toUpperCase().equals("BUZZ")) {

      soundToPlay = Sound.ENTITY_BEE_LOOP;

    } else if (soundToSet.toUpperCase().equals("SSSS")) {

      soundToPlay = Sound.ENTITY_CREEPER_PRIMED;

    } else {

      soundToPlay = Sound.BLOCK_LAVA_EXTINGUISH;
    }

    this.AllowedWorlds = this.configFile.getStringList("AllowedWorlds");
  }

  private FileConfiguration configFile;
  private File config;
  private int[] ratioPrecomputation;
  private Sound soundToPlay;
  private List<String> AllowedWorlds;

  public RandomizedMaterialList materialList;

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

  public void GetRandomizationSound(CommandSender sender) {

    String configuredSound = this.configFile.getString("RandomizationSound", "");

    sender.sendMessage(
        "Randomization sound is currently set to \""
            + configuredSound.substring(0, 1).toUpperCase()
            + configuredSound.substring(1).toLowerCase()
            + "\".");
  }

  public void SetRandomizationSound(String soundToSet) {

    this.configFile.set("RandomizationSound", soundToSet);

    if (soundToSet.equals("Buzz")) {

      soundToPlay = Sound.ENTITY_BEE_LOOP;

    } else if (soundToSet.equals("Ssss")) {

      soundToPlay = Sound.ENTITY_CREEPER_PRIMED;

    } else {

      soundToPlay = Sound.BLOCK_LAVA_EXTINGUISH;
    }
  }

  public void SetOreRatio() {
    this.materialList.populateRatios(this.configFile);
    this.materialList.populateThresholds();
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
            .playSound(involvedBlock.getLocation(), this.soundToPlay, 1, (float) 1);
        break;
      case STONE:
      case OBSIDIAN:
        event.setCancelled(true);
        involvedBlock.setType(newBlockType);
        involvedBlock
            .getWorld()
            .playSound(involvedBlock.getLocation(), this.soundToPlay, 1, (float) 1);
        break;
      default:
        break;
    }
  }
}
