package com.company.model.effect;

import com.company.model.component.Player;

import java.util.List;

public class BankruptEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final List<AbandonPropertyEffect> abandonPropertyEffects;

    public BankruptEffect(String name, Player player, List<AbandonPropertyEffect> abandonPropertyEffects) {
        super(name);
        this.player = player;
        this.abandonPropertyEffects = abandonPropertyEffects;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        player.setBankruptStatus();
        for (AbandonPropertyEffect abandonPropertyEffect : abandonPropertyEffects) {
            abandonPropertyEffect.onLand();
        }
    }

    public String getDescription() {
        return String.format("%s has no money in Turn End.", player);
    }
}
