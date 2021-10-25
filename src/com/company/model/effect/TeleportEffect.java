package com.company.model.effect;

import com.company.model.component.PlayerLocation;
import com.company.model.component.Player;
import com.company.model.component.block.Block;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class TeleportEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final PlayerLocation playerLocation;
    private final Block block;
    private final boolean triggerOnLand;

    public TeleportEffect(String name, List<EffectObserver> effectObservers,
                          Player player, PlayerLocation playerLocation, Block block, boolean triggerOnLand) {
        super(name, effectObservers);
        this.player = player;
        this.playerLocation = playerLocation;
        this.block = block;
        this.triggerOnLand = triggerOnLand;
    }

    @Override
    public void onLand() {
        playerLocation.moveTo(player, block, triggerOnLand);
        notifyEffectSubscribers();
    }


    public String getDescription() {
        return String.format("%s: %s teleport to %s", getColoredName(), player, block);
    }
}
