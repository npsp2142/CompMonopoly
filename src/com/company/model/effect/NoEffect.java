package com.company.model.effect;

public class NoEffect extends Effect implements OnLandEffect, OnEnterEffect {
    private final String description;
    public NoEffect(){
        super(DEFAULT_NAME);
        description = DEFAULT_NAME;
    }
    public NoEffect(String name) {
        super(name);
        description = DEFAULT_NAME;
    }
    public NoEffect(String name,String description) {
        super(name);
        this.description = description;
    }

    @Override
    public void onLand() {
    }


    @Override
    public void onEnter() {
    }

    @Override
    public String getDescription() {
        return String.format("%s: %s",this,description) ;
    }

    public static String DEFAULT_NAME = "No Effect";
}
