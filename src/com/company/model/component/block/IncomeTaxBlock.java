package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;

public class IncomeTaxBlock extends Block {
    final float INCOME_TAX_RATE = (float) 0.1;

    public IncomeTaxBlock(String name) {
        super(name);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        int oldAmount = player.getAmount();
        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Income Tax", player, (int) (oldAmount * INCOME_TAX_RATE));
        loseMoneyEffect.setEffectObservers(getEffectObservers());
        return loseMoneyEffect;
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }

    @Override
    public String getDescription() {
        return "Reduce money by " + INCOME_TAX_RATE * 100 + "%";
    }
}
