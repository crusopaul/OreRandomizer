package io.github.crusopaul.OreRandomizer;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.util.Random;

public class OreListener implements Listener {

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

    public void SetOreRatio() {

        this.ratioPrecomputation[0] = plugin.getConfig().getInt("RandomSpawnRatios.Cobblestone");
        this.ratioPrecomputation[1] = this.ratioPrecomputation[0] +
                plugin.getConfig().getInt("RandomSpawnRatios.Coal");
        this.ratioPrecomputation[2] = this.ratioPrecomputation[1] +
                plugin.getConfig().getInt("RandomSpawnRatios.Diamond");
        this.ratioPrecomputation[3] = this.ratioPrecomputation[2] +
                plugin.getConfig().getInt("RandomSpawnRatios.Emerald");
        this.ratioPrecomputation[4] = this.ratioPrecomputation[3] +
                plugin.getConfig().getInt("RandomSpawnRatios.Gold");
        this.ratioPrecomputation[5] = this.ratioPrecomputation[4] +
                plugin.getConfig().getInt("RandomSpawnRatios.Iron");
        this.ratioPrecomputation[6] = this.ratioPrecomputation[5] +
                plugin.getConfig().getInt("RandomSpawnRatios.Lapis");
        this.ratioPrecomputation[7] = this.ratioPrecomputation[6] +
                plugin.getConfig().getInt("RandomSpawnRatios.Redstone");

    }

    OreListener (OreRandomizer pluginToSet) {

        this.plugin = pluginToSet;
        this.rng = new Random();
        this.ratioPrecomputation = new int [8];
        SetOreRatio();
        this.soundToPlay = plugin.getConfig().getBoolean("RandomizationSound.PlayCreeperPrimingSound")?
                Sound.ENTITY_CREEPER_PRIMED:
                Sound.BLOCK_LAVA_EXTINGUISH;

    }

    @EventHandler
    public void onBlockFromToEvent (BlockFormEvent event) {

        Block involvedBlock = event.getBlock();
        Material newBlockType = event.getNewState().getType();

        switch(newBlockType) {
            case COBBLESTONE:
                event.setCancelled(true);
                involvedBlock.setType(getRandomOre());
                involvedBlock.getWorld().playSound(
                        involvedBlock.getLocation(),
                        this.soundToPlay,
                        1,
                        (float)1
                );
                break;

            case STONE:
            case OBSIDIAN:
                event.setCancelled(true);
                involvedBlock.setType(newBlockType);
                involvedBlock.getWorld().playSound(
                        involvedBlock.getLocation(),
                        this.soundToPlay,
                        1,
                        (float)1
                );
                break;
            default:
                break;
        }

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
