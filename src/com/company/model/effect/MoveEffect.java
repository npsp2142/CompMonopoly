package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;
import com.company.model.observer.EffectObserver;

import java.util.Arrays;
import java.util.List;

public class MoveEffect extends Effect implements OnLandEffect, Describable {
    private final Player player;
    private final int[] steps;
    private final PlayerLocation playerLocation;

    public MoveEffect(String name, List<EffectObserver> effectObservers, Player player, int[] steps, PlayerLocation playerLocation) {
        super(name, effectObservers);
        this.player = player;
        this.steps = steps;
        this.playerLocation = playerLocation;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        if (player.getStatus().equals(Player.Status.GROUNDED)) {
            Block block = player.getCurrentLocation(playerLocation);
            OnLandEffect onLandEffect = block.createOnLandEffect(player);
            onLandEffect.onLand();
            block.notifyBlockSubscribers(player);
            return;
        }

        playerLocation.moveStep(player, Arrays.stream(steps).sum());
    }

    public String getDescription() {
        if (player.getStatus().equals(Player.Status.GROUNDED)) {
            return "Move not allowed";
        }
        return String.format("Move %s steps", Arrays.stream(steps).sum());
    }
}
