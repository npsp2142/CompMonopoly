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
    public static final String DEFAULT_NAME = "Path Observer";
    private final PlayerLocation playerLocation;
    private final Map<Player, List<Block>> paths;
    private final Block start;

    public PathObserver(PlayerLocation playerLocation, Block start) {
        this.playerLocation = playerLocation;
        this.paths = new HashMap<>();
        this.start = start;
        for (Player player : playerLocation.getPlayers()) {
            List<Block> path = new ArrayList<>();
            paths.put(player, path);
            paths.get(player).add(start);
        }
    }

    public void update(Player player) {
        Block currentLocation = player.getCurrentLocation(playerLocation);
        if (!paths.containsKey(player)) {
            return;
        }
        List<Block> path = paths.get(player);
        if (path.get(path.size() - 1).equals(currentLocation)) {
            GameDisplay.infoMessage(String.format("%s stay at %s", player, currentLocation.getColoredName()));
            path.add(currentLocation);
            return;
        }
        GameDisplay.infoMessage(
                String.format("%s move from %s to %s", player, path.get(path.size() - 1).getColoredName(),
                        currentLocation.getColoredName()));
        path.add(currentLocation);
    }

    public void reset() {
        paths.clear();
        for (Player player : playerLocation.getPlayers()) {
            List<Block> path = new ArrayList<>();
            paths.put(player, path);
            paths.get(player).add(start);
        }
    }

    public Map<Player, List<Block>> getPaths() {
        return paths;
    }
}
