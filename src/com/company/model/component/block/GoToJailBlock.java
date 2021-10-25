package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.effect.*;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

public class GoToJailBlock extends Block {
    public static final String DEFAULT_NAME = "Go To Jail";
    private final PlayerLocation playerLocation;
    private final JustVisitingOrInJailBlock justVisitingOrInJailBlock;


    public GoToJailBlock(String name,
                         ArrayList<BlockObserver> blockObservers, List<EffectObserver> effectObservers,
                         PlayerLocation playerLocation,
                         JustVisitingOrInJailBlock justVisitingOrInJailBlock) {
        super(name, blockObservers, effectObservers);
        this.justVisitingOrInJailBlock = justVisitingOrInJailBlock;
        this.playerLocation = playerLocation;
    }

    public OnLandEffect createOnLandEffect(Player player) {
        return new GoJailEffect(
                DEFAULT_NAME, getEffectObservers(),
                new SetGroundedEffect("Grounded", getEffectObservers(), player),
                new TeleportEffect(DEFAULT_NAME, getEffectObservers(), player, playerLocation, justVisitingOrInJailBlock, false)
        );

    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getEffectObservers());
    }

    @Override
    public String getDescription() {
        return String.format("Go to %s and immediately end turn", justVisitingOrInJailBlock.getColoredName());
    }
}
