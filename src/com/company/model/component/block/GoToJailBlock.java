package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.effect.*;

public class GoToJailBlock extends Block {
    public static final String DEFAULT_NAME = "Go To Jail";
    private final PlayerLocation playerLocation;
    private final JustVisitingOrInJailBlock justVisitingOrInJailBlock;


    public GoToJailBlock(String name,
                         PlayerLocation playerLocation,
                         JustVisitingOrInJailBlock justVisitingOrInJailBlock) {
        super(name);
        this.justVisitingOrInJailBlock = justVisitingOrInJailBlock;
        this.playerLocation = playerLocation;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        SetGroundedEffect setGroundedEffect = new SetGroundedEffect("Grounded", player);
        TeleportEffect teleportEffect = new TeleportEffect(
                DEFAULT_NAME,
                player,
                playerLocation,
                justVisitingOrInJailBlock,
                false
        );

        setGroundedEffect.setEffectObservers(getEffectObservers());
        teleportEffect.setEffectObservers(getEffectObservers());

        GoJailEffect goJailEffect = new GoJailEffect(
                DEFAULT_NAME,
                setGroundedEffect,
                teleportEffect
        );
        goJailEffect.setEffectObservers(getEffectObservers());
        return goJailEffect;
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }

    @Override
    public String getDescription() {
        return String.format("Go to %s and immediately end turn", justVisitingOrInJailBlock.getColoredName());
    }
}
