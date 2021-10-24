package com.company.model.data;

import java.io.Serializable;

public class BlockDatum implements Serializable {
    private final String name;

    public BlockDatum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
