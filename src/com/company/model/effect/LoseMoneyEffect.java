package com.company.model.effect;

import com.company.model.component.Player;

public class LoseMoneyEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final int amount;

    public LoseMoneyEffect(String name, Player player, int amount) {
        super(name);
        this.player = player;
        this.amount = amount;
    }

    public void triggerOnLand() {
        player.addAmount(-amount);
        notifyEffectSubscribers();
    }

    public String getDescription() {
        return String.format("%s lose %d HKD", player, amount);
    }
}
