package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.block.Block;
import com.company.model.component.Player;

import java.util.Map;

public class LocationObserver implements BlockObserver {
    public final Map<Player, Block> location;

    public LocationObserver(Map<Player, Block> location) {
        this.location = location;
    }

    @Override
    public void update(Block block, Player player) {
        if (!location.containsKey(player)) {
            try {
                GameDisplay.infoMessage(String.format("%s are at %s", player, block));
            } catch (Exception e) {
                e.printStackTrace();
            }
            location.put(player, block);
            return;
        }

        if (location.get(player).equals(block)) {
            try {
                GameDisplay.infoMessage(String.format("%s are at %s", player, block));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            GameDisplay.infoMessage(String.format("%s move from %s to %s", player, location.get(player), block));
        } catch (Exception e) {
            e.printStackTrace();
        }
        location.replace(player, block);
    }
}
