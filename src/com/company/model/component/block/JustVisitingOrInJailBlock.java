package com.company.model.component.block;

import com.company.model.component.Player;

public class JustVisitingOrInJailBlock extends ConditionalBlock {
    public JustVisitingOrInJailBlock(NoEffectBlock noEffectBlock, JailBlock jailBlock) {
        super(noEffectBlock.getName() + " / " + jailBlock.getName(), noEffectBlock, jailBlock);
    }

    @Override
    public boolean GoTo(Player player) {
        return player.getStatus() == Player.Status.NORMAL;
    }


}
