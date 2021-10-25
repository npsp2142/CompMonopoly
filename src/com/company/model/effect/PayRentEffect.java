package com.company.model.effect;

import com.company.model.observer.EffectObserver;

import java.util.List;

public class PayRentEffect extends Effect implements OnLandEffect {
    final GainMoneyEffect gainMoneyEffect;
    final LoseMoneyEffect loseMoneyEffect;

    public PayRentEffect(String name, List<EffectObserver> effectObservers, GainMoneyEffect gainMoneyEffect, LoseMoneyEffect loseMoneyEffect) {
        super(name, effectObservers);
        this.gainMoneyEffect = gainMoneyEffect;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    public void onLand() {
        notifyEffectSubscribers();
        gainMoneyEffect.onLand();
        loseMoneyEffect.onLand();
    }

    @Override
    public String getDescription() {
        return String.format("%s: Pay Rent", getColoredName());
    }
}
