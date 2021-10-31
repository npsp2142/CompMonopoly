package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;

public class StartCommand implements Command {
    private final CompMonopolyApplication compMonopolyApplication;
    private final GameSystem gameSystem;

    public StartCommand(CompMonopolyApplication compMonopolyApplication, GameSystem gameSystem) {
        this.compMonopolyApplication = compMonopolyApplication;
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        GameDisplay.titleBar("GAME START!");
        gameSystem.startGame();
    }

    @Override
    public boolean isValid() {
        return compMonopolyApplication.getStatus() == CompMonopolyApplication.Status.MENU;
    }
}
