package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public class NoEffectBlock extends Block{
    public NoEffectBlock(String name, ArrayList<BlockObserver> blockObservers) {
        super(name, blockObservers);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        return new NoEffect(getName());
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getName());
    }

    @Override
    public String getDescription() {
        return this + " - No Effect";
    }
}
