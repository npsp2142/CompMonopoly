package com.company.model.save;

import java.io.Serializable;

public class BlockSave implements Serializable {
    private final String name;

    public BlockSave(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
