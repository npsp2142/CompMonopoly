package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;

/**
 * When player wants to load a saved game
 */
public class LoadCommand implements Command {
    private final GameSystem gameSystem;
    private String fileName;

    /**
     * @param gameSystem the GameSystem to modify
     */
    public LoadCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public void execute() {
        if (fileName != null) {
            gameSystem.loadGame(fileName);
            GameDisplay.infoMessage("Game loaded - " + fileName);
            return;
        }
        gameSystem.loadGame();
        GameDisplay.infoMessage("Game loaded - " + GameSystem.DEFAULT_FILE_NAME);

    }

    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.MENU;
    }

    /**
     * @param fileName The saved file name the user wants to load
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
