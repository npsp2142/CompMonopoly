package com.company.model.component;

import com.company.model.GameController;
import com.company.model.block.Block;
import com.company.model.observer.PlayerObserver;

import java.io.Serializable;
import java.util.ArrayList;

public class Player {
    public static final int DEFAULT_AMOUNT = 1500;
    public static boolean NEED_PROMPT = false;
    private final String name;
    private final Dice dice;
    private final ArrayList<PlayerObserver> playerObservers;
    private Status status;
    private int amount;
    private Response response;

    public Player(String name, Status status, int amount, Dice dice,
                  ArrayList<PlayerObserver> playerObservers) {
        this.name = name;
        this.status = status;
        this.amount = amount;
        this.dice = dice;
        this.playerObservers = playerObservers;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void setJailStatus() {
        status = Status.IN_JAIL;
    }

    public void setHealthyStatus() {
        status = Status.HEALTHY;
    }

    public void setBankruptStatus() {
        status = Status.BANKRUPT;
    }

    public Status getStatus() {
        return status;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Block getCurrentLocation(Location location) {
        return location.getCurrentLocation(this);
    }

// --Commented out by Inspection START (24/10/2021 17:19):
//    public void addSubscriber(PlayerObserver playerObserver){
//        playerObservers.add(playerObserver);
//    }
// --Commented out by Inspection STOP (24/10/2021 17:19)

    public int[] roll(int times) {
        int[] result = new int[times];
        for (int i = 0; i < times; i++) {
            result[i] = dice.roll();
        }

        return result;
    }

    public void notifySubscribers() {
        for (PlayerObserver playerObserver : playerObservers
        ) {
            playerObserver.update(this);
        }
    }

    public void reload() {
        status = Status.HEALTHY;
        amount = DEFAULT_AMOUNT;
    }

    public Response getResponse(String prompt) {
        if (Player.NEED_PROMPT) {
            Response tempResponse = null;
            while (tempResponse == null) {
                tempResponse = GameController.instance.getResponse(prompt);
            }
            return tempResponse;
        }

        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public enum Status implements Serializable {
        HEALTHY, BANKRUPT, IN_JAIL
    }
    public enum Response {
        YES, NO
    }
}
