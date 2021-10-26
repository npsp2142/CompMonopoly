package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;

public class NoEffectBlock extends Block {
    public NoEffectBlock(String name) {
        super(name);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        NoEffect noEffect = new NoEffect(getName());
        noEffect.setEffectObservers(getEffectObservers());
        return noEffect;
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        NoEffect noEffect = new NoEffect(getName());
        noEffect.setEffectObservers(getEffectObservers());
        return noEffect;
    }

    @Override
    public String getDescription() {
        return "No Effect";
    }
}
