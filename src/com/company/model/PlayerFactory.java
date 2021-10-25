package com.company.model;

import com.company.model.component.Player;
import com.company.model.observer.PlayerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerFactory {
    private final List<String> names;
    private final Random random;
    private final List<PlayerObserver> playerObservers;
    private final Player.Status status;
    private final int amount;

    public PlayerFactory(List<String> names, Random random, List<PlayerObserver> playerObservers, Player.Status status, int amount) {
        this.names = names;
        this.random = random;
        this.playerObservers = playerObservers;
        this.status = status;
        this.amount = amount;
    }

    public ArrayList<Player> make() {
        ArrayList<Player> players = new ArrayList<>();
        for (String name : names) {
            Player player = new Player(name, random, playerObservers);
            player.setAmount(amount);
            player.setStatus(status);
            players.add(player);
        }
        return players;
    }
}
