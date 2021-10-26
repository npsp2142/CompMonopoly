package com.company.model.component.block;

import com.company.model.component.Player;

public class JustVisitingOrInJailBlock extends ConditionalBlock {
    public JustVisitingOrInJailBlock(NoEffectBlock noEffectBlock, InJailBlock inJailBlock) {
        super(noEffectBlock.getName() + " / " + inJailBlock.getName(), noEffectBlock, inJailBlock);
    }

    @Override
    public boolean GoTo(Player player) {
        return player.getStatus() == Player.Status.HEALTHY;
    }


}
