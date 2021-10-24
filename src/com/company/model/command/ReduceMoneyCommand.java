package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.effect.LoseMoneyEffect;

public class ReduceMoneyCommand implements Command {
    private final LoseMoneyEffect loseMoneyEffect;

    public ReduceMoneyCommand(LoseMoneyEffect loseMoneyEffect) {
        this.loseMoneyEffect = loseMoneyEffect;
    }

    @Override
    public void execute() {
        loseMoneyEffect.onLand();
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.PLAYING;
    }
}
