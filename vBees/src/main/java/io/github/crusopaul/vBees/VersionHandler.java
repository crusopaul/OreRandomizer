package io.github.crusopaul.vBees;

import io.github.crusopaul.VersionHandler.RandomizedMaterial;
import io.github.crusopaul.VersionHandler.RandomizedMaterialList;
import io.github.crusopaul.VersionHandler.VersionInterface;
import org.bukkit.Material;

public class VersionHandler extends VersionInterface {
  public VersionHandler() {
    this.randomizedMaterialList =
        new RandomizedMaterialList(
            new RandomizedMaterial[] {
              new RandomizedMaterial(Material.ANDESITE, "RandomSpawnRatios.ANDESITE", "ANDESITE"),
              new RandomizedMaterial(
                  Material.COBBLESTONE, "RandomSpawnRatios.COBBLESTONE", "COBBLESTONE"),
              new RandomizedMaterial(Material.COAL_ORE, "RandomSpawnRatios.COAL_ORE", "COAL_ORE"),
              new RandomizedMaterial(
                  Material.DIAMOND_ORE, "RandomSpawnRatios.DIAMOND_ORE", "DIAMOND_ORE"),
              new RandomizedMaterial(Material.DIORITE, "RandomSpawnRatios.DIORITE", "DIORITE"),
              new RandomizedMaterial(
                  Material.EMERALD_ORE, "RandomSpawnRatios.EMERALD_ORE", "EMERALD_ORE"),
              new RandomizedMaterial(Material.GOLD_ORE, "RandomSpawnRatios.GOLD_ORE", "GOLD_ORE"),
              new RandomizedMaterial(Material.GRANITE, "RandomSpawnRatios.GRANITE", "GRANITE"),
              new RandomizedMaterial(Material.IRON_ORE, "RandomSpawnRatios.IRON_ORE", "IRON_ORE"),
              new RandomizedMaterial(
                  Material.LAPIS_ORE, "RandomSpawnRatios.LAPIS_ORE", "LAPIS_ORE"),
              new RandomizedMaterial(
                  Material.REDSTONE_ORE, "RandomSpawnRatios.REDSTONE_ORE", "REDSTONE_ORE")
            });
  }
}
