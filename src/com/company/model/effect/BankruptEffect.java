package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class BankruptEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final List<Property> properties;

    public BankruptEffect(String name, List<EffectObserver> effectObservers, Player player, List<Property> properties) {
        super(name, effectObservers);
        this.player = player;
        this.properties = properties;
    }

    @Override
    public void onLand() {
        player.setBankruptStatus();
        for (Property property : properties) {
            if (property.getOwner() == null) {
                continue;
            }
            if (property.getOwner().equals(player)) {
                property.setOwner(null);
            }
        }
        notifyEffectSubscribers();
    }

    public String getDescription() {
        return String.format("%s - Trigger when no money in Turn End. Sell property to back healthy.", getColoredName());
    }
}
