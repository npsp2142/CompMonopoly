package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;

public class TeleportEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final PlayerLocation playerLocation;
    private final Block block;
    private final boolean triggerOnLand;

    public TeleportEffect(String name,
                          Player player, PlayerLocation playerLocation, Block block, boolean triggerOnLand) {
        super(name);
        this.player = player;
        this.playerLocation = playerLocation;
        this.block = block;
        this.triggerOnLand = triggerOnLand;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        playerLocation.moveTo(player, block, triggerOnLand);
    }


    public String getDescription() {
        return String.format("%s teleport to %s", player, block);
    }
}
