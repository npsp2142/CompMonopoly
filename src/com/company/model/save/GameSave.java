package com.company.model.save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameSave implements Serializable {
    private final ArrayList<PlayerSave> playerSaves;
    private final ArrayList<PropertySave> propertySaves;
    private final PlayerLocationSave playerLocationSave;
    private final Integer round;
    private final PlayerSave currentPlayer;
    private final Random random;
    private final ArrayList<Integer> roundCounter;
    private Map<BlockSave, Integer> blockVisit;
    private Map<PlayerSave, List<BlockSave>> path;

    public GameSave(ArrayList<PlayerSave> playerSaves,
                    ArrayList<PropertySave> propertySaves,
                    PlayerLocationSave playerLocationSave,
                    Integer round,
                    PlayerSave currentPlayer,
                    Random random, ArrayList<Integer> roundCounter) {
        this.playerSaves = playerSaves;
        this.propertySaves = propertySaves;
        this.playerLocationSave = playerLocationSave;
        this.round = round;
        this.currentPlayer = currentPlayer;
        this.random = random;
        this.roundCounter = roundCounter;
    }

    public ArrayList<PlayerSave> getPlayerSaves() {
        return playerSaves;
    }

    public ArrayList<PropertySave> getPropertySaves() {
        return propertySaves;
    }

    public PlayerLocationSave getPlayerLocationSave() {
        return playerLocationSave;
    }

    public PlayerSave getCurrentPlayer() {
        return currentPlayer;
    }

    public Integer getRound() {
        return round;
    }

    public Random getRandom() {
        return random;
    }

    public Map<PlayerSave, List<BlockSave>> getPath() {
        return path;
    }

    public ArrayList<Integer> getRoundCounter() {
        return roundCounter;
    }

    public void setPath(Map<PlayerSave, List<BlockSave>> path) {
        this.path = path;
    }

    public Map<BlockSave, Integer> getBlockVisit() {
        return blockVisit;
    }

    public void setBlockVisit(Map<BlockSave, Integer> blockVisit) {
        this.blockVisit = blockVisit;
    }

}
