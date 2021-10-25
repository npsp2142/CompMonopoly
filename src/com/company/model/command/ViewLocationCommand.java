package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;

import java.util.ArrayList;

public class ViewLocationCommand implements Command {
    private final PlayerLocation playerLocation;
    private final ArrayList<Player> players;

    public ViewLocationCommand(PlayerLocation playerLocation, ArrayList<Player> players) {
        this.playerLocation = playerLocation;
        this.players = players;
    }

    @Override
    public void execute() {
        StringBuilder builder = new StringBuilder();
        for (Player player : players) {
            builder.append(player);
            builder.append(" is at ");
            builder.append(playerLocation.getCurrentLocation(player).getColoredName());
            builder.append("\n");
        }
        builder.delete(builder.toString().length() - 1, builder.toString().length());
        GameDisplay.infoMessage(builder.toString());
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
