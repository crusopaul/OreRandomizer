package io.github.crusopaul.v1_13_1;

import io.github.crusopaul.VersionHandler.VersionInterface;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class VersionHandler extends VersionInterface {

    private GetOreRatio getOreRatio;
    private SetOreRatio setOreRatio;
    private ToggleCreeperSound toggleCreeperSound;
    private OreListener oreListener;

    @Override
    public void instantiate (FileConfiguration configFileToSet, File configToSet) {

        this.oreListener = new OreListener(configFileToSet, configToSet);
        this.getOreRatio = new GetOreRatio(this.oreListener);
        this.setOreRatio = new SetOreRatio(this.oreListener);
        this.toggleCreeperSound = new ToggleCreeperSound(this.oreListener);

    }

    public GetOreRatio getGetOreRatio() {

        return this.getOreRatio;

    }

    @Override
    public SetOreRatio getSetOreRatio() {

        return this.setOreRatio;

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