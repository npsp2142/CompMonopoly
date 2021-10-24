package com.company.model.effect;

import com.company.model.component.Player;

public class SetGroundedEffect extends Effect implements OnLandEffect {
    private final Player player;

    public SetGroundedEffect(String name, Player player) {
        super(name);
        this.player = player;
    }

    @Override
    public void onLand() {
        player.setJailStatus();
    }

    @Override
    public String getDescription() {
        return String.format("%s: %s cannot move", this, player);
    }
}
