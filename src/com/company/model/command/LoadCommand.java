package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.GameSystem;

public class LoadCommand implements Command{
    private final GameSystem gameSystem;

    public LoadCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public void execute() {
        gameSystem.loadGame();
        gameSystem.onGameLoad();
    }

    public boolean isValid()  {
        return GameApplication.instance.getStatus() == GameApplication.Status.MENU;
    }
}
