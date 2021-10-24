package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;

public class StartCommand implements Command {
    private final GameApplication gameApplication;

    public StartCommand(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
    }

    @Override
    public void execute() {
        GameDisplay.titleBar("GAME START!");
        GameSystem.instance.onGameStart();
    }

    @Override
    public boolean isValid() {
        return gameApplication.getStatus() == GameApplication.Status.MENU;
    }
}
