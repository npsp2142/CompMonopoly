package com.company.model;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.Block;
import com.company.model.observer.BlockVisitObserver;
import com.company.model.observer.PathObserver;
import com.company.model.save.*;

import java.util.*;

public class GameLoader {
    private final GameSystem gameSystem;
    private final GameSave gameSave;
    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final PlayerLocation playerLocation;
    private final ArrayList<PlayerSave> playerSaves;
    private final ArrayList<PropertySave> propertySaves;
    private final PlayerLocationSave locationSave;
    private final Random random;

    public GameLoader(GameSystem gameSystem, GameSave gameSave) {
        this.gameSystem = gameSystem;
        this.gameSave = gameSave;
        players = gameSystem.getPlayers();
        properties = gameSystem.getProperties();
        playerLocation = gameSystem.getLocation();
        playerSaves = gameSave.getPlayerSaves();
        propertySaves = gameSave.getPropertySaves();
        locationSave = gameSave.getPlayerLocationSave();
        random = gameSave.getRandom();
    }

    public void load() {
        HashMap<PlayerSave, Player> playerSavePlayerHashMap = new HashMap<>();
        players.clear();
        playerLocation.getPlayers().clear();

        // Load player
        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.NORMAL, Player.DEFAULT_AMOUNT);

        for (PlayerSave playerSave : playerSaves) {  // Load every player in save file
            Player player = playerFactory.make(playerSave.getName(), playerSave.getStatus(), playerSave.getAmount());
            players.add(player);
            playerSavePlayerHashMap.put(playerSave, player);
        }

        // Load properties
        for (Property property : properties) { // Clear all owner
            property.reset();
        }

        for (PropertySave propertySave : propertySaves) { // Load every property in save file
            for (Property property : properties) {
                if (property.getName().equals(propertySave.getName())) {
                    property.setOwner(playerSavePlayerHashMap.get(propertySave.getOwner()));
                    break;
                }
            }
        }

        // Load location
        for (PlayerSave playerSave : locationSave.getLocation().keySet()) { // Move every player to the destination.
            playerLocation.setStartLocation();
            playerLocation.moveTo(playerSavePlayerHashMap.get(playerSave), locationSave.getLocation().get(playerSave).getName());
        }

        gameSystem.setCurrentPlayer(playerSavePlayerHashMap.get(gameSave.getCurrentPlayer()));
        gameSystem.setRound(gameSave.getRound());
        gameSystem.setRandom(gameSave.getRandom());

        loadBlockObservers();
        loadPlayerObservers(playerSavePlayerHashMap);
    }

    private void loadBlockObservers() {
        if (gameSave.getBlockVisit() == null) {
            return;
        }
        if (gameSystem.getBlockObservers() == null) {
            return;
        }
        Map<BlockSave, Integer> blockSaveIntegerMap = gameSave.getBlockVisit();
        BlockVisitObserver blockVisitObserver = new BlockVisitObserver();
        for (BlockSave blockSave : blockSaveIntegerMap.keySet()) {
            Block block = gameSystem.getBoard().findBlock(blockSave.getName());
            blockVisitObserver.getCounter().put(block, blockSaveIntegerMap.get(blockSave));
        }

        gameSystem.getBlockObservers().clear();
        gameSystem.getBlockObservers().put(BlockVisitObserver.DEFAULT_NAME, blockVisitObserver);
    }

    private void loadPlayerObservers(Map<PlayerSave, Player> playerSavePlayerMap) {
        if (gameSave.getPath() == null) {
            return;
        }
        if (gameSystem.getPlayerObservers() == null) {
            return;
        }
        Map<PlayerSave, List<BlockSave>> playerSaveListMap = gameSave.getPath();
        PathObserver pathObserver = new PathObserver(playerLocation, playerLocation.getStartBlock());
        for (PlayerSave playerSave : playerSaveListMap.keySet()) {
            Player player = playerSavePlayerMap.get(playerSave);
            List<Block> path = new ArrayList<>();
            for (BlockSave blockSave : playerSaveListMap.get(playerSave)) {
                Block block = gameSystem.getBoard().findBlock(blockSave.getName());
                path.add(block);
            }
            pathObserver.getPaths().put(player, path);
        }
        gameSystem.getPlayerObservers().clear();
        gameSystem.getPlayerObservers().put(PathObserver.DEFAULT_NAME, pathObserver);
    }
}
