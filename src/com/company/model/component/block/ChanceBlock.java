package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.*;

import java.util.Random;

/**
 * The block of Chance
 */
public class ChanceBlock extends Block {
    /**
     * The maximum gain of money per visit
     */
    public static final int MAX_ADD = 200;
    /**
     * The maximum lose of money per visit
     */
    public static final int MAX_REDUCE = 300;
    /**
     *
     */
    public static final String DEFAULT_EFFECT_NAME = "Chance";
    public static final float PROBABILITY_ADD = 0.5f;
    private final Random random;

    public ChanceBlock(String name,
                       Random random) {
        super(name);
        this.random = random;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        boolean isAdd = random.nextFloat() < PROBABILITY_ADD;
        if (isAdd) {
            GainMoneyEffect gainMoneyEffect = new GainMoneyEffect(
                    DEFAULT_EFFECT_NAME,
                    player,
                    (random.nextInt() % MAX_ADD + 1) / 10 * 10
            );
            gainMoneyEffect.setEffectObservers(getEffectObservers());
            return gainMoneyEffect;
        } else {
            LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect(
                    DEFAULT_EFFECT_NAME,
                    player,
                    Math.abs((random.nextInt() % MAX_REDUCE + 1) / 10 * 10)
            );
            loseMoneyEffect.setEffectObservers(getEffectObservers());
            return loseMoneyEffect;
        }
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }

    @Override
    public String getDescription() {
        return String.format("Maximum Gain: %d - Maximum Lost: %d - Probability of Gain: %2.0f %%",
                MAX_ADD, MAX_REDUCE, PROBABILITY_ADD * 100);
    }
}
