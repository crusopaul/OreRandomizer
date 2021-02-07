package io.github.crusopaul.OreRandomizer.sort;

import io.github.crusopaul.OreRandomizer.element.RandomizedMaterial;
import java.util.Comparator;

public class MaterialListSorter implements Comparator<RandomizedMaterial> {
  public int compare(RandomizedMaterial a, RandomizedMaterial b) {
    return a.getName().compareTo(b.getName());
  }
}
