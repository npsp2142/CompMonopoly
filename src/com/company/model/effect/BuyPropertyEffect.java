package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;

public class BuyPropertyEffect extends Effect implements OnLandEffect{
    private Player player;
    private Property property;
    private LoseMoneyEffect loseMoneyEffect;
    public BuyPropertyEffect(String name, Player player, Property property,LoseMoneyEffect loseMoneyEffect) {
        super(name);
        this.player = player;
        this.property = property;
        this.loseMoneyEffect = loseMoneyEffect;
    }

    @Override
    public void onLand() {
        property.setOwner(player);
        loseMoneyEffect.onLand();
    }

    @Override
    public String getDescription() {
        return String.format("%s: %s buy %s",this,player.getName(),property.getName())
                + "\n" + loseMoneyEffect.getDescription();
    }
}
