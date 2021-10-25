package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class ConditionalBlock extends Block {
    private final Block blockA;
    private final Block blockB;

    // BlockA true, BlockB false
    public ConditionalBlock(String name,
                            ArrayList<BlockObserver> blockObservers,
                            List<EffectObserver> effectObservers, Block blockA, Block blockB) {
        super(name, blockObservers, effectObservers);
        this.blockA = blockA;
        this.blockB = blockB;
    }

    // BlockA true, BlockB false
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
        return "Either " + blockA.getDescription() + " or " + blockB.getDescription();    }
}
