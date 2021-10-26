package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;

public class BuyPropertyEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final Property property;
    private final LoseMoneyEffect loseMoneyEffect;

    public BuyPropertyEffect(String name, Player player, Property property, LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.player = player;
        this.property = property;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    @Override
    public void onLand() {
        notifyEffectSubscribers();
        property.setOwner(player);
        loseMoneyEffect.onLand();
    }

    public String getDescription() {
        return String.format("%s buy %s", player.getName(), property.getName());
    }
}
