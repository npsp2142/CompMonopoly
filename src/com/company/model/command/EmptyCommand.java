package com.company.model.command;

import com.company.model.GameDisplay;

public class EmptyCommand implements Command {
    private final String description;

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
