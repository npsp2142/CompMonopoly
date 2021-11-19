package com.company.model.effect;

/**
 * effect that create nothing happened
 */
public class NoEffect extends Effect implements OnLandEffect, OnEnterEffect {
    /**
     * the default name of the effect
     */
    public static final String DEFAULT_NAME = "No Effect";
    private final String description;

    /**
     * the constructor of no effect
     */
    public NoEffect() {
        super(DEFAULT_NAME);
        this.description = DEFAULT_NAME;
    }

    /**
     * @param description the description shown in the command line interface
     */
    public NoEffect(String description) {
        super(DEFAULT_NAME);
        this.description = description;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
    }

    @Override
    public void triggerOnEnter() {
    }

    @Override
    public String getDescription() {
        return String.format("%s", description);
    }
}
