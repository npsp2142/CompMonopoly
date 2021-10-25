package com.company.model.effect;

import com.company.model.observer.EffectObserver;

import java.util.List;

public class GoJailEffect extends Effect implements OnLandEffect {
    final SetGroundedEffect setGroundedEffect;
    final TeleportEffect teleportEffect;

    public GoJailEffect(String name, List<EffectObserver> effectObservers, SetGroundedEffect setGroundedEffect, TeleportEffect teleportEffect) {
        super(name, effectObservers);
        this.setGroundedEffect = setGroundedEffect;
        this.teleportEffect = teleportEffect;
    }

    @Override
    public void onLand() {
        setGroundedEffect.onLand();
        teleportEffect.onLand();
        notifyEffectSubscribers();
    }

    public String getDescription() {
        return String.format("%s: Go jail", getColoredName());
    }
}
