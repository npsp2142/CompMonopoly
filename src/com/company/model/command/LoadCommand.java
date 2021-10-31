package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;

public class LoadCommand implements Command {
    private final GameSystem gameSystem;

    public LoadCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public void execute() {
        gameSystem.loadGame();
    }

    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.MENU;
    }
}
