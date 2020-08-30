package io.github.crusopaul.VersionHandler;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class RandomizedMaterialList {

  public RandomizedMaterialList(RandomizedMaterial[] listToSet) {
    this.list = listToSet;
    this.rng = new Random();
  }

  private Random rng;

  public RandomizedMaterial[] list;

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

  public void populateRatios(FileConfiguration config)
      throws BadMaterialNodeException, NegativeRatioException, NullPointerException {
    if (config == null) {
      throw new NullPointerException("The config file is not accessible");
    }

    int ratioToSet;

    for (int i = 0; i < this.list.length; i++) {
      ratioToSet = config.getInt(this.list[i].getNode());

      if (!config.isInt(this.list[i].getNode())) {
        throw new BadMaterialNodeException(
            "Value of \""
                + this.list[i].getNode()
                + "\" material node is not an integer or does not exist");
      } else if (ratioToSet < 0) {
        throw new NegativeRatioException(
            "Value of \"" + this.list[i].getNode() + "\" material node is negative");
      } else {
        this.list[i].setRatio(ratioToSet);
      }
    }
  }

  public void populateThresholds() {
    switch (this.list.length) {
      case 0:
        break;
      case 1:
        this.list[0].setThreshold(this.list[0].getRatio());
        break;
      default:
        this.list[0].setThreshold(this.list[0].getRatio());
        for (int i = 1; i < this.list.length; i++) {
          this.list[i].setThreshold(this.list[i].getRatio() + this.list[i - 1].getThreshold());
        }
        break;
    }
  }

  public Material getRandomOre() {
    Material materialToReturn = Material.COBBLESTONE;

    switch (this.list.length) {
      case 0:
        break;
      case 1:
        materialToReturn = this.list[0].getMaterial();
        break;
      default:
        if (this.list[this.list.length - 1].getThreshold() != 0) {
          int randomNumber = this.rng.nextInt(this.list[this.list.length - 1].getThreshold());
          if (randomNumber < this.list[0].getThreshold()) {
            materialToReturn = this.list[0].getMaterial();
          } else {
            for (int i = 1; i < this.list.length; i++) {
              if (randomNumber >= this.list[i - 1].getThreshold()) {
                materialToReturn = this.list[i].getMaterial();
                break;
              }
            }
          }
        }
        break;
    }

    return materialToReturn;
  }
}
