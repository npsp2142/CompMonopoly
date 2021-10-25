package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.GainMoneyEffect;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

public class GoBlock extends Block {

    public static final int SALARY = 1500;
    public static final String DEFAULT_NAME = "Go Effect";

    public GoBlock(String name, ArrayList<BlockObserver> blockObservers, List<EffectObserver> effectObservers) {
        super(name, blockObservers, effectObservers);
    }

    public OnEnterEffect createOnEnterEffect(Player player) {
        return new GainMoneyEffect(DEFAULT_NAME, getEffectObservers(), player, SALARY);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        return new NoEffect(getEffectObservers());
    }

    @Override
    public String getDescription() {
        return "Gain: " + SALARY + " HKD";
    }
}
