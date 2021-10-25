package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.*;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChanceBlock extends Block {
    public static final int MAX_ADD = 200;
    public static final int MAX_REDUCE = 300;
    public static final String DEFAULT_EFFECT_NAME = "Chance";
    public static final float PROBABILITY_ADD = 0.5f;
    private final Random random;

    public ChanceBlock(String name,
                       ArrayList<BlockObserver> blockObservers,
                       List<EffectObserver> effectObservers, Random random) {
        super(name, blockObservers, effectObservers);
        this.random = random;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        boolean isAdd = random.nextFloat() < PROBABILITY_ADD;
        if (isAdd) {
            return new GainMoneyEffect(DEFAULT_EFFECT_NAME, getEffectObservers(), player, (random.nextInt() % MAX_ADD + 1) / 10 * 10);
        } else {
            return new LoseMoneyEffect(DEFAULT_EFFECT_NAME, getEffectObservers(), player, (random.nextInt() % MAX_REDUCE + 1) / 10 * 10);
        }

    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getEffectObservers());
    }

    @Override
    public String getDescription() {
        return String.format("Maximum Gain: %d - Maximum Lost: %d - Probability of Gain: %2.0f %%", MAX_ADD, MAX_REDUCE, PROBABILITY_ADD * 100);
    }
}
