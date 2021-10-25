package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Location;
import com.company.model.component.Player;

import java.util.ArrayList;

public class ViewLocationCommand implements Command {
    private final Location location;
    private final ArrayList<Player> players;

    public ViewLocationCommand(Location location, ArrayList<Player> players) {
        this.location = location;
        this.players = players;
    }

    @Override
    public void execute() {
        StringBuilder builder = new StringBuilder();
        for (Player player : players) {
            builder.append(player);
            builder.append(" is at ");
            builder.append(location.getCurrentLocation(player));
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
