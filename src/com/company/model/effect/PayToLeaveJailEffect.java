package com.company.model.effect;

import com.company.model.component.Player;

import java.util.Map;

/**
 * Player wants to pay $150 to leave jail
 * @see LoseMoneyEffect
 */
public class PayToLeaveJailEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final LoseMoneyEffect loseMoneyEffect;
    private final CureEffect cureEffect;
    private final MoveEffect moveEffect;
    private final Map<Player, Integer> roundCounter;

    /**
     * @param name the name of the effect
     * @param player player that want to pay to leave
     * @param loseMoneyEffect reduce money by 150
     * @see LoseMoneyEffect
     * @param cureEffect to set free (can move in this round)
     * @see CureEffect
     * @param moveEffect to move to desire block base on the dice result
     * @see MoveEffect
     * @param roundCounter the current round
     */
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
    public void triggerOnLand() {
        notifyEffectSubscribers();
        loseMoneyEffect.triggerOnLand();
        cureEffect.triggerOnLand();
        roundCounter.replace(player, 0);
        moveEffect.triggerOnLand();
    }

    @Override
    public String getDescription() {
        return "You paid to leave jail";
    }
}
