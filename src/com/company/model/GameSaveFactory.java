package com.company.model;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.Block;
import com.company.model.observer.BlockVisitObserver;
import com.company.model.observer.PathObserver;
import com.company.model.save.*;

import java.util.*;

public class GameSaveFactory {

    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final PlayerLocation playerLocation;
    private final Integer round;
    private final Player currentPlayer;
    private final Random random;
    private BlockVisitObserver blockVisitObserver;
    private PathObserver pathObserver;

    public GameSaveFactory(
            ArrayList<Player> players,
            ArrayList<Property> properties,
            PlayerLocation playerLocation,
            Integer round,
            Player currentPlayer,
            Random random) {
        this.players = players;
        this.properties = properties;
        this.playerLocation = playerLocation;
        this.round = round;
        this.currentPlayer = currentPlayer;
        this.random = random;
    }

    public GameSave make() {
        ArrayList<PlayerSave> playerSaves = new ArrayList<>();
        HashMap<Player, PlayerSave> playerPlayerSaveHashMap = new HashMap<>();
        for (Player player : players) {
            PlayerSave playerSave = new PlayerSave(player.getName(), player.getStatus(), player.getAmount());
            playerSaves.add(playerSave);
            playerPlayerSaveHashMap.put(player, playerSave);
        }
        ArrayList<PropertySave> propertySaves = new ArrayList<>();
        for (Property property : properties) {
            if (property.getOwner() == null) {
                continue;
            }
            PropertySave propertyDatum =
                    new PropertySave(property.getName(), playerPlayerSaveHashMap.get(property.getOwner()));
            propertySaves.add(propertyDatum);
        }

        HashMap<PlayerSave, BlockSave> playerSaveBlockSaveHashMap = new HashMap<>();
        PlayerLocationSave locationSave = new PlayerLocationSave(playerSaveBlockSaveHashMap);
        for (Player player : players) {
            BlockSave blockDatum = new BlockSave(playerLocation.getCurrentLocation(player).getName());
            playerSaveBlockSaveHashMap.put(playerPlayerSaveHashMap.get(player), blockDatum);
        }


        GameSave gameSave = new GameSave(playerSaves, propertySaves,
                locationSave, round, playerPlayerSaveHashMap.get(currentPlayer), random);

        if (blockVisitObserver != null) {
            Map<Block, Integer> blockVisitObserverCounter = blockVisitObserver.getCounter();
            Map<BlockSave, Integer> blockSaveIntegerMap = new HashMap<>();
            for (Block block : blockVisitObserverCounter.keySet()) {
                BlockSave blockSave = new BlockSave(block.getName());
                blockSaveIntegerMap.put(blockSave, blockVisitObserverCounter.get(block));
            }
            gameSave.setBlockVisit(blockSaveIntegerMap);
        }
        if (pathObserver != null) {
            Map<Player, List<Block>> paths = pathObserver.getPaths();
            Map<PlayerSave, List<BlockSave>> newPaths = new HashMap<>();
            for (Player player : paths.keySet()) {
                List<Block> path = paths.get(player);
                List<BlockSave> savedPath = new ArrayList<>();
                for (Block block : path) {
                    BlockSave blockSave = new BlockSave(block.getName());
                    savedPath.add(blockSave);
                }
                PlayerSave playerSave = playerPlayerSaveHashMap.get(player);
                newPaths.put(playerSave, savedPath);
            }
            gameSave.setPath(newPaths);
        }

        return gameSave;
    }

    public void setBlockVisitObserver(BlockVisitObserver blockVisitObserver) {
        this.blockVisitObserver = blockVisitObserver;
    }

    public void setPathObserver(PathObserver pathObserver) {
        this.pathObserver = pathObserver;
    }
}
