package com.company.model.data;

import java.io.Serializable;
import java.util.Map;

public class LocationDatum implements Serializable {
    private final Map<PlayerDatum, BlockDatum> location;

    public LocationDatum(Map<PlayerDatum, BlockDatum> location) {
        this.location = location;
    }

    public Map<PlayerDatum, BlockDatum> getLocation() {
        return location;
    }
}
