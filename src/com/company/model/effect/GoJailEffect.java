package com.company.model.effect;

/**
 * Player moves to jail when he stepped on GO TO JAIL block
 */
public class GoJailEffect extends Effect implements OnLandEffect {

    private final SetGroundedEffect setGroundedEffect;
    private final TeleportEffect teleportEffect;

    /**
     * @param name the name of the effect
     * @param setGroundedEffect player is set to cannot move for three turns
     * @see SetGroundedEffect
     * @param teleportEffect player is teleported to IN JAIL block
     * @see TeleportEffect
     */
    public GoJailEffect(String name, SetGroundedEffect setGroundedEffect, TeleportEffect teleportEffect) {
        super(name);
        this.setGroundedEffect = setGroundedEffect;
        this.teleportEffect = teleportEffect;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        setGroundedEffect.triggerOnLand();
        teleportEffect.triggerOnLand();
    }

    @Override
    public String getDescription() {
        return "Go to jail immediately";
    }
}
