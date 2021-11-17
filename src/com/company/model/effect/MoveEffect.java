package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;

import java.util.Arrays;

public class MoveEffect extends Effect implements OnLandEffect, Describable {
    private final Player player;
    private final int[] steps;
    private final PlayerLocation playerLocation;

    public MoveEffect(String name, Player player, int[] steps, PlayerLocation playerLocation) {
        super(name);
        this.player = player;
        this.steps = steps;
        this.playerLocation = playerLocation;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        if (player.getStatus().equals(Player.Status.GROUNDED)) { // if no move
            Block block = player.getCurrentLocation(playerLocation);
            OnLandEffect onLandEffect = block.createOnLandEffect(player);
            onLandEffect.triggerOnLand();
            block.notifyBlockSubscribers(true);
            return;
        }

        playerLocation.moveStep(player, Arrays.stream(steps).sum());
    }

    public String getDescription() {
        if (player.getStatus().equals(Player.Status.GROUNDED)) {
            return "Move not allowed";
        }
        assert (steps.length == 2);
        return String.format("You rolled %d and %d. Move %s steps.", steps[0], steps[1], Arrays.stream(steps).sum());
    }
}
