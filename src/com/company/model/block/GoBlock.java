package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.effect.GainMoneyEffect;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public class GoBlock extends Block{

    public GoBlock(String name, ArrayList<BlockObserver> blockObservers) {
        super(name, blockObservers);
    }

    public OnEnterEffect createOnEnterEffect(Player player) {
        return new GainMoneyEffect(DEFAULT_NAME,player,SALARY);
    }

    public static final int SALARY = 1500;
    public static final String DEFAULT_NAME = "Go Effect";

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        return new NoEffect();
    }

    @Override
    public String getDescription() {
        return this + " - Gain: " + SALARY + " HKD";
    }
}
