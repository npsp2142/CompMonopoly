package com.company.model.component;

import com.company.model.GameController;
import com.company.model.component.block.Block;
import com.company.model.observer.PlayerObserver;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Player {
    public static final int DEFAULT_AMOUNT = 1500;
    public static final int MAX_ROLL_VALUE = 4;
    public static boolean NEED_PROMPT = false;

    private final String name;
    private final Random random;
    private final List<PlayerObserver> playerObservers;

    private Status status;
    private int amount;
    private Response response;

    public Player(String name, Random random, List<PlayerObserver> playerObservers) {
        this.name = name;
        this.random = random;
        this.playerObservers = playerObservers;
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

// --Commented out by Inspection START (24/10/2021 17:19):
//    public void addSubscriber(PlayerObserver playerObserver){
//        playerObservers.add(playerObserver);
//    }
// --Commented out by Inspection STOP (24/10/2021 17:19)

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
