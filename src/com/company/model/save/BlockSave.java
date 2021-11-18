package com.company.model.save;

import java.io.Serializable;

/**
 * The object for saving block
 */
public class BlockSave implements Serializable {
    private final String name;

    /**
     * @param name the name of the saved block
     */
    public BlockSave(String name) {
        this.name = name;
    }

    /**
     * @return the name of the saved block
     */
    public String getName() {
        return name;
    }
}
