package com.company.model.component.block;

import com.company.model.GameDisplay;
import com.company.model.effect.Describable;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.HashMap;
import java.util.Map;


/**
 * Block should be a Effect Factory.
 *
 * @see com.company.model.effect.Effect
 */
public abstract class Block implements OnLandBlock, OnEnterBlock, Describable {
    private final String name;
    private Map<String, BlockObserver> blockObservers;
    private Map<String, EffectObserver> effectObservers;

    public Block(String name) {
        this.name = name;
        blockObservers = new HashMap<>();
        effectObservers = new HashMap<>();
    }

    public void notifyBlockSubscribers(boolean isVerbose) {
        for (String name : blockObservers.keySet()
        ) {
            blockObservers.get(name).update(this, isVerbose);
        }
    }

    public Map<String, EffectObserver> getEffectObservers() {
        return effectObservers;
    }

    public void setEffectObservers(Map<String, EffectObserver> effectObservers) {
        this.effectObservers = effectObservers;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return GameDisplay.colorString(name, GameDisplay.ANSI_PURPLE);
    }

    public void setBlockObservers(Map<String, BlockObserver> blockObservers) {
        this.blockObservers = blockObservers;
    }

}
