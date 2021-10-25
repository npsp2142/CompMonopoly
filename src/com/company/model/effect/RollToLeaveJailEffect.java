package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.List;
import java.util.Map;

public class RollToLeaveJailEffect extends Effect implements OnLandEffect, Describable {
    public static final int MAX_STAY = 3;
    private final Player player;
    private final int[] dices;
    private final MoveEffect moveEffect;
    private final CureEffect cureEffect;
    private final LoseMoneyEffect loseMoneyEffect;
    private final Map<Player, Integer> roundCounter;

    public RollToLeaveJailEffect(String name, List<EffectObserver> effectObservers,
                                 Player player,
                                 int[] dices,
                                 MoveEffect moveEffect,
                                 CureEffect cureEffect,
                                 LoseMoneyEffect loseMoneyEffect,
                                 Map<Player, Integer> roundCounter) {
        super(name, effectObservers);
        this.player = player;
        this.dices = dices;
        this.moveEffect = moveEffect;
        this.cureEffect = cureEffect;
        this.loseMoneyEffect = loseMoneyEffect;
        this.roundCounter = roundCounter;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
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

    public String getDescription() {
        return String.format("%s: Try to leave fail by rolling", getColoredName());
    }

    private boolean canRollToLeave() {
        return dices[0] == dices[1];
    }

}
