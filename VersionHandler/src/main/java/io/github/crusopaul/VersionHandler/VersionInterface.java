package io.github.crusopaul.VersionHandler;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFormEvent;

public interface VersionInterface {

    public void sendMessage(Player player, String message);
    public void BlockFormEvent(Block block, BlockState newState);


}