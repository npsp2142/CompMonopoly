package com.company.model.effect;

import com.company.model.ANSI;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

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

    public void notifyEffectSubscribers(){
       for(EffectObserver effectObserver:effectObservers){
           effectObserver.update(this);
       }
    }

    public String getColoredName(){
        return ANSI.ANSI_YELLOW + name + ANSI.ANSI_RESET;
    }
}
