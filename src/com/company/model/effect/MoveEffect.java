package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Location;
import com.company.model.block.Block;

import java.util.Arrays;

public class MoveEffect extends Effect implements OnLandEffect,Describable{
    private final Player player;
    private final int[] steps;
    private final Location location;
    private OnLandEffect onLandEffect;
    public MoveEffect(String name, Player player, int[] steps, Location location) {
        super(name);
        this.player = player;
        this.steps = steps;
        this.location = location;
    }

    @Override
    public void onLand() {
        if(player.getStatus().equals(Player.Status.IN_JAIL)){
            Block block = player.getCurrentLocation(location);
            onLandEffect = block.createOnLandEffect(player);
            onLandEffect.onLand();
            block.notifySubscribers(player);
            return;
        }

        location.moveStep(player, Arrays.stream(steps).sum());
    }

    @Override
    public String getDescription() {
        if(player.getStatus().equals(Player.Status.IN_JAIL)){
            StringBuilder stringBuilder = new StringBuilder();
            for (int step : steps) {
                stringBuilder.append(String.format("%s roll %d.\n", player.getName(), step));
            }
            stringBuilder.append(onLandEffect.getDescription());

            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int step : steps) {
            stringBuilder.append(String.format("%s move %d step.\n", player.getName(), step));
        }
        return stringBuilder.toString();
    }
}
