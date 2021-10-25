package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.effect.*;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class PropertyBlock extends Block {
    private final Property property;

    public PropertyBlock(String name, List<BlockObserver> blockObservers, List<EffectObserver> effectObservers, Property property) {
        super(name, blockObservers, effectObservers);
        this.property = property;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        if (hasNoOwner()) {
            if (player.getAmount() < property.getPrice()) {
                return new NoEffect(getEffectObservers(), "No Money buy");
            }
            Player.Response response = player.getResponse("Buy Property?");
            if (response == Player.Response.YES) {
                return new BuyPropertyEffect(
                        "Buy Property", getEffectObservers(),
                        player,
                        property,
                        new LoseMoneyEffect("Pay Ownership Fee", getEffectObservers(), player, property.getPrice()));
            }
            return new NoEffect(getEffectObservers(), "This Time No Buy");
        }
        if (property.getOwner() == player) {
            return new NoEffect(getEffectObservers(), "You are owner");
        }
        return new PayRentEffect("Pay Rent", getEffectObservers(),
                new GainMoneyEffect("Get Rent", getEffectObservers(), property.getOwner(), property.getRent()),
                new LoseMoneyEffect("Pay Rent", getEffectObservers(), player, property.getRent())
        );
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getEffectObservers());
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
        return String.format("%s - Rent: %d",
                property.getOwner(),
                property.getRent());
    }

    private boolean hasNoOwner() {
        return property.getOwner() == null;
    }
}
