package com.company.model.effect;

import com.company.model.component.Player;

public class CureEffect extends Effect implements OnLandEffect {
    private final Player player;

    public CureEffect(String name, Player player) {
        super(name);
        this.player = player;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        player.setStatus(Player.Status.NORMAL);
    }

    @Override
    public String getDescription() {
        return "You are healthy";
    }
}
