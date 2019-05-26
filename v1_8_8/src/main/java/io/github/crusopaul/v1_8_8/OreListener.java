package io.github.crusopaul.v1_8_8;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class OreListener implements Listener {

    private FileConfiguration configFile;
    private File config;
    private Random rng;
    private int [] ratioPrecomputation;
    private Sound soundToPlay;
    private Block[] adjacentBlocks;

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

    public Random getRng() {

        return this.rng;

    }

    public void ToggleCreeperSound() {

        this.soundToPlay = (this.soundToPlay == Sound.CREEPER_HISS)?
                Sound.FIZZ:
                Sound.CREEPER_HISS;

    }

    public void SetOreRatio() {

        this.ratioPrecomputation[0] = this.configFile.getInt("RandomSpawnRatios.Cobblestone");
        this.ratioPrecomputation[1] = this.ratioPrecomputation[0] +
                this.configFile.getInt("RandomSpawnRatios.Coal");
        this.ratioPrecomputation[2] = this.ratioPrecomputation[1] +
                this.configFile.getInt("RandomSpawnRatios.Diamond");
        this.ratioPrecomputation[3] = this.ratioPrecomputation[2] +
                this.configFile.getInt("RandomSpawnRatios.Emerald");
        this.ratioPrecomputation[4] = this.ratioPrecomputation[3] +
                this.configFile.getInt("RandomSpawnRatios.Gold");
        this.ratioPrecomputation[5] = this.ratioPrecomputation[4] +
                this.configFile.getInt("RandomSpawnRatios.Iron");
        this.ratioPrecomputation[6] = this.ratioPrecomputation[5] +
                this.configFile.getInt("RandomSpawnRatios.Lapis");
        this.ratioPrecomputation[7] = this.ratioPrecomputation[6] +
                this.configFile.getInt("RandomSpawnRatios.Redstone");

    }

    OreListener (FileConfiguration configFileToSet, File configToSet) {

        this.configFile = configFileToSet;
        this.config = configToSet;
        this.rng = new Random();
        this.ratioPrecomputation = new int [8];
        SetOreRatio();
        this.soundToPlay = this.configFile.getBoolean("RandomizationSound.PlayCreeperPrimingSound")?
                Sound.CREEPER_HISS:
                Sound.FIZZ;

    }

    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent event) {

        Block fromBlock = event.getBlock();
        Block toBlock = event.getToBlock();

        if (generatesCobblestone(fromBlock, toBlock)) {

            event.setCancelled(true);
            fromBlock.setType(getRandomOre());
            fromBlock.getWorld().playSound(
                    fromBlock.getLocation(),
                    this.soundToPlay,
                    1,
                    (float) 1
            );

        }

    }

    public boolean generatesCobblestone(Block fromBlock, Block toBlock) {

        this.adjacentBlocks = new Block [] {

        };

        return true;

    }

    public Material getRandomOre() {

        Material materialToReturn;
        int randomNumber = getRng().nextInt(ratioPrecomputation[7]);

        if (randomNumber < ratioPrecomputation[0])
            materialToReturn = Material.COBBLESTONE;
        else if (randomNumber < ratioPrecomputation[1])
            materialToReturn = Material.COAL_ORE;
        else if (randomNumber < ratioPrecomputation[2])
            materialToReturn = Material.DIAMOND_ORE;
        else if (randomNumber < ratioPrecomputation[3])
            materialToReturn = Material.EMERALD_ORE;
        else if (randomNumber < ratioPrecomputation[4])
            materialToReturn = Material.GOLD_ORE;
        else if (randomNumber < ratioPrecomputation[5])
            materialToReturn = Material.IRON_ORE;
        else if (randomNumber < ratioPrecomputation[6])
            materialToReturn = Material.LAPIS_ORE;
        else if (randomNumber < ratioPrecomputation[7])
            materialToReturn = Material.REDSTONE_ORE;
        else
            materialToReturn = Material.COBBLESTONE;

        return materialToReturn;

    }

}
