package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;

public class HelpCommand implements Command {

    @Override
    public void execute() {
        GameDisplay.HelpMessage("Command", "Description");
        GameDisplay.divider();
        GameDisplay.HelpMessage("save (filename)", "Save the game at the moment.");
        GameDisplay.HelpMessage("load (filename)", "Load the game at the moment.");
        GameDisplay.HelpMessage("quit", "Quit the application at the moment.");
        GameDisplay.divider();
        GameDisplay.HelpMessage("roll", "Roll to move across the board.");
        GameDisplay.HelpMessage("y", "Answer yes for prompt question.");
        GameDisplay.HelpMessage("n", "Answer no for prompt question.");
        GameDisplay.divider();
        GameDisplay.HelpMessage("help", "Show all commands and descriptions");
        GameDisplay.HelpMessage("destiny", "Show all blocks in the future road and corresponding owner and details.");
        GameDisplay.HelpMessage("money", "Show all players with their amount of money.");
        GameDisplay.HelpMessage("board", "Show the whole board and properties description.");
        GameDisplay.HelpMessage("profile", "Show all player's information, e.g. money they have, current location,");
        GameDisplay.HelpMessage("", "status and properties owned with rent amount.");
        GameDisplay.HelpMessage("board", "Show the whole board and properties description.");
        GameDisplay.HelpMessage("bvc", "Show blocks that all players stayed and corresponding times.");
        GameDisplay.HelpMessage("path", "Show the path that all players passed through.");

    }


    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
