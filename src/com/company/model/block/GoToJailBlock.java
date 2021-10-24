package com.company.model.block;

import com.company.model.component.Location;
import com.company.model.effect.*;
import com.company.model.component.Player;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public class GoToJailBlock extends Block{
    private final Location location;
    private final JustVisitingOrInJailBlock justVisitingOrInJailBlock;
    public GoToJailBlock(String name,
                         ArrayList<BlockObserver> blockObservers,
                         Location location,
                         JustVisitingOrInJailBlock justVisitingOrInJailBlock) {
        super(name, blockObservers);
        this.justVisitingOrInJailBlock =justVisitingOrInJailBlock;
        this.location = location;
    }



    public OnLandEffect createOnLandEffect(Player player) {
        return new GoJailEffect(
                DEFAULT_NAME,
                new SetGroundedEffect(DEFAULT_NAME,player),
                new TeleportEffect(DEFAULT_NAME,player, location,justVisitingOrInJailBlock,false)
                );

    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }
    public static String DEFAULT_NAME = "Go To Jail Effect";

    @Override
    public String getDescription() {
        return  String.format("%s - Go to %s and immediately end turn",this,justVisitingOrInJailBlock);
    }
}
