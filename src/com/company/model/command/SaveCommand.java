package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;
// TODO: change the three lines to Player A.... Player B... Player C...
public class SaveCommand implements Command {
    private final GameSystem gameSystem;

    public SaveCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        gameSystem.saveGame();
        CompMonopolyApplication.instance.quitGame();
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
