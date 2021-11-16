package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;

public class HelpCommand implements Command {

    @Override
    public void execute() {
        GameDisplay.commandHelpMessage("Command", "Description");
        GameDisplay.divider();
        GameDisplay.commandHelpMessage("save", "Save the game at the moment.");
        GameDisplay.commandHelpMessage("load", "Load the game at the moment.");
        GameDisplay.commandHelpMessage("quit", "Quit the application at the moment.");
        GameDisplay.divider();
        GameDisplay.commandHelpMessage("roll", "Roll to move across the board.");
        GameDisplay.commandHelpMessage("y", "Answer yes for prompt question.");
        GameDisplay.commandHelpMessage("n", "Answer no for prompt question.");
        GameDisplay.divider();
        GameDisplay.commandHelpMessage("help", "Show all commands and descriptions");
        GameDisplay.commandHelpMessage("location", "Show current location and the path that player passed through.");
        GameDisplay.commandHelpMessage("property", "Show properties in the future road and corresponding owner.");
        GameDisplay.commandHelpMessage("money", "Show all players with their amount of money.");
        GameDisplay.commandHelpMessage("board", "Show the whole board and properties description.");
        GameDisplay.commandHelpMessage("profile", "Show all player's information, e.g. money they have, current location, status and properties owned with rent amount.");
        GameDisplay.commandHelpMessage("board", "Show the whole board and properties description.");
        GameDisplay.commandHelpMessage("bvc", "Show blocks that all players stayed and corresponding times.");
        GameDisplay.commandHelpMessage("path", "Show the path that all players passed through.");

    }


    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
