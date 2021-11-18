package com.company.model.command;

import com.company.model.component.Player;

/**
 * When player response Yes, this will set player response to Yes
 */
public class GiveYesRespondCommand implements Command {

    private final Player player;

    /**
     * @param player the current player
     */
    public GiveYesRespondCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.setResponse(Player.Response.YES);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}

