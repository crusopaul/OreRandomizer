package io.github.crusopaul.OreRandomizer.compat.element;

import org.bukkit.Material;

public class RandomizedMaterial {
  public RandomizedMaterial(Material rm, String mn, String fn) {
    this.randomizedMaterial = rm;
    this.materialNode = mn;
    this.friendlyName = fn;
    this.ratio = 0;
    this.threshold = 0;
  }

  private Material randomizedMaterial;
  private String materialNode;
  private String friendlyName;
  private int ratio;
  private int threshold;

  public Material getMaterial() {
    return this.randomizedMaterial;
  }

  public String getNode() {
    return this.materialNode;
  }

  public String getName() {
    return this.friendlyName;
  }

  public String getNormalName() {
    return this.friendlyName.toLowerCase();
  }

  public int getRatio() {
    return this.ratio;
  }

  public void setRatio(int ratioToSet) {
    this.ratio = ratioToSet;
  }

  public int getThreshold() {
    return this.threshold;
  }

  public void setThreshold(int thresholdToSet) {
    this.threshold = thresholdToSet;
  }
}
