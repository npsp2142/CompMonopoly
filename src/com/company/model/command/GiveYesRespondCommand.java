package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.component.Player;

public class GiveYesRespondCommand implements Command {
    public final Player player;

    public GiveYesRespondCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.setResponse(Player.Response.YES);
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.MENU;
    }
}

