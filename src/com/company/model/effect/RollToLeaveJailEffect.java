package com.company.model.effect;

import com.company.model.component.Player;

import java.util.Map;

public class RollToLeaveJailEffect extends Effect implements OnLandEffect, Describable {
    public static final int MAX_STAY = 3;
    private final Player player;
    private final int[] dices;
    private final MoveEffect moveEffect;
    private final CureEffect cureEffect;
    private final LoseMoneyEffect loseMoneyEffect;
    private final Map<Player, Integer> roundCounter;

    public RollToLeaveJailEffect(String name,
                                 Player player,
                                 int[] dices,
                                 MoveEffect moveEffect,
                                 CureEffect cureEffect,
                                 LoseMoneyEffect loseMoneyEffect,
                                 Map<Player, Integer> roundCounter) {
        super(name);
        this.player = player;
        this.dices = dices;
        this.moveEffect = moveEffect;
        this.cureEffect = cureEffect;
        this.loseMoneyEffect = loseMoneyEffect;
        this.roundCounter = roundCounter;
    }

    @Override
    public void onLand() {
        if (canRollToLeave()) {
            cureEffect.onLand();
            roundCounter.replace(player, 0);
            moveEffect.onLand();
            return;
        }

        if (roundCounter.get(player) < 2) {
            roundCounter.replace(player, roundCounter.get(player) + 1);
            return;
        }

        cureEffect.onLand();
        loseMoneyEffect.onLand();
        roundCounter.replace(player, 0);
        moveEffect.onLand();
    }

    @Override
    public String getDescription() {
        if (canRollToLeave()) {
            return String.format("%s: %s can move\n", this, player) +
                    moveEffect.getDescription();
        }

        if (roundCounter.get(player) < 3) {
            return String.format("%s: %s remain %s round to leave", this, player, MAX_STAY - roundCounter.get(player));
        }

        return "";
    }

    private boolean canRollToLeave() {
        return dices[0] == dices[1];
    }

}
