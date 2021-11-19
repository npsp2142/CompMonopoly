package com.company.model.effect;

import com.company.model.component.Player;

/**
 * Reduce player money when buying property or paying money
 */
public class LoseMoneyEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final int amount;

    /**
     * @param name the name of the effect
     * @param player the player that losing money
     * @param amount the amount of money is being reduced
     */
    public LoseMoneyEffect(String name, Player player, int amount) {
        super(name);
        this.player = player;
        this.amount = amount;
    }

    @Override
    public void triggerOnLand() {
        player.reduceAmount(amount);
        notifyEffectSubscribers();
    }

    @Override
    public String getDescription() {
        return String.format("%s losses %d HKD", player, amount);
    }
}
