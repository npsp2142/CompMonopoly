package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.effect.TeleportEffect;

public class TeleportCommand implements Command {
    private final TeleportEffect teleportEffect;

    public TeleportCommand(TeleportEffect teleportEffect) {
        this.teleportEffect = teleportEffect;
    }

    @Override
    public void execute() {
        teleportEffect.onLand();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
