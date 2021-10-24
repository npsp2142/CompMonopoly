package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.GameDisplay;

public class HelpCommand implements Command{

    @Override
    public void execute() {
        if (GameApplication.instance.getStatus() == GameApplication.Status.PLAYING){
            GameDisplay.commandHelpMessage("Command","Description");
            GameDisplay.divider();
            GameDisplay.commandHelpMessage("roll","Roll to move across the board.");
        }

    }

    @Override
    public boolean isValid() {
        return true;
    }
}
