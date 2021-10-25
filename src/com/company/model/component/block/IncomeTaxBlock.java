package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxBlock extends Block {
    final float INCOME_TAX_RATE = (float) 0.1;

    public IncomeTaxBlock(String name, ArrayList<BlockObserver> blockObservers, List<EffectObserver> effectObservers) {
        super(name, blockObservers, effectObservers);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        int oldAmount = player.getAmount();
        return new LoseMoneyEffect("Income Tax",getEffectObservers(), player, (int) (oldAmount * INCOME_TAX_RATE));
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect(getEffectObservers());
    }

    @Override
    public String getDescription() {
        return this + " - Reduce money by " + INCOME_TAX_RATE * 100 + "%";
    }
}
