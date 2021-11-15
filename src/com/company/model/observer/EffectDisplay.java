package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.effect.Effect;

public class EffectDisplay implements EffectObserver {
    public static final String DEFAULT_NAME = "Effect Display";
    private static EffectDisplay instance;

    public EffectDisplay() {
        if (instance == null) {
            instance = this;
        }
    }

    /**
     * Print effect description.
     *
     * @param effect the effect to be displayed
     */
    @Override
    public void update(Effect effect) {
        GameDisplay.infoMessage(effect.getColoredName() + " - " + effect.getDescription());
    }
}
