package io.github.crusopaul.vPreBees;

import io.github.crusopaul.VersionHandler.VersionInterface;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class VersionHandler extends VersionInterface {

    private GetOreRatio getOreRatio;
    private SetOreRatio setOreRatio;
    private AddNewWorld addNewWorld;
    private GetAllowedWorlds getAllowedWorlds;
    private RemoveAllowedWorld removeAllowedWorld;
    private GetRandomizationSound getRandomizationSound;
    private SetRandomizationSound setRandomizationSound;
    private ToggleCreeperSound toggleCreeperSound;
    private OreListener oreListener;

    @Override
    public void instantiate (FileConfiguration configFileToSet, File configToSet) {

        this.oreListener = new OreListener(configFileToSet, configToSet);
        this.getOreRatio = new GetOreRatio(this.oreListener);
        this.setOreRatio = new SetOreRatio(this.oreListener);
        this.addNewWorld = new AddNewWorld(this.oreListener);
        this.getAllowedWorlds = new GetAllowedWorlds(this.oreListener);
        this.removeAllowedWorld = new RemoveAllowedWorld(this.oreListener);
        this.getRandomizationSound = new GetRandomizationSound(this.oreListener);
        this.setRandomizationSound = new SetRandomizationSound(this.oreListener);
        this.toggleCreeperSound = new ToggleCreeperSound();

    }

    @Override
    public GetOreRatio getGetOreRatio() {

        return this.getOreRatio;

    }

    @Override
    public SetOreRatio getSetOreRatio() {

        return this.setOreRatio;

    }

    @Override
    public AddNewWorld getAddNewWorld() {

        return this.addNewWorld;

    }

    @Override
    public GetAllowedWorlds getGetAllowedWorlds() {

        return this.getAllowedWorlds;

    }

    @Override
    public RemoveAllowedWorld getRemoveAllowedWorld () {

        return this.removeAllowedWorld;

    }

    @Override
    public GetRandomizationSound getGetRandomizationSound() {

        return this.getRandomizationSound;

    }

    @Override
    public SetRandomizationSound getSetRandomizationSound() {

        return this.setRandomizationSound;

    }

    @Override
    public ToggleCreeperSound getToggleCreeperSound() {

        return this.toggleCreeperSound;

    }

    @Override
    public OreListener getOreListener() {

        return this.oreListener;

    }

}