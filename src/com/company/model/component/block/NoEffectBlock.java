package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

public class NoEffectBlock extends Block {
    public NoEffectBlock(String name, ArrayList<BlockObserver> blockObservers, List<EffectObserver> effectObservers) {
        super(name, blockObservers, effectObservers);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        return new NoEffect(getEffectObservers(), getName());
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getEffectObservers(), getName());
    }

    @Override
    public String getDescription() {
        return "No Effect";
    }
}
