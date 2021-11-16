package com.company.model.effect;

public class PayRentEffect extends Effect implements OnLandEffect {
    private final GainMoneyEffect gainMoneyEffect;
    private final LoseMoneyEffect loseMoneyEffect;

    public PayRentEffect(String name, GainMoneyEffect gainMoneyEffect, LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.gainMoneyEffect = gainMoneyEffect;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    public void triggerOnLand() {
        notifyEffectSubscribers();
        gainMoneyEffect.triggerOnLand();
        loseMoneyEffect.triggerOnLand();
    }

    public String getDescription() {
        return "You stepped on a property owned by others and pay Rent";
    }
}
