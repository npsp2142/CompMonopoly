package com.company.model.effect;

import com.company.model.block.Block;
import com.company.model.component.Location;
import com.company.model.component.Player;

public class TeleportEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final Location location;
    private final Block block;
    private final boolean triggerOnLand;

    public TeleportEffect(String name, Player player, Location location, Block block, boolean triggerOnLand) {
        super(name);
        this.player = player;
        this.location = location;
        this.block = block;
        this.triggerOnLand = triggerOnLand;
    }

    @Override
    public void onLand() {
        location.moveTo(player, block, triggerOnLand);
    }


    @Override
    public String getDescription() {
        return String.format("%s: %s teleport to %s", this, player, block);
    }
}
