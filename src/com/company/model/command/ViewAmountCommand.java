package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Player;

import java.util.ArrayList;

public class ViewAmountCommand implements Command {
    private final ArrayList<Player> players;

    public ViewAmountCommand(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public void execute() {
        for (Player player : players) {
            GameDisplay.infoMessage(String.format("%-15s %d", player, player.getAmount()));
        }
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
