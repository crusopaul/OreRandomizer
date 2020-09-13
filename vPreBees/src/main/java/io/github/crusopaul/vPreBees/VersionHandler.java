package io.github.crusopaul.vPreBees;

import io.github.crusopaul.VersionHandler.RandomizationSound;
import io.github.crusopaul.VersionHandler.RandomizationSoundList;
import io.github.crusopaul.VersionHandler.RandomizedMaterial;
import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import io.github.crusopaul.VersionHandler.VersionInterface;
import org.bukkit.Material;
import org.bukkit.Sound;

public class VersionHandler extends VersionInterface {
  public VersionHandler() {
    this.randomizedMaterialList =
        new RandomizedMaterialList(
            new RandomizedMaterial[] {
              new RandomizedMaterial(Material.ANDESITE, "RandomSpawnRatios.Andesite", "Andesite"),
              new RandomizedMaterial(
                  Material.COBBLESTONE, "RandomSpawnRatios.Cobblestone", "Cobblestone"),
              new RandomizedMaterial(Material.COAL_ORE, "RandomSpawnRatios.Coal", "Coal"),
              new RandomizedMaterial(Material.DIAMOND_ORE, "RandomSpawnRatios.Diamond", "Diamond"),
              new RandomizedMaterial(Material.DIORITE, "RandomSpawnRatios.Diorite", "Diorite"),
              new RandomizedMaterial(Material.EMERALD_ORE, "RandomSpawnRatios.Emerald", "Emerald"),
              new RandomizedMaterial(Material.GOLD_ORE, "RandomSpawnRatios.Gold", "Gold"),
              new RandomizedMaterial(Material.GRANITE, "RandomSpawnRatios.Granite", "Granite"),
              new RandomizedMaterial(Material.IRON_ORE, "RandomSpawnRatios.Iron", "Iron"),
              new RandomizedMaterial(Material.LAPIS_ORE, "RandomSpawnRatios.Lapis", "Lapis"),
              new RandomizedMaterial(
                  Material.REDSTONE_ORE, "RandomSpawnRatios.Redstone", "Redstone")
            });

    this.randomizationSoundList =
        new RandomizationSoundList(
            new RandomizationSound[] {
              new RandomizationSound(Sound.BLOCK_LAVA_EXTINGUISH, "Normal"),
              new RandomizationSound(Sound.ENTITY_CREEPER_PRIMED, "Ssss")
            });
  }
}
