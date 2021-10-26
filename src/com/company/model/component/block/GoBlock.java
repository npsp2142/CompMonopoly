package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.GainMoneyEffect;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;

public class GoBlock extends Block {

    public static final int SALARY = 1500;
    public static final String DEFAULT_NAME = "Go Effect";

    public GoBlock(String name) {
        super(name);
    }

    public OnEnterEffect createOnEnterEffect(Player player) {
        GainMoneyEffect gainMoneyEffect = new GainMoneyEffect(DEFAULT_NAME, player, SALARY);
        gainMoneyEffect.setEffectObservers(getEffectObservers());
        return gainMoneyEffect;
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        return new NoEffect();
    }

    @Override
    public String getDescription() {
        return "Gain: " + SALARY + " HKD";
    }
}
