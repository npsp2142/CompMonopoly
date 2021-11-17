package com.company.model.effect;

import com.company.model.GameDisplay;
import com.company.model.observer.EffectObserver;

import java.util.Map;

/**
 * Concrete class should change component in some way.
 *
 * @see com.company.model.component
 */
public abstract class Effect implements Describable {
    private final String name;
    private Map<String, EffectObserver> effectObservers;

    protected Effect(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void notifyEffectSubscribers() {
        if (effectObservers == null) {
            return;
        }
        for (String name : effectObservers.keySet()) {
            effectObservers.get(name).update(this);
        }
    }

    public String getColoredName() {
        return GameDisplay.colorString(name, GameDisplay.ANSI_YELLOW);
    }

    public void setEffectObservers(Map<String, EffectObserver> effectObservers) {
        this.effectObservers = effectObservers;
    }

}
