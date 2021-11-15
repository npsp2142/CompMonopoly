package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.effect.*;

public class PropertyBlock extends Block {
    private final Property property;

    public PropertyBlock(String name, Property property) {
        super(name);
        this.property = property;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        if (hasNoOwner()) {
            if (player.getAmount() < property.getPrice()) {
                NoEffect noEffect = new NoEffect("No Money buy");
                noEffect.setEffectObservers(getEffectObservers());

                return noEffect;
            }
            Player.Response response = player.getResponse("Buy Property?");
            if (response == Player.Response.YES) {
                LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Pay Ownership Fee", player, property.getPrice());
                BuyPropertyEffect buyPropertyEffect = new BuyPropertyEffect("Buy Property", player, property, loseMoneyEffect);

                loseMoneyEffect.setEffectObservers(getEffectObservers());
                buyPropertyEffect.setEffectObservers(getEffectObservers());

                return buyPropertyEffect;
            }
            NoEffect noEffect = new NoEffect("This Time No Buy");
            noEffect.setEffectObservers(getEffectObservers());

            return noEffect;
        }
        if (property.getOwner() == player) {
            NoEffect noEffect = new NoEffect("You are owner");
            noEffect.setEffectObservers(getEffectObservers());

            return noEffect;
        }
        GainMoneyEffect gainMoneyEffect = new GainMoneyEffect("Get Rent", property.getOwner(), property.getRent());
        gainMoneyEffect.setEffectObservers(getEffectObservers());
        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Pay Rent", player, property.getRent());
        loseMoneyEffect.setEffectObservers(getEffectObservers());
        PayRentEffect payRentEffect = new PayRentEffect("Pay Rent", gainMoneyEffect, loseMoneyEffect);
        payRentEffect.setEffectObservers(getEffectObservers());
        return payRentEffect;
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }

    public Property getProperty() {
        return property;
    }

    @Override
    public String getDescription() {
        if (hasNoOwner()) {
            return String.format("Price: %d HKD - Rent: %d",
                    property.getPrice(),
                    property.getRent());
        }
        return String.format("Owner: %s - Rent: %d",
                property.getOwner(),
                property.getRent());
    }

    private boolean hasNoOwner() {
        return property.getOwner() == null;
    }
}
