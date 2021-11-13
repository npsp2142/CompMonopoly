package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;

public class SaveCommand implements Command {
    private final GameSystem gameSystem;

    public SaveCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        // TODO: Add tmp folder
        gameSystem.saveGame();
        CompMonopolyApplication.instance.quitGame();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
