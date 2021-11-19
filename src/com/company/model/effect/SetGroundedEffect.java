package com.company.model.effect;

import com.company.model.component.Player;

public class SetGroundedEffect extends Effect implements OnLandEffect {
    private final Player player;

    public SetGroundedEffect(String name, Player player) {
        super(name);
        this.player = player;
    }

    @Override
    public void triggerOnLand() {
        player.setStatus(Player.Status.GROUNDED);
        notifyEffectSubscribers();
    }

    @Override
    public String getDescription() {
        return String.format("%s cannot move", player);
    }
}
