package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;
import com.company.model.effect.MoveEffect;

public class RollCommand implements Command {
    private final MoveEffect moveEffect;

    public RollCommand(MoveEffect moveEffect) {
        this.moveEffect = moveEffect;
    }

    @Override
    public void execute() {
        moveEffect.onLand();
        GameSystem.instance.endTurn();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
