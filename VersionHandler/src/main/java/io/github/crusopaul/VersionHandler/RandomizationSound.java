package io.github.crusopaul.VersionHandler;

import org.bukkit.Sound;

public class RandomizationSound {
  public RandomizationSound(Sound s, String nm) {
    this.sound = s;
    this.name = nm;
  }

  private Sound sound;
  private String name;

  public Sound getSound() {
    return this.sound;
  }

  public String getName() {
    return this.name;
  }

  public String getNormalName() {
    return this.name.toLowerCase();
  }
}
