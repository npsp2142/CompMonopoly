package com.company.model.data;

import com.company.model.GameSystem;
import com.company.model.PlayerFactory;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameDataFactory {

    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final PlayerLocation playerLocation;
    private final Integer round;
    private final Player currentPlayer;
    private final Random random;

    public GameDataFactory(
            ArrayList<Player> players,
            ArrayList<Property> properties,
            PlayerLocation playerLocation,
            Integer round,
            Player currentPlayer,
            Random random
    ) {
        this.players = players;
        this.properties = properties;
        this.playerLocation = playerLocation;
        this.round = round;
        this.currentPlayer = currentPlayer;
        this.random = random;
    }

    public GameData make() {
        ArrayList<PlayerDatum> playerData = new ArrayList<>();
        HashMap<Player, PlayerDatum> playerDatumHashMap = new HashMap<>();
        for (Player player : players) {
            PlayerDatum saveData = new PlayerDatum(player.getName(), player.getStatus(), player.getAmount());
            playerData.add(saveData);
            playerDatumHashMap.put(player, saveData);
        }

        ArrayList<PropertyDatum> propertyData = new ArrayList<>();
        for (Property property : properties) {
            if (property.getOwner() == null) {
                continue;
            }
            PropertyDatum propertyDatum = new PropertyDatum(property.getName(), playerDatumHashMap.get(property.getOwner()));
            propertyData.add(propertyDatum);
        }

        HashMap<PlayerDatum, BlockDatum> playerDatumBlockDatumHashMap = new HashMap<>();
        LocationDatum locationDatum = new LocationDatum(playerDatumBlockDatumHashMap);
        for (Player player : players) {
            BlockDatum blockDatum = new BlockDatum(playerLocation.getCurrentLocation(player).getName());
            playerDatumBlockDatumHashMap.put(playerDatumHashMap.get(player), blockDatum);
        }
        return new GameData(playerData, propertyData, locationDatum, round, playerDatumHashMap.get(currentPlayer), random);
    }

    public void load(GameSystem gameSystem, GameData gameData) {
        ArrayList<PlayerDatum> playerData = gameData.getPlayerData();
        ArrayList<PropertyDatum> propertyData = gameData.getPropertyData();
        LocationDatum locationDatum = gameData.getLocationDatum();

        // TODO: Add random in Game Data
        HashMap<PlayerDatum, Player> map = new HashMap<>();
        Random random = new Random(System.currentTimeMillis());
        players.clear();

        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.HEALTHY, Player.DEFAULT_AMOUNT);

        for (PlayerDatum playerDatum : playerData) {  // Load every player in save file
            Player player = playerFactory.make(playerDatum.getName(), playerDatum.getStatus(), playerDatum.getAmount());
            players.add(player);
            map.put(playerDatum, player);
        }

        // properties
        for (Property property : properties) { // Clear all owner
            property.reset();
        }

        for (PropertyDatum propertyDatum : propertyData) { // Load every property in save file
            for (Property property : properties) {
                if (property.getName().equals(propertyDatum.getName())) {
                    property.setOwner(map.get(propertyDatum.getOwner()));
                }
            }
        }

        // location
        for (PlayerDatum datum : locationDatum.getLocation().keySet()) { // Move every player to the destination.
            playerLocation.setStartLocation();
            playerLocation.moveTo(map.get(datum), locationDatum.getLocation().get(datum).getName());
        }

        gameSystem.setCurrentPlayer(map.get(gameData.getCurrentPlayer()));
        gameSystem.setRound(gameData.getRound());
        gameSystem.setRandom(gameData.getRandom());
    }
}
