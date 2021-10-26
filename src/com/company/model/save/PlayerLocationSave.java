package com.company.model.save;

import java.io.Serializable;
import java.util.Map;

public class PlayerLocationSave implements Serializable {
    private final Map<PlayerSave, BlockSave> location;

    public PlayerLocationSave(Map<PlayerSave, BlockSave> location) {
        this.location = location;
    }

    public Map<PlayerSave, BlockSave> getLocation() {
        return location;
    }
}
