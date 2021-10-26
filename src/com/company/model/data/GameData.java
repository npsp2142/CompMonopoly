package com.company.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameData implements Serializable {
    private final ArrayList<PlayerDatum> playerData;
    private final ArrayList<PropertyDatum> propertyData;
    private final LocationDatum locationDatum;
    private final Integer round;
    private final PlayerDatum currentPlayer;
    private final Random random;

    public GameData(ArrayList<PlayerDatum> playerData,
                    ArrayList<PropertyDatum> propertyDatumArrayList,
                    LocationDatum locationDatum, Integer round, PlayerDatum currentPlayer, Random random) {
        this.playerData = playerData;
        this.propertyData = propertyDatumArrayList;
        this.locationDatum = locationDatum;
        this.round = round;
        this.currentPlayer = currentPlayer;
        this.random = random;
    }

    public ArrayList<PlayerDatum> getPlayerData() {
        return playerData;
    }

    public ArrayList<PropertyDatum> getPropertyData() {
        return propertyData;
    }

    public LocationDatum getLocationDatum() {
        return locationDatum;
    }

    public PlayerDatum getCurrentPlayer() {
        return currentPlayer;
    }

    public Integer getRound() {
        return round;
    }

    public Random getRandom() {
        return random;
    }
}
