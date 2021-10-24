package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.effect.*;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public class PropertyBlock extends Block implements OnLandBlock {
    private final Property property;
    private final ArrayList<BlockObserver> blockObservers;

    public PropertyBlock(String name, ArrayList<BlockObserver> blockObservers, Property property) {
        super(name, blockObservers);
        this.blockObservers = blockObservers;
        this.property = property;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        if (hasNoOwner()) {
            if (player.getAmount() < property.getPrice()) {
                return new NoEffect("No Money buy");
            }
            Player.Response response = player.getResponse("Buy Property?");
            if (response == Player.Response.YES) {
                return new BuyPropertyEffect(
                        "Buy Property",
                        player,
                        property,
                        new LoseMoneyEffect("Pay Ownership Fee", player, property.getPrice()));
            }
            return new NoEffect("This Time No Buy");
        }
        if (property.getOwner() == player) {
            return new NoEffect("You are owner");
        }
        return new PayRentEffect("Pay Rent",
                new GainMoneyEffect("Get Rent", property.getOwner(), property.getRent()),
                new LoseMoneyEffect("Pay Rent", player, property.getRent())
        );
    }

    public void notifySubscribers(Player player) {
        for (BlockObserver blockObserver : blockObservers
        ) {
            blockObserver.update(this, player);
        }
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
            return String.format("%s - Price: %d HKD - Rent: %d",
                    this,
                    property.getPrice(),
                    property.getRent());
        }
        return String.format("%s - %s - Rent: %d",
                this,
                property.getOwner(),
                property.getRent());
    }

    private boolean hasNoOwner() {
        return property.getOwner() == null;
    }
}
