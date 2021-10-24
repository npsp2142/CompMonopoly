package com.company.model.effect;

import com.company.model.ANSI;

public abstract class Effect{
    private final String name;
    protected Effect(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return ANSI.ANSI_YELLOW + name + ANSI.ANSI_RESET;
    }
}
