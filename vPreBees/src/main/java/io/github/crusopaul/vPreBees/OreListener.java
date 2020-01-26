package io.github.crusopaul.vPreBees;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.List;

public class OreListener implements Listener {

    private FileConfiguration configFile;
    private File config;
    private Random rng;
    private int [] ratioPrecomputation;
    private Sound soundToPlay;
    private List<String> AllowedWorlds;

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

    public void GetRandomizationSound(CommandSender sender) {

        String configuredSound = this.configFile.getString("RandomizationSound", "");

        sender.sendMessage(
                "Randomization sound is currently set to \"" +
                        configuredSound.substring(0, 1).toUpperCase() +
                        configuredSound.substring(1).toLowerCase() +
                        "\"."
        );

    }

    public void SetRandomizationSound(String soundToSet) {

        this.configFile.set("RandomizationSound", soundToSet);

        if (soundToSet.equals("Ssss")) {

            soundToPlay = Sound.ENTITY_CREEPER_PRIMED;

        }
        else {

            soundToPlay = Sound.BLOCK_LAVA_EXTINGUISH;

        }

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

    public List<String> GetAllowedWorlds() {

        return this.AllowedWorlds;

    }

    public void RemoveAllowedWorld(int i) {

        this.AllowedWorlds.remove(i);

    }

    public void AddNewWorld(String worldToAdd) {

        this.AllowedWorlds.add(worldToAdd);

    }

    OreListener (FileConfiguration configFileToSet, File configToSet) {

        this.configFile = configFileToSet;
        this.config = configToSet;
        this.rng = new Random();
        this.ratioPrecomputation = new int [8];
        String soundToSet = this.configFile.getString("RandomizationSound");
        SetOreRatio();

        if (soundToSet.toUpperCase().equals("SSSS")) {

            soundToPlay = Sound.ENTITY_CREEPER_PRIMED;

        }
        else {

            soundToPlay = Sound.BLOCK_LAVA_EXTINGUISH;

        }

        this.AllowedWorlds = this.configFile.getStringList("AllowedWorlds");

    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event) {

        Block involvedBlock = event.getBlock();

        boolean validWorld = false;
        for (int i = 0; i < AllowedWorlds.size(); i++) {

            if (AllowedWorlds.get(i).equals(involvedBlock.getWorld().getName())) {

                validWorld = true;

            }

        }

        if (!validWorld) {

            return;

        }

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
