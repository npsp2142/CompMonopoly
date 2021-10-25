package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class LoseMoneyEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final int amount;

    public LoseMoneyEffect(String name, List<EffectObserver> effectObservers, Player player, int amount) {
        super(name, effectObservers);
        this.player = player;
        this.amount = amount;
    }

    public void onLand() {
        player.addAmount(-amount);
        notifyEffectSubscribers();
    }

    public String getDescription() {
        return String.format("%s lose %d HKD", player, amount);
    }
}
