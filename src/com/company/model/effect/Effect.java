package com.company.model.effect;

import com.company.model.ANSI;
import com.company.model.observer.EffectObserver;

import java.util.List;

/**
 * Concrete class should change component in some way.
 *
 * @see com.company.model.component
 */
public abstract class Effect implements Describable {
    private final String name;
    private final List<EffectObserver> effectObservers;

    protected Effect(String name, List<EffectObserver> effectObservers) {
        this.name = name;
        this.effectObservers = effectObservers;
    }

    @Override
    public String toString() {
        return name;
    }

    public void notifyEffectSubscribers() {
        for (EffectObserver effectObserver : effectObservers) {
            effectObserver.update(this);
        }
    }

    public String getColoredName() {
        return ANSI.ANSI_YELLOW + name + ANSI.ANSI_RESET;
    }
}
