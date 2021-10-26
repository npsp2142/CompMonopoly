package com.company.model.component;

import com.company.model.GameController;
import com.company.model.component.block.Block;
import com.company.model.observer.PlayerObserver;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;

public class Player {
    public static final int DEFAULT_AMOUNT = 1500;
    public static final int MAX_ROLL_VALUE = 4;
    public static boolean NEED_PROMPT = false;

    private final String name;

    private Random random;
    private Map<String, PlayerObserver> playerObservers;
    private Status status;
    private int amount;
    private Response response;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void setBankruptStatus() {
        status = Status.BANKRUPT;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Block getCurrentLocation(PlayerLocation playerLocation) {
        return playerLocation.getCurrentLocation(this);
    }

    public int[] roll(int times) {
        int[] result = new int[times];
        for (int i = 0; i < times; i++) {
            result[i] = random.nextInt(MAX_ROLL_VALUE) + 1;
        }

        return result;
    }

    public void notifySubscribers() {
        if (playerObservers == null) {
            return;
        }
        for (String name : playerObservers.keySet()) {
            playerObservers.get(name).update(this);
        }
    }

    public void reset() {
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

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setPlayerObservers(Map<String, PlayerObserver> playerObservers) {
        this.playerObservers = playerObservers;
    }


    /**
     *
     */
    public enum Status implements Serializable {
        /**
         * Player are healthy.
         */
        HEALTHY
        /*
          Player cannot play
         */, BANKRUPT
        /*
          Player cannot move
         */, GROUNDED
    }

    public enum Response {
        YES, NO
    }
}
