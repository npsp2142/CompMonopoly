package com.company.model.effect;

/**
 * Player need to pay rent to owner when player stepped on owned land
 */
public class PayRentEffect extends Effect implements OnLandEffect {
    private final GainMoneyEffect gainMoneyEffect;
    private final LoseMoneyEffect loseMoneyEffect;

    /**
     * @param name the name of the effect
     * @param gainMoneyEffect the owner gain money from the player
     * @see GainMoneyEffect
     * @param loseMoneyEffect the player pay money to the owner
     * @see LoseMoneyEffect
     */
    public PayRentEffect(String name, GainMoneyEffect gainMoneyEffect, LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.gainMoneyEffect = gainMoneyEffect;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        gainMoneyEffect.triggerOnLand();
        loseMoneyEffect.triggerOnLand();
    }

    @Override
    public String getDescription() {
        return "You stepped on a property owned by others and pay Rent";
    }
}
