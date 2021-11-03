package com.company.model.effect;

import com.company.model.component.Player;

public class GainMoneyEffect extends Effect implements OnLandEffect, OnEnterEffect {
    private final Player player;
    private final int amount;

    public GainMoneyEffect(String name, Player player, int amount) {
        super(name);
        this.player = player;
        this.amount = amount;
    }

    public void triggerOnLand() {
        player.addAmount(amount);
        notifyEffectSubscribers();
    }

    @Override
    public void triggerOnEnter() {
        player.addAmount(amount);
        notifyEffectSubscribers();
    }

    public String getDescription() {
        return String.format("%s gain %d HKD", player, amount);
    }
}
