package com.company.model.block;

import com.company.model.ANSI;
import com.company.model.component.Player;
import com.company.model.effect.Describable;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public abstract class Block implements OnLandBlock, OnEnterBlock, Describable {
    private final String name;
    private final ArrayList<BlockObserver> blockObservers;

    public Block(String name,ArrayList<BlockObserver> blockObservers){
        this.name = name;
        this.blockObservers = blockObservers;
    }

// --Commented out by Inspection START (24/10/2021 17:13):
//    public void addSubscriber(BlockObserver blockObserver){
//        blockObservers.add(blockObserver);
//    }
// --Commented out by Inspection STOP (24/10/2021 17:13)

    public void notifySubscribers(Player player){
        for (BlockObserver blockObserver : blockObservers
        ) {
            blockObserver.update(this,player );
        }
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return ANSI.ANSI_PURPLE + name + ANSI.ANSI_RESET;
    }
}
