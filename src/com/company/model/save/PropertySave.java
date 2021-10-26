package com.company.model.save;

import java.io.Serializable;

public class PropertySave implements Serializable {
    private final String name;
    private final PlayerSave owner;

    public PropertySave(String name, PlayerSave owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public PlayerSave getOwner() {
        return owner;
    }

}
