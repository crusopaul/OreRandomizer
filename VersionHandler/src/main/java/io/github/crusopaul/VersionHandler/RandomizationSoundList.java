package io.github.crusopaul.VersionHandler;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

public class RandomizationSoundList {
  public RandomizationSoundList(RandomizationSound[] listToSet) {
    this.list = listToSet;
    this.soundToPlayIndex = -1;
  }

  private int soundToPlayIndex;

  public final RandomizationSound[] list;

  public String getLingualList() {
    String lingualList = "";

    switch (this.list.length) {
      case 0:
        lingualList = "";
        break;
      case 1:
        lingualList = this.list[0].getName();
        break;
      case 2:
        lingualList = this.list[0].getName() + " or " + this.list[1].getName();
        break;
      default:
        for (int i = 0; i < this.list.length - 1; i++) {
          lingualList = lingualList + ", " + this.list[i].getName();
        }
        lingualList =
            lingualList.substring(2) + ", or " + this.list[this.list.length - 1].getName();
        break;
    }

    return lingualList;
  }

  public void setSound(FileConfiguration configFile) throws BadSoundNodeException {
    final String soundName = configFile.getString("RandomizationSound");
    int soundIndex = -1;

    for (int i = 0; i < this.list.length; i++) {
      if (this.list[i].getNormalName().equals(soundName.toLowerCase())) {
        soundIndex = i;
        break;
      }
    }

    if (soundIndex == -1) {
      throw new BadSoundNodeException("Sound \"" + soundName + "\" is not a valid sound specifier");
    } else {
      this.soundToPlayIndex = soundIndex;
    }
  }

  public RandomizationSound getSound() {
    RandomizationSound ret = new RandomizationSound(Sound.BLOCK_LAVA_EXTINGUISH, "Normal");

    if (this.soundToPlayIndex != -1) {
      ret = this.list[this.soundToPlayIndex];
    }

    return ret;
  }

  public String getSoundName() {
    String ret = "Normal";

    if (this.soundToPlayIndex != -1) {
      ret = this.list[this.soundToPlayIndex].getName();
    }

    return ret;
  }
}
