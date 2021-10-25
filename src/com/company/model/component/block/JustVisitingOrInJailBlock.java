package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

public class JustVisitingOrInJailBlock extends ConditionalBlock {
    public JustVisitingOrInJailBlock(
            ArrayList<BlockObserver> blockObservers,
            List<EffectObserver> effectObservers,
            NoEffectBlock noEffectBlock,
            InJailBlock inJailBlock) {
        super(noEffectBlock.getName() + " / " +
                inJailBlock.getName(), blockObservers, effectObservers,noEffectBlock, inJailBlock);
    }

    @Override
    public boolean GoTo(Player player) {
        return player.getStatus() == Player.Status.HEALTHY;
    }


}
