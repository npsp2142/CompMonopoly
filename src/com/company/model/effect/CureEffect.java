package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class CureEffect extends Effect implements OnLandEffect {
    private final Player player;

    public CureEffect(String name, List<EffectObserver> effectObservers, Player player) {
        super(name, effectObservers);
        this.player = player;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        player.setHealthyStatus();
    }

    @Override
    public String getDescription() {
        return String.format("%s: You are healthy", getColoredName());
    }
}
