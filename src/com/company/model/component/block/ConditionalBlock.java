package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;

/**
 * Conditional Block contains two block, visit one block each time depends on player status.
 */
public abstract class ConditionalBlock extends Block {
    private final Block blockA;
    private final Block blockB;

    /**
     * @param name name of the condition block
     * @param blockA the block that travels when goto is true
     * @param blockB the block that travels when goto is false
     */
    public ConditionalBlock(String name,
                            Block blockA, Block blockB) {
        super(name);
        this.blockA = blockA;
        this.blockB = blockB;
    }

    /**
     * @param player the player entered the conditional block
     * @return the block that the player travels to
     */
    public abstract boolean GoTo(Player player);

    public OnEnterEffect createOnEnterEffect(Player player) {
        if (blockA == null) {
            return null;
        }
        if (blockB == null) {
            return null;
        }
        if (GoTo(player)) {
            return blockA.createOnEnterEffect(player);
        } else {
            return blockB.createOnEnterEffect(player);
        }
    }

    public OnLandEffect createOnLandEffect(Player player) {
        if (blockA == null) {
            return null;
        }
        if (blockB == null) {
            return null;
        }
        if (GoTo(player)) {
            return blockA.createOnLandEffect(player);
        } else {
            return blockB.createOnLandEffect(player);
        }
    }

    @Override
    public String getDescription() {
        return "Either " + blockA.getDescription() + " or " + blockB.getDescription();
    }

    @Override
    public String getName() {
        return blockA.getName() + " / " + blockB.getName();
    }

    /**
     * @return the block that travels when goto is false
     */
    public Block getBlockB() {
        return blockB;
    }
}
