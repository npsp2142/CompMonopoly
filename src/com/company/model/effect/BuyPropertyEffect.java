package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;

/**
 * The effect when player wants to buy property
 */
public class BuyPropertyEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final Property property;
    private final LoseMoneyEffect loseMoneyEffect;

    /**
     * @param name the name of the effect
     * @param player the player that buys property
     * @param property the property that being bought
     * @param loseMoneyEffect to reduce player's money
     * @see LoseMoneyEffect
     */
    public BuyPropertyEffect(String name, Player player, Property property, LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.player = player;
        this.property = property;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    @Override
    public void triggerOnLand() {
        notifyEffectSubscribers();
        property.setOwner(player);
        loseMoneyEffect.triggerOnLand();
    }

    public String getDescription() {
        return String.format("%s buys %s", player.getName(), property.getName());
    }
}
