package io.github.crusopaul.OreRandomizer;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Random;

public class OreListener implements Listener {

    // Private fields
    private OreRandomizer plugin;
    private Random rng;
    private int [] ratioPrecomputation;
    private Sound soundToPlay;

    public Random getRng() {

        return this.rng;

    }

    public void ToggleCreeperSound() {

        this.soundToPlay = (this.soundToPlay == Sound.ENTITY_CREEPER_PRIMED)?
                Sound.BLOCK_LAVA_EXTINGUISH:
                Sound.ENTITY_CREEPER_PRIMED;

    }

    // Constructor
    OreListener (OreRandomizer pluginToSet) {

        this.plugin = pluginToSet;
        this.rng = new Random();
        this.ratioPrecomputation = new int [8];

        // Compute ratios for random ore generation
        this.ratioPrecomputation[0] = plugin.getConfig().getInt("RandomSpawnRatios.Cobblestone"); // Cobblestone: 20
        this.ratioPrecomputation[1] = this.ratioPrecomputation[0] +
                plugin.getConfig().getInt("RandomSpawnRatios.Coal"); // Coal
        this.ratioPrecomputation[2] = this.ratioPrecomputation[1] +
                plugin.getConfig().getInt("RandomSpawnRatios.Diamond"); // Diamond
        this.ratioPrecomputation[3] = this.ratioPrecomputation[2] +
                plugin.getConfig().getInt("RandomSpawnRatios.Emerald"); // Emerald
        this.ratioPrecomputation[4] = this.ratioPrecomputation[3] +
                plugin.getConfig().getInt("RandomSpawnRatios.Gold"); // Gold
        this.ratioPrecomputation[5] = this.ratioPrecomputation[4] +
                plugin.getConfig().getInt("RandomSpawnRatios.Iron"); // Iron
        this.ratioPrecomputation[6] = this.ratioPrecomputation[5] +
                plugin.getConfig().getInt("RandomSpawnRatios.Lapis"); // Lapis
        this.ratioPrecomputation[7] = this.ratioPrecomputation[6] +
                plugin.getConfig().getInt("RandomSpawnRatios.Redstone"); // Redstone

        this.soundToPlay = plugin.getConfig().getBoolean("RandomizationSound.PlayCreeperPrimingSound")?
                Sound.ENTITY_CREEPER_PRIMED:
                Sound.BLOCK_LAVA_EXTINGUISH;

    }

    // Event handler
    @EventHandler
    public void onBlockFromToEvent (BlockFormEvent event) {

        Block involvedBlock = event.getBlock();

        if (event.getNewState().getType() == Material.COBBLESTONE) {

            event.setCancelled(true);
            involvedBlock.setType(getRandomOre());
            involvedBlock.getWorld().playSound(
                    involvedBlock.getLocation(),
                    this.soundToPlay,
                    1,
                    (float)0.9
            );

        }

    }

    // Returns a random material based on ore ratio settings
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
