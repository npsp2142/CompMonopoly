package com.company.model.command;

import com.company.model.GameDisplay;

/**
 * When user input is empty, will show some messages
 */
public class EmptyCommand implements Command {
    private final String description;

    /**
     * @param description is messages
     */
    public EmptyCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute() {
        GameDisplay.infoMessage(description);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
