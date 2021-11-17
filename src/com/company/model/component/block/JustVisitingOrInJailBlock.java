package com.company.model.component.block;

import com.company.model.component.Player;

public class JustVisitingOrInJailBlock extends ConditionalBlock {
    public static final String JUST_VISITING_DEFAULT_NAME = "Just Visiting";
    public static final String IN_JAIL_DEFAULT_NAME = "In Jail";
    public static final String DEFAULT_NAME = JUST_VISITING_DEFAULT_NAME + " / " + IN_JAIL_DEFAULT_NAME;
    public JustVisitingOrInJailBlock(String name,NoEffectBlock noEffectBlock, JailBlock jailBlock) {
        super(name, noEffectBlock, jailBlock);
    }

    @Override
    public boolean GoTo(Player player) {
        return player.getStatus() == Player.Status.NORMAL;
    }

    public JailBlock getJailBlock() {
        return (JailBlock) super.getBlockB();
    }
}
