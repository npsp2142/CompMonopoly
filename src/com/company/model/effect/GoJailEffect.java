package com.company.model.effect;

public class GoJailEffect extends Effect implements OnLandEffect{
    SetGroundedEffect setGroundedEffect;
    TeleportEffect teleportEffect;

    public GoJailEffect(String name, SetGroundedEffect setGroundedEffect, TeleportEffect teleportEffect) {
        super(name);
        this.setGroundedEffect = setGroundedEffect;
        this.teleportEffect = teleportEffect;
    }

    @Override
    public void onLand() {
        setGroundedEffect.onLand();
        teleportEffect.onLand();
    }

    @Override
    public String getDescription() {
        return String.format("%s: Go jail",this) + "\n" +
                setGroundedEffect.getDescription() + "\n" +
                teleportEffect.getDescription();
    }
}
