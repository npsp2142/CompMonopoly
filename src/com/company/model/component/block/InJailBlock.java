package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.effect.*;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InJailBlock extends Block {
    public static final int FINE = 150;
    private final PlayerLocation playerLocation;
    private final Map<Player, Integer> roundCounter;

    public InJailBlock(String name,
                       ArrayList<BlockObserver> blockObservers,
                       List<EffectObserver> effectObservers,
                       PlayerLocation playerLocation,
                       Map<Player, Integer> roundCounter) {
        super(name, blockObservers, effectObservers);
        this.playerLocation = playerLocation;
        this.roundCounter = roundCounter;
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        Player.Response response;
        response = player.getResponse(String.format("pay $%d HKD to leave. [y] or Roll double [n]",
                FINE));

        if (response == Player.Response.YES) {
            return new PayToLeaveJailEffect(
                    String.format("Pay To Leave %d", FINE), getEffectObservers(),
                    player,
                    new LoseMoneyEffect("Pay Fine", getEffectObservers(), player, FINE),
                    new CureEffect("You are free", getEffectObservers(), player),
                    new MoveEffect("Roll To Move", getEffectObservers(), player, player.roll(2), playerLocation),
                    roundCounter
            );
        }
        int[] dices = player.roll(2);
        return new RollToLeaveJailEffect(
                "Roll To Leave", getEffectObservers(),
                player,
                dices,
                roundCounter,
                new MoveEffect("You are free", getEffectObservers(), player, player.roll(2), playerLocation),
                new CureEffect("You can move", getEffectObservers(), player),
                new PayToLeaveJailEffect(
                        String.format("Pay To Leave %d", FINE), getEffectObservers(),
                        player,
                        new LoseMoneyEffect("Pay Fine", getEffectObservers(), player, FINE),
                        new CureEffect("You are free", getEffectObservers(), player),
                        new MoveEffect("Roll To Move", getEffectObservers(), player, player.roll(2), playerLocation),
                        roundCounter
                )
        );
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getEffectObservers());
    }


    @Override
    public String getDescription() {
        return String.format("No Move %d at maximum - Pay Fine %d HKD if not roll to leave",
                RollToLeaveJailEffect.MAX_STAY, FINE);
    }
}
