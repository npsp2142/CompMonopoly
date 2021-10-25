package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class SetGroundedEffect extends Effect implements OnLandEffect {
    private final Player player;

    public SetGroundedEffect(String name, List<EffectObserver> effectObservers, Player player) {
        super(name, effectObservers);
        this.player = player;
    }

    @Override
    public void onLand() {
        player.setStatus(Player.Status.GROUNDED);
        notifyEffectSubscribers();
    }

    public String getDescription() {
        return String.format("%s cannot move", player);
    }
}
