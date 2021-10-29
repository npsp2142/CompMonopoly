package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;

public class HelpCommand implements Command {

    @Override
    public void execute() {
        GameDisplay.commandHelpMessage("Command", "Description");
        GameDisplay.divider();
        GameDisplay.commandHelpMessage("roll", "Roll to move across the board.");
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
