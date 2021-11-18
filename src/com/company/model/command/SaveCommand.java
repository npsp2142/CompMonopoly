package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;

/**
 * When player wants to save, this will help to save the game
 */
public class SaveCommand implements Command {
    private final GameSystem gameSystem;
    private String fileName;

    /**
     * @param gameSystem the GameSystem to be modify
     */
    public SaveCommand(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    @Override
    public void execute() {
        if (fileName != null) {
            gameSystem.saveGame(fileName);
            CompMonopolyApplication.instance.quitGame();
            GameDisplay.infoMessage("Game saved - " + fileName);
            return;
        }
        gameSystem.saveGame();
        CompMonopolyApplication.instance.quitGame();
        GameDisplay.infoMessage("Game saved - " + GameSystem.DEFAULT_FILE_NAME);
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }

    /**
     * @param fileName the file name user wants to save as
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
