package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;
import com.company.model.effect.MoveEffect;

/**
 * When player wants to roll the dice, this will set the dice result
 */
public class RollCommand implements Command {
    private final MoveEffect moveEffect;
    private final GameSystem gameSystem;

    /**
     * @param moveEffect to see if the player can move
     * @param gameSystem GameSystem to be modify
     */
    public RollCommand(MoveEffect moveEffect, GameSystem gameSystem) {
        this.moveEffect = moveEffect;
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        moveEffect.triggerOnLand();
        gameSystem.getCurrentPlayer().notifySubscribers();
        gameSystem.endTurn();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
