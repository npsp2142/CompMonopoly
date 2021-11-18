package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.block.JailBlock;

import java.util.Map;

/**
 * player chose to roll instead of pay
 */
public class RollToLeaveJailEffect extends Effect implements OnLandEffect, Describable {
    /**
     * The maximise turns of round stay in jail
     */
    public static final int MAX_STAY = 3;

    private final Player player;
    private final int[] dices;
    private final Map<Player, Integer> roundCounter;
    private final MoveEffect moveEffect;
    private final CureEffect cureEffect;
    private final PayToLeaveJailEffect payToLeaveJailEffect;

    /**
     * @param name the name of the effect
     * @param player player want to roll to leave
     * @param dices the result of dices
     * @param roundCounter the current round
     * @param moveEffect to move to desire block
     * @param cureEffect to set player free (can move)
     * @param payToLeaveJailEffect to pay to leave the jail
     */
    public RollToLeaveJailEffect(String name,
                                 Player player,
                                 int[] dices,
                                 Map<Player, Integer> roundCounter, MoveEffect moveEffect,
                                 CureEffect cureEffect,
                                 PayToLeaveJailEffect payToLeaveJailEffect) {
        super(name);
        this.player = player;
        this.dices = dices;
        this.roundCounter = roundCounter;
        this.moveEffect = moveEffect;
        this.cureEffect = cureEffect;
        this.payToLeaveJailEffect = payToLeaveJailEffect;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        if (dices[0] == dices[1]) { // If roll Double
            cureEffect.triggerOnLand();
            roundCounter.replace(player, 0);
            moveEffect.triggerOnLand();
            return;
        }
        if (roundCounter.get(player) < MAX_STAY - 1) { // If still need in jail this turn
            roundCounter.replace(player, roundCounter.get(player) + 1);
            return;
        }
        payToLeaveJailEffect.triggerOnLand();
    }

    public String getDescription() {
        assert (dices.length == 2);
        if (dices[0] == dices[1]) { // If roll Double
            return String.format(
                    "You rolled %d and %d. DOUBLE!!! Leave jail immediately.",
                    dices[0], dices[1]
            );
        }
        if (roundCounter.get(player) < MAX_STAY - 1) { // If still need in jail this turn
            return String.format(
                    "You rolled %d and %d. You need to stay in jail for %s more round.",
                    dices[0], dices[1], MAX_STAY - roundCounter.get(player) - 1
            );
        }
        return String.format("You rolled %d and %d. Pay %s to leave jail", dices[0], dices[1], JailBlock.FINE);
    }

}
