package com.company.model.component.block;

import com.company.model.ANSI;
import com.company.model.component.Player;
import com.company.model.effect.Describable;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.List;


/**
 * Block should be a Effect Factory.
 *
 * @see com.company.model.effect.Effect
 */
public abstract class Block implements OnLandBlock, OnEnterBlock, Describable {
    private final String name;
    private final List<BlockObserver> blockObservers;
    private final List<EffectObserver> effectObservers;

    public Block(String name, List<BlockObserver> blockObservers, List<EffectObserver> effectObservers) {
        this.name = name;
        this.blockObservers = blockObservers;
        this.effectObservers = effectObservers;
    }

// --Commented out by Inspection START (24/10/2021 17:13):
//    public void addSubscriber(BlockObserver blockObserver){
//        blockObservers.add(blockObserver);
//    }
// --Commented out by Inspection STOP (24/10/2021 17:13)

    public void notifyBlockSubscribers(Player player) {
        for (BlockObserver blockObserver : blockObservers
        ) {
            blockObserver.update(this, player);
        }
    }

    public String getName() {
        return name;
    }

    public List<EffectObserver> getEffectObservers() {
        return effectObservers;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getColoredName() {
        return ANSI.ANSI_PURPLE + name + ANSI.ANSI_RESET;
    }
}
