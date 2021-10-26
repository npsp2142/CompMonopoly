package com.company.model.effect;

public class GoJailEffect extends Effect implements OnLandEffect {
    final SetGroundedEffect setGroundedEffect;
    final TeleportEffect teleportEffect;

    public GoJailEffect(String name, SetGroundedEffect setGroundedEffect, TeleportEffect teleportEffect) {
        super(name);
        this.setGroundedEffect = setGroundedEffect;
        this.teleportEffect = teleportEffect;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        setGroundedEffect.onLand();
        teleportEffect.onLand();
    }

    public String getDescription() {
        return "Go to jail immediately";
    }
}
