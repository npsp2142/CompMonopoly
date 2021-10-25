package com.company.model.effect;

import com.company.model.observer.EffectObserver;

import java.util.List;

public class NoEffect extends Effect implements OnLandEffect, OnEnterEffect {
    public static final String DEFAULT_NAME = "No Effect";
    private final String description;

    public NoEffect(List<EffectObserver> effectObservers) {
        super(DEFAULT_NAME, effectObservers);
        description = DEFAULT_NAME;
    }

    public NoEffect(String name, List<EffectObserver> effectObservers) {
        super(name, effectObservers);
        description = DEFAULT_NAME;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
    }

    @Override
    public void onEnter() {
    }

    public String getDescription() {
        return String.format("%s: %s", getColoredName(), description);
    }
}
