package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.observer.EffectObserver;

import java.util.List;
import java.util.Map;

public class RollToLeaveJailEffect extends Effect implements OnLandEffect, Describable {
    public static final int MAX_STAY = 3;

    private final Player player;
    private final int[] dices;
    private final Map<Player, Integer> roundCounter;
    private final MoveEffect moveEffect;
    private final CureEffect cureEffect;
    private final PayToLeaveJailEffect payToLeaveJailEffect;

    public RollToLeaveJailEffect(String name, List<EffectObserver> effectObservers,
                                 Player player,
                                 int[] dices,
                                 Map<Player, Integer> roundCounter, MoveEffect moveEffect,
                                 CureEffect cureEffect,
                                 PayToLeaveJailEffect payToLeaveJailEffect) {
        super(name, effectObservers);
        this.player = player;
        this.dices = dices;
        this.roundCounter = roundCounter;
        this.moveEffect = moveEffect;
        this.cureEffect = cureEffect;
        this.payToLeaveJailEffect = payToLeaveJailEffect;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        if (dices[0] == dices[1]) { // If roll Double
            cureEffect.onLand();
            roundCounter.replace(player, 0);
            moveEffect.onLand();
            return;
        }
        if (roundCounter.get(player) < MAX_STAY - 1) { // If still need in jail this turn
            roundCounter.replace(player, roundCounter.get(player) + 1);
            return;
        }
        payToLeaveJailEffect.onLand();
    }

    public String getDescription() {
        if (dices[0] == dices[1]) { // If roll Double
            return String.format("%s roll double.", player.getName());
        }
        if (roundCounter.get(player) < MAX_STAY - 1) { // If still need in jail this turn
            return String.format("You need to stay in jail for %s round.", MAX_STAY - roundCounter.get(player));
        }
        return "So Unlucky.";
    }

}
