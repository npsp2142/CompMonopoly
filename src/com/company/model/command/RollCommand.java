package com.company.model.command;

import com.company.model.*;
import com.company.model.effect.MoveEffect;

public class RollCommand implements Command {
    private final MoveEffect moveEffect;

    public RollCommand(MoveEffect moveEffect){
        this.moveEffect = moveEffect;
    }
    @Override
    public void execute() {
        moveEffect.onLand();
        GameSystem.instance.endTurn();
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.PLAYING;
    }
}
