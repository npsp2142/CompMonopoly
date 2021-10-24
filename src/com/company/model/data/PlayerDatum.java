package com.company.model.data;

import com.company.model.component.Player;

import java.io.Serializable;

public class PlayerDatum implements Serializable {
    private final String name;
    private final Player.Status status;
    private final Integer amount;

    public PlayerDatum(String name, Player.Status status, int amount) {
        this.name = name;
        this.status = status;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Player.Status getStatus() {
        return status;
    }

    public Integer getAmount() {
        return amount;
    }
}