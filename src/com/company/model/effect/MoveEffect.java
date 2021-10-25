package com.company.model.effect;

import com.company.model.component.Location;
import com.company.model.component.Player;
import com.company.model.component.block.Block;
import com.company.model.observer.EffectObserver;

import java.util.Arrays;
import java.util.List;

public class MoveEffect extends Effect implements OnLandEffect, Describable {
    private final Player player;
    private final int[] steps;
    private final Location location;

    public MoveEffect(String name, List<EffectObserver> effectObservers, Player player, int[] steps, Location location) {
        super(name, effectObservers);
        this.player = player;
        this.steps = steps;
        this.location = location;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        if (player.getStatus().equals(Player.Status.GROUNDED)) {
            Block block = player.getCurrentLocation(location);
            OnLandEffect onLandEffect = block.createOnLandEffect(player);
            onLandEffect.onLand();
            block.notifyBlockSubscribers(player);
            return;
        }

        location.moveStep(player, Arrays.stream(steps).sum());
    }

    public String getDescription() {
        return String.format("%s: Move board", getColoredName());
    }
}
