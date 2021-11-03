package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.effect.*;

import java.util.Map;

public class JailBlock extends Block {
    public static final int FINE = 150;
    public static boolean IS_RANDOM = true;
    private final PlayerLocation playerLocation;
    private final Map<Player, Integer> roundCounter;
    private int[] diceRolls;

    public JailBlock(String name,
                     PlayerLocation playerLocation,
                     Map<Player, Integer> roundCounter) {
        super(name);
        this.playerLocation = playerLocation;
        this.roundCounter = roundCounter;
    }

    /**
     * @param player the player steps on the block
     * @return When the player response is yes, return the effect that the player pay to leave the jail.
     * When the player response is yes, return the effect that the player attempts to roll to leave the jail.
     * @see Player.Response
     * @see PayToLeaveJailEffect
     * @see RollToLeaveJailEffect
     */
    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        Player.Response response;
        response = player.getResponse(String.format("pay $%d HKD to leave. [y] or Roll double [n]",
                FINE));

        if (response == Player.Response.YES) {
            LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Pay Fine", player, FINE);
            CureEffect cureEffect = new CureEffect("You are free", player);
            MoveEffect moveEffect = new MoveEffect("Roll To Move", player, player.roll(2), playerLocation);

            loseMoneyEffect.setEffectObservers(getEffectObservers());
            cureEffect.setEffectObservers(getEffectObservers());
            moveEffect.setEffectObservers(getEffectObservers());

            PayToLeaveJailEffect payToLeaveJailEffect = new PayToLeaveJailEffect(
                    String.format("Pay To Leave %d", FINE),
                    player,
                    loseMoneyEffect,
                    cureEffect,
                    moveEffect,
                    roundCounter
            );
            payToLeaveJailEffect.setEffectObservers(getEffectObservers());

            return payToLeaveJailEffect;
        }

        if (IS_RANDOM) {
            diceRolls = player.roll(2);
        }

        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Pay Fine", player, FINE);
        CureEffect cureEffect = new CureEffect("You can move", player);
        MoveEffect moveEffect = new MoveEffect("You are free", player, diceRolls, playerLocation);
        PayToLeaveJailEffect payToLeaveJailEffect = new PayToLeaveJailEffect(
                String.format("Pay To Leave %d", FINE), player, loseMoneyEffect, cureEffect, moveEffect, roundCounter);
        RollToLeaveJailEffect rollToLeaveJailEffect = new RollToLeaveJailEffect(
                "Roll To Leave", player, diceRolls, roundCounter, moveEffect, cureEffect, payToLeaveJailEffect);

        loseMoneyEffect.setEffectObservers(getEffectObservers());
        cureEffect.setEffectObservers(getEffectObservers());
        moveEffect.setEffectObservers(getEffectObservers());
        payToLeaveJailEffect.setEffectObservers(getEffectObservers());
        rollToLeaveJailEffect.setEffectObservers(getEffectObservers());

        return rollToLeaveJailEffect;
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }


    @Override
    public String getDescription() {
        return String.format("No Move %d at maximum - Pay Fine %d HKD if not roll to leave",
                RollToLeaveJailEffect.MAX_STAY, FINE);
    }

    public void setDiceRolls(int[] diceRolls) {
        this.diceRolls = diceRolls;
    }
}
