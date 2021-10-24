package com.company.model.effect;

import com.company.model.component.Player;

import java.util.Map;

public class PayToLeaveJailEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final LoseMoneyEffect loseMoneyEffect;
    private final CureEffect cureEffect;
    private final MoveEffect moveEffect;
    private final Map<Player, Integer> roundCounter;

    public PayToLeaveJailEffect(String name,
                                Player player, LoseMoneyEffect loseMoneyEffect,
                                CureEffect cureEffect,
                                MoveEffect moveEffect,
                                Map<Player, Integer> roundCounter) {
        super(name);
        this.player = player;
        this.loseMoneyEffect = loseMoneyEffect;
        this.cureEffect = cureEffect;
        this.moveEffect = moveEffect;
        this.roundCounter = roundCounter;
    }

    @Override
    public void onLand() {
        loseMoneyEffect.onLand();
        cureEffect.onLand();
        roundCounter.replace(player, 0);
        moveEffect.onLand();
    }

    @Override
    public String getDescription() {
        return loseMoneyEffect.getDescription() + "\n" +
                cureEffect.getDescription() + "\n" +
                moveEffect;
    }
}
