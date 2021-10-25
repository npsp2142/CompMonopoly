package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class GainMoneyEffect extends Effect implements OnLandEffect, OnEnterEffect {
    private final Player player;
    private final int amount;

    public GainMoneyEffect(String name, List<EffectObserver> effectObservers, Player player, int amount) {
        super(name, effectObservers);
        this.player = player;
        this.amount = amount;
    }

    public void onLand() {
        player.addAmount(amount);
        notifyEffectSubscribers();
    }

    @Override
    public void onEnter() {
        player.addAmount(amount);
        notifyEffectSubscribers();
    }

    @Override
    public String getDescription() {
        return String.format("%s: %s gain %d HKD", getColoredName(), player, amount);
    }
}
