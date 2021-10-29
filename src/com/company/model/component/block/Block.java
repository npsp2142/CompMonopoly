package com.company.model.component.block;

import com.company.model.ANSI;
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

    public void notifyBlockSubscribers() {
        for (String name : blockObservers.keySet()
        ) {
            blockObservers.get(name).update(this);
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
        return ANSI.ANSI_PURPLE + name + ANSI.ANSI_RESET;
    }

    public void setBlockObservers(Map<String, BlockObserver> blockObservers) {
        this.blockObservers = blockObservers;
    }

}
