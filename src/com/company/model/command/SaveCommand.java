package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.GameSystem;

public class SaveCommand implements Command {
    private final GameSystem gameSystem;

    public SaveCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        gameSystem.saveGame();
        GameApplication.instance.quitGame();
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.PLAYING;
    }
}
