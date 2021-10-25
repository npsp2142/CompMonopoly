package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.Player;
import com.company.model.component.block.Block;

import java.util.Map;

public class LocationObserver implements BlockObserver {
    public final Map<Player, Block> location;

    public LocationObserver(Map<Player, Block> location) {
        this.location = location;
    }

    @Override
    public void update(Block block, Player player) {
        if (!location.containsKey(player)) {
            GameDisplay.infoMessage(String.format("%s are at %s", player, block.getColoredName()));
            location.put(player, block);
            return;
        }

        if (location.get(player).equals(block)) {
            GameDisplay.infoMessage(String.format("%s are at %s", player, block.getColoredName()));
            return;
        }
        GameDisplay.infoMessage(
                String.format("%s move from %s to %s", player, location.get(player).getColoredName(),
                        block.getColoredName()));
        location.replace(player, block);
    }
}
