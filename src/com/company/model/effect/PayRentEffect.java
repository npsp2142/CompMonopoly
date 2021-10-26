package com.company.model.effect;

public class PayRentEffect extends Effect implements OnLandEffect {
    final GainMoneyEffect gainMoneyEffect;
    final LoseMoneyEffect loseMoneyEffect;

    public PayRentEffect(String name, GainMoneyEffect gainMoneyEffect, LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.gainMoneyEffect = gainMoneyEffect;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    public void onLand() {
        notifyEffectSubscribers();
        gainMoneyEffect.onLand();
        loseMoneyEffect.onLand();
    }

    public String getDescription() {
        return "You step on a property owned by others and pay Rent";
    }
}
