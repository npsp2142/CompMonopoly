package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.NoEffect;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;
import com.company.model.observer.BlockObserver;

import java.util.ArrayList;

public class IncomeTaxBlock extends Block {
    final float INCOME_TAX_RATE = (float) 0.1;

    public IncomeTaxBlock(String name, ArrayList<BlockObserver> blockObservers) {
        super(name, blockObservers);
    }

    @Override
    public OnLandEffect createOnLandEffect(Player player) {
        int oldAmount = player.getAmount();
        return new LoseMoneyEffect("Income Tax", player, (int) (oldAmount * INCOME_TAX_RATE));
    }

    @Override
    public OnEnterEffect createOnEnterEffect(Player player) {
        return new NoEffect();
    }

    @Override
    public String getDescription() {
        return this + " - Reduce money by " + INCOME_TAX_RATE * 100 + "%";
    }
}
