package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.effect.*;

import java.util.Map;

public class JailBlock extends Block {
    public static final int FINE = 150;
    public static final int ROLL_TIMES = 2;

    private static final String PROMPT = "pay $%d HKD to leave. [y] or Roll double [n]";
    private static final String[] EFFECT_NAMES = {
            "Pay Fine",
            "You are free",
            "Roll To Move",
            "Pay To Leave %d",
            "Roll To Leave"
    };

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
     * When the player response is no, return the effect that the player attempts to roll to leave the jail.
     * @see Player.Response
     * @see PayToLeaveJailEffect
     * @see RollToLeaveJailEffect
     */
    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        Player.Response response;
        response = player.getResponse(String.format(PROMPT, FINE));

        if (response == Player.Response.YES) {
            LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect(EFFECT_NAMES[0], player, FINE);
            CureEffect cureEffect = new CureEffect(EFFECT_NAMES[1], player);
            MoveEffect moveEffect = new MoveEffect(EFFECT_NAMES[2], player, player.roll(ROLL_TIMES), playerLocation);

            loseMoneyEffect.setEffectObservers(getEffectObservers());
            cureEffect.setEffectObservers(getEffectObservers());
            moveEffect.setEffectObservers(getEffectObservers());

            PayToLeaveJailEffect payToLeaveJailEffect = new PayToLeaveJailEffect(
                    String.format(EFFECT_NAMES[3], FINE),
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
            diceRolls = player.roll(ROLL_TIMES);
        }

        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect(EFFECT_NAMES[0], player, FINE);
        CureEffect cureEffect = new CureEffect(EFFECT_NAMES[1], player);
        MoveEffect moveEffect = new MoveEffect(EFFECT_NAMES[2], player, diceRolls, playerLocation);
        PayToLeaveJailEffect payToLeaveJailEffect = new PayToLeaveJailEffect(
                String.format(EFFECT_NAMES[3], FINE), player, loseMoneyEffect, cureEffect, moveEffect, roundCounter);
        RollToLeaveJailEffect rollToLeaveJailEffect = new RollToLeaveJailEffect(
                EFFECT_NAMES[4], player, diceRolls, roundCounter, moveEffect, cureEffect, payToLeaveJailEffect);

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
        return String.format("No Move %d round at maximum - Pay Fine %d HKD if not roll to leave",
                RollToLeaveJailEffect.MAX_STAY, FINE);
    }

    public void setDiceRolls(int[] diceRolls) {
        this.diceRolls = diceRolls;
    }

    public Map<Player, Integer> getRoundCounter() {
        return roundCounter;
    }
}
