package com.company.model.effect;

public class PayRentEffect extends Effect implements OnLandEffect{
    final GainMoneyEffect gainMoneyEffect;
    final LoseMoneyEffect loseMoneyEffect;
    public PayRentEffect(String name,GainMoneyEffect gainMoneyEffect, LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.gainMoneyEffect = gainMoneyEffect;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    public void onLand() {
        gainMoneyEffect.onLand();
        loseMoneyEffect.onLand();
    }

    @Override
    public String getDescription() {
        return gainMoneyEffect.getDescription() + "\n" + loseMoneyEffect.getDescription();
    }
}
