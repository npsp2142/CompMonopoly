package com.company.model.effect;

import com.company.model.component.Player;

public class CureEffect extends Effect implements OnLandEffect {
    private final Player player;

    public CureEffect(String name, Player player) {
        super(name);
        this.player = player;
    }

    @Override
    public void onLand() {
        player.setHealthyStatus();
    }

    @Override
    public String getDescription() {
        return String.format("%s: You are healthy", this);
    }
}
