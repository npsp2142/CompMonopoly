package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public class JustVisitingOrInJailBlock extends ConditionalBlock {
    public JustVisitingOrInJailBlock(ArrayList<BlockObserver> blockObservers, NoEffectBlock noEffectBlock, InJailBlock inJailBlock) {
        super(noEffectBlock.getName() + " / " + inJailBlock.getName(), blockObservers, noEffectBlock, inJailBlock);
    }

    @Override
    public boolean GoTo(Player player) {
        return player.getStatus() == Player.Status.HEALTHY;
    }


}
