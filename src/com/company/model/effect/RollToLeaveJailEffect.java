package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.block.JailBlock;

import java.util.Map;

public class RollToLeaveJailEffect extends Effect implements OnLandEffect, Describable {
    public static final int MAX_STAY = 3;

    private final Player player;
    private final int[] dices;
    private final Map<Player, Integer> roundCounter;
    private final MoveEffect moveEffect;
    private final CureEffect cureEffect;
    private final PayToLeaveJailEffect payToLeaveJailEffect;

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
                    "You roll %d and %d. DOUBLE!!! Leave jail immediately.",
                    dices[0], dices[1]
            );
        }
        if (roundCounter.get(player) < MAX_STAY - 1) { // If still need in jail this turn
            return String.format(
                    "You roll %d and %d. You need to stay in jail for %s more round.",
                    dices[0], dices[1], MAX_STAY - roundCounter.get(player) - 1
            );
        }
        return String.format("You roll %d and %d. Pay %s to leave jail", dices[0], dices[1], JailBlock.FINE);
    }

}
