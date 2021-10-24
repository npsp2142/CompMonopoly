package com.company.model;

import com.company.model.data.LocationDatum;
import com.company.model.data.PlayerDatum;
import com.company.model.data.PropertyDatum;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveSystem implements Serializable {
    private final ArrayList<PlayerDatum> playerData;
    private final ArrayList<PropertyDatum> propertyData;
    private final LocationDatum locationDatum;
    private final Integer round;
    private final PlayerDatum currentPlayer;

    public SaveSystem(ArrayList<PlayerDatum> playerData,
                      ArrayList<PropertyDatum> propertyDatumArrayList,
                      LocationDatum locationDatum, Integer round, PlayerDatum currentPlayer) {
        this.playerData = playerData;
        this.propertyData = propertyDatumArrayList;
        this.locationDatum = locationDatum;
        this.round = round;
        this.currentPlayer = currentPlayer;
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

    public Integer getRound() {
        return round;
    }

    public PlayerDatum getCurrentPlayer() {
        return currentPlayer;
    }
}
