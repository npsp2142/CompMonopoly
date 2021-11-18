package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.effect.LoseMoneyEffect;

/**
 * When player needs to reduce their money, this will set the player money to corresponding amount
 */
public class ReduceMoneyCommand implements Command {
    private final LoseMoneyEffect loseMoneyEffect;

    /**
     * @param loseMoneyEffect Reduce the money that the player have
     */
    public ReduceMoneyCommand(LoseMoneyEffect loseMoneyEffect) {
        this.loseMoneyEffect = loseMoneyEffect;
    }

    @Override
    public void execute() {
        loseMoneyEffect.triggerOnLand();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
