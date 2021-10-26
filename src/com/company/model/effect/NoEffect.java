package com.company.model.effect;

public class NoEffect extends Effect implements OnLandEffect, OnEnterEffect {
    public static final String DEFAULT_NAME = "No Effect";
    private final String description;

    public NoEffect() {
        super(DEFAULT_NAME);
        this.description = DEFAULT_NAME;
    }

    public NoEffect(String description) {
        super(DEFAULT_NAME);
        this.description = description;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
    }

    @Override
    public void onEnter() {
    }

    public String getDescription() {
        return String.format("%s", description);
    }
}
