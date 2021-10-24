package com.company.model.effect;

import com.company.model.component.Player;

public class LoseMoneyEffect extends Effect implements OnLandEffect{
    private Player player;
    private int amount;
    public LoseMoneyEffect(String name, Player player, int amount) {
        super(name);
        this.player = player;
        this.amount = amount;
    }

    public void onLand() {
        player.addAmount(-amount);
    }

    @Override
    public String getDescription() {
        return String.format("%s: %s lose %d HKD",this,player,amount);
    }
}
