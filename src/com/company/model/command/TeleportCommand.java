package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;
import com.company.model.effect.TeleportEffect;

public class TeleportCommand implements Command {
    private final TeleportEffect teleportEffect;
    private final GameSystem gameSystem;

    public TeleportCommand(TeleportEffect teleportEffect, GameSystem gameSystem) {
        this.teleportEffect = teleportEffect;
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        teleportEffect.triggerOnLand();
        gameSystem.getCurrentPlayer().notifySubscribers();
        gameSystem.endTurn();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
