package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Player;

import java.util.ArrayList;

/**
 * When player wants to view all players amount, this will show all players amount
 */
public class ViewAmountCommand implements Command {
    private final ArrayList<Player> players;

    /**
     * @param players the players in the game
     */
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
