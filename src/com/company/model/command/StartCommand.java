package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;

public class StartCommand implements Command {
    private final CompMonopolyApplication compMonopolyApplication;

    public StartCommand(CompMonopolyApplication compMonopolyApplication) {
        this.compMonopolyApplication = compMonopolyApplication;
    }

    @Override
    public void execute() {
        GameDisplay.titleBar("GAME START!");
        GameSystem.instance.onGameStart();
    }

    @Override
    public boolean isValid() {
        return compMonopolyApplication.getStatus() == CompMonopolyApplication.Status.MENU;
    }
}
