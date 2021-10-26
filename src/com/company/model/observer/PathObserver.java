package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathObserver implements PlayerObserver {
    public static final String DEFAULT_NAME = "Block Visit Observer";
    private final PlayerLocation playerLocation;
    private final Block start;
    private final Map<Player, List<Block>> paths;

    public PathObserver(PlayerLocation playerLocation, Block start) {
        this.playerLocation = playerLocation;
        this.paths = new HashMap<>();
        this.start = start;
    }

    public void view() {
        for (Player player : paths.keySet()) {
            GameDisplay.titleBar(player.getName());
            List<Block> path = paths.get(player);
            for (Block block : path) {
                GameDisplay.infoMessage(
                        String.format("Round %d: %s", path.indexOf(block), block.getColoredName()));
            }
        }
    }

    @Override
    public void update(Player player) {
        Block block = player.getCurrentLocation(playerLocation);
        if (!paths.containsKey(player)) {
            GameDisplay.infoMessage(String.format("%s is at %s", player, start.getColoredName()));
            List<Block> path = new ArrayList<>();
            paths.put(player, path);
            paths.get(player).add(start);
            return;
        }
        List<Block> path = paths.get(player);
        if (path.get(path.size() - 1).equals(block)) {
            GameDisplay.infoMessage(String.format("%s stay at %s", player, block.getColoredName()));
            path.add(block);
            return;
        }
        GameDisplay.infoMessage(
                String.format("%s move from %s to %s", player, path.get(path.size() - 1).getColoredName(),
                        block.getColoredName()));
        path.add(block);
    }


}
