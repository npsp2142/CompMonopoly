package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.component.Location;
import com.company.model.effect.*;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;
import java.util.Map;

public class InJailBlock extends Block {
    private final Location location;
    private final Map<Player,Integer> roundCounter;

    public InJailBlock(String name,
                       ArrayList<BlockObserver> blockObservers,
                       Location location,
                       Map<Player, Integer> roundCounter) {
        super(name, blockObservers);
        this.location = location;
        this.roundCounter = roundCounter;
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        Player.Response response;
        response =  player.getResponse(String.format("pay $%d HKD to leave. [y] or Roll double [n]",
               FINE ));

        if (response == Player.Response.YES) {
            return new PayToLeaveJailEffect(
                    String.format("Pay To Leave %d", FINE),
                    player, new LoseMoneyEffect("Pay Fine", player, FINE),
                    new CureEffect("You are free", player),
                    new MoveEffect("Roll To Move", player, player.roll(2), location),
                    roundCounter
            );
        }
        int[] dices = player.roll(2);
        return new RollToLeaveJailEffect(
                "RollToLeave",
                player,
                dices,
                new MoveEffect("You are free", player, player.roll(2), location),
                new CureEffect("You can move", player),
                new LoseMoneyEffect("Pay Fine", player, FINE),
                roundCounter
        );
    }
    public static int FINE = 150;

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }


    @Override
    public String getDescription() {
        return String.format("%s - No Move %d at maximum - Pay Fine %d HKD if not roll to leave",
                this,RollToLeaveJailEffect.MAX_STAY, FINE);
    }
}
