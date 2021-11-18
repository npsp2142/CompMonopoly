package com.company.model.effect;

import com.company.model.component.Player;

import java.util.List;

/**
 * When player gets negative money, he will bankrupt.
 */
public class BankruptEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final List<AbandonPropertyEffect> abandonPropertyEffects;

    /**
     * @param name the name of the effect
     * @param player the player to be bankrupt
     * @param abandonPropertyEffects to set property to unowned
     * @see AbandonPropertyEffect
     */
    public BankruptEffect(String name, Player player, List<AbandonPropertyEffect> abandonPropertyEffects) {
        super(name);
        this.player = player;
        this.abandonPropertyEffects = abandonPropertyEffects;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        player.setBankruptStatus();
        for (AbandonPropertyEffect abandonPropertyEffect : abandonPropertyEffects) {
            abandonPropertyEffect.triggerOnLand();
        }
    }

    public String getDescription() {
        return String.format("%s has no money in Turn End.", player);
    }
}
