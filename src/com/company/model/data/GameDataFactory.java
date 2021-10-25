package com.company.model.data;

import com.company.model.GameSystem;
import com.company.model.component.Dice;
import com.company.model.component.Location;
import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.data.*;
import com.company.model.observer.PlayerObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameDataFactory {

    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final Location location;
    private final Integer round;
    private final Player currentPlayer;

    public GameDataFactory(ArrayList<Player> players, ArrayList<Property> properties, Location location, Integer round, Player currentPlayer) {
        this.players = players;
        this.properties = properties;
        this.location = location;
        this.round = round;
        this.currentPlayer = currentPlayer;
    }

    public GameData make(){
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
            BlockDatum blockDatum = new BlockDatum(location.getCurrentLocation(player).getName());
            playerDatumBlockDatumHashMap.put(playerDatumHashMap.get(player), blockDatum);
        }
        return new GameData(playerData, propertyData, locationDatum, round, playerDatumHashMap.get(currentPlayer));
    }

    public void load(GameSystem gameSystem, GameData gameData){
        ArrayList<PlayerDatum> playerData = gameData.getPlayerData();
        ArrayList<PropertyDatum> propertyData = gameData.getPropertyData();
        LocationDatum locationDatum = gameData.getLocationDatum();

        HashMap<PlayerDatum, Player> map = new HashMap<>();
        Random random = new Random(System.currentTimeMillis());
        Dice dice = new Dice(random, 4);
        ArrayList<PlayerObserver> observers = new ArrayList<>();
        players.clear();
        // player
        for (PlayerDatum playerDatum : playerData) {
            Player player = new Player(playerDatum.getName(), playerDatum.getStatus(), playerDatum.getAmount(),
                    dice,
                    observers);
            players.add(player);
            map.put(playerDatum, player);
        }

        // properties
        for (Property property : properties) {
            property.reload();
        }
        for (PropertyDatum propertyDatum : propertyData) {
            for (Property property : properties) {
                if (property.getName().equals(propertyDatum.getName())) {
                    property.setOwner(map.get(propertyDatum.getOwner()));
                }
            }
        }

        // location
        for (PlayerDatum datum : locationDatum.getLocation().keySet()) {
            location.setStartLocation(location.getStartBlock());
            location.moveTo(map.get(datum), locationDatum.getLocation().get(datum).getName());
        }
        gameSystem.setCurrentPlayer(map.get(gameData.getCurrentPlayer()));
        gameSystem.setRound(round);
    }
}
