package com.company.model.effect;

import com.company.model.component.Player;

public class GainMoneyEffect extends Effect implements OnLandEffect, OnEnterEffect{
    private final Player player;
    private final int amount;
    public GainMoneyEffect(String name, Player player, int amount) {
        super(name);
        this.player = player;
        this.amount = amount;
    }

    public void onLand() {
        player.addAmount(amount);
    }

    @Override
    public void onEnter() {
        player.addAmount(amount);
    }

    @Override
    public String getDescription() {
        return String.format("%s: %s gain %d HKD",this,player,amount);
    }
}
