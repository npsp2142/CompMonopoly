package com.company.model.effect;

import com.company.model.GameDisplay;
import com.company.model.component.block.Block;
import com.company.model.component.Location;
import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.Arrays;
import java.util.List;

public class MoveEffect extends Effect implements OnLandEffect, Describable {
    private final Player player;
    private final int[] steps;
    private final Location location;
    private OnLandEffect onLandEffect;

    public MoveEffect(String name, List<EffectObserver> effectObservers, Player player, int[] steps, Location location) {
        super(name, effectObservers);
        this.player = player;
        this.steps = steps;
        this.location = location;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        if (player.getStatus().equals(Player.Status.IN_JAIL)) {
            Block block = player.getCurrentLocation(location);
            onLandEffect = block.createOnLandEffect(player);
            onLandEffect.onLand();
            block.notifyBlockSubscribers(player);
            return;
        }

        location.moveStep(player, Arrays.stream(steps).sum());
    }

    @Override
    public String getDescription() {
        return String.format("%s: Move board",getColoredName());
       /* if (player.getStatus().equals(Player.Status.IN_JAIL)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this + ": ");
            for (int step : steps) {
                stringBuilder.append(String.format("%s roll %d.\n", player.getName(), step));
            }
            stringBuilder.append(onLandEffect.getDescription());
            stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());

            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this + ": ");
        for (int step : steps) {
            stringBuilder.append(String.format("%s move %d step. ", player.getName(), step));
        }
        stringBuilder.delete(stringBuilder.toString().length() - 1, stringBuilder.toString().length());
        return stringBuilder.toString();*/
    }
}
