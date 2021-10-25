package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.observer.EffectObserver;

import java.util.List;

public class BuyPropertyEffect extends Effect implements OnLandEffect {
    private final Player player;
    private final Property property;
    private final LoseMoneyEffect loseMoneyEffect;

    public BuyPropertyEffect(String name, List<EffectObserver> effectObservers, Player player, Property property, LoseMoneyEffect loseMoneyEffect) {
        super(name, effectObservers);
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

    @Override
    public String getDescription() {
        return String.format("%s: %s buy %s", getColoredName(), player.getName(), property.getName());
    }
}
