package com.company.model.save;

import com.company.model.component.Player;

import java.io.Serializable;

public class PlayerSave implements Serializable {
    private final String name;
    private final Player.Status status;
    private final Integer amount;

    public PlayerSave(String name, Player.Status status, int amount) {
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
