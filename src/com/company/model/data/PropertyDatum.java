package com.company.model.data;

import com.company.model.component.Player;

import java.io.Serializable;

public class PropertyDatum implements Serializable {
    private final String name;
    private final PlayerDatum owner;

    public PropertyDatum(String name, PlayerDatum owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public PlayerDatum getOwner() {
        return owner;
    }
}
