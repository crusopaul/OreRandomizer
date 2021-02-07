package io.github.crusopaul.OreRandomizer.compat;

import io.github.crusopaul.OreRandomizer.element.RandomizationSound;
import io.github.crusopaul.OreRandomizer.element.RandomizedMaterial;
import io.github.crusopaul.OreRandomizer.exception.BadMaterialNodeException;
import io.github.crusopaul.OreRandomizer.exception.BadSoundNodeException;
import io.github.crusopaul.OreRandomizer.exception.BadVersion;
import io.github.crusopaul.OreRandomizer.exception.NegativeRatioException;
import io.github.crusopaul.OreRandomizer.list.RandomizationSoundList;
import io.github.crusopaul.OreRandomizer.list.RandomizedMaterialList;
import io.github.crusopaul.OreRandomizer.sort.MaterialListSorter;
import io.github.crusopaul.OreRandomizer.sort.SoundListSorter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

public class VersionEngine {
  public VersionEngine(String BukkitVersionString, FileConfiguration config)
      throws BadVersion, BadMaterialNodeException, NegativeRatioException, BadSoundNodeException,
          NullPointerException {
    final Version version = this.parseVersionString(BukkitVersionString);

    if (version == Version.None) {
      throw new BadVersion("This version is not yet supported");
    }

    this.buildLists(version);

    try {
      this.prepLists(config);
    } catch (final BadMaterialNodeException e) {
      throw e;
    } catch (final NegativeRatioException e) {
      throw e;
    } catch (final BadSoundNodeException e) {
      throw e;
    } catch (final NullPointerException e) {
      throw e;
    }
  }

  public RandomizedMaterialList randomizedMaterialList;
  public RandomizationSoundList randomizationSoundList;

  private enum Version {
    Bees,
    PreBees,
    Legacy,
    None
  }

  private Version parseVersionString(String BukkitVersionString) {
    final Version version;

    System.out.println(BukkitVersionString);
    if (BukkitVersionString.contains("1.16.5")
        || BukkitVersionString.contains("1.16.4")
        || BukkitVersionString.contains("1.16.3")
        || BukkitVersionString.contains("1.16.2")
        || BukkitVersionString.contains("1.16.1")
        || BukkitVersionString.contains("1.16")
        || BukkitVersionString.contains("1.15.2")
        || BukkitVersionString.contains("1.15.1")
        || BukkitVersionString.contains("1.15")) {
      version = Version.Bees;
    } else if (BukkitVersionString.contains("1.14.4")
        || BukkitVersionString.contains("1.14.3")
        || BukkitVersionString.contains("1.14.2")
        || BukkitVersionString.contains("1.14.1")
        || BukkitVersionString.contains("1.14")
        || BukkitVersionString.contains("1.13.2")
        || BukkitVersionString.contains("1.13.1")
        || BukkitVersionString.contains("1.13")) {
      version = Version.PreBees;
    } else if (BukkitVersionString.contains("1.12.2")
        || BukkitVersionString.contains("1.12.1")
        || BukkitVersionString.contains("1.12")
        || BukkitVersionString.contains("1.11.2")
        || BukkitVersionString.contains("1.11.1")
        || BukkitVersionString.contains("1.11")) {
      version = Version.Legacy;
    } else {
      version = Version.None;
    }

    return version;
  }

  private void buildLists(Version version) {
    final List<RandomizedMaterial> matList = new ArrayList<RandomizedMaterial>();
    final List<RandomizationSound> sndList = new ArrayList<RandomizationSound>();

    switch (version) {
      case Bees:
        sndList.addAll(Arrays.asList(new RandomizationSound(Sound.ENTITY_BEE_LOOP, "Buzz")));
      case PreBees:
        matList.addAll(
            Arrays.asList(
                new RandomizedMaterial(Material.ANDESITE, "RandomSpawnRatios.Andesite", "Andesite"),
                new RandomizedMaterial(Material.DIORITE, "RandomSpawnRatios.Diorite", "Diorite"),
                new RandomizedMaterial(Material.GRANITE, "RandomSpawnRatios.Granite", "Granite")));
      case Legacy:
        matList.addAll(
            Arrays.asList(
                new RandomizedMaterial(
                    Material.COBBLESTONE, "RandomSpawnRatios.Cobblestone", "Cobblestone"),
                new RandomizedMaterial(Material.COAL_ORE, "RandomSpawnRatios.Coal", "Coal"),
                new RandomizedMaterial(
                    Material.DIAMOND_ORE, "RandomSpawnRatios.Diamond", "Diamond"),
                new RandomizedMaterial(
                    Material.EMERALD_ORE, "RandomSpawnRatios.Emerald", "Emerald"),
                new RandomizedMaterial(Material.GOLD_ORE, "RandomSpawnRatios.Gold", "Gold"),
                new RandomizedMaterial(Material.IRON_ORE, "RandomSpawnRatios.Iron", "Iron"),
                new RandomizedMaterial(Material.LAPIS_ORE, "RandomSpawnRatios.Lapis", "Lapis"),
                new RandomizedMaterial(
                    Material.REDSTONE_ORE, "RandomSpawnRatios.Redstone", "Redstone")));
        sndList.addAll(
            Arrays.asList(
                new RandomizationSound(Sound.BLOCK_LAVA_EXTINGUISH, "Normal"),
                new RandomizationSound(Sound.ENTITY_CREEPER_PRIMED, "Ssss")));
        break;
      default:
        break;
    }

    Collections.sort(matList, new MaterialListSorter());
    Collections.sort(sndList, new SoundListSorter());
    randomizedMaterialList =
        new RandomizedMaterialList(matList.toArray(new RandomizedMaterial[matList.size()]));
    randomizationSoundList =
        new RandomizationSoundList(sndList.toArray(new RandomizationSound[sndList.size()]));
  }

  private void prepLists(FileConfiguration config)
      throws BadMaterialNodeException, NegativeRatioException, BadSoundNodeException,
          NullPointerException {
    try {
      randomizedMaterialList.populateRatios(config);
      randomizedMaterialList.populateThresholds();
      randomizationSoundList.setSound(config);
    } catch (final BadMaterialNodeException e) {
      throw e;
    } catch (final NegativeRatioException e) {
      throw e;
    } catch (final BadSoundNodeException e) {
      throw e;
    } catch (final NullPointerException e) {
      throw e;
    }
  }
}
