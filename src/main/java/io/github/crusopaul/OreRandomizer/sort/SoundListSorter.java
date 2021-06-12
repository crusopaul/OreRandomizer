package io.github.crusopaul.OreRandomizer.sort;

import io.github.crusopaul.OreRandomizer.element.RandomizationSound;
import java.util.Comparator;

public class SoundListSorter implements Comparator<RandomizationSound> {
  public int compare(RandomizationSound a, RandomizationSound b) {
    return a.getName().compareTo(b.getName());
  }
}
