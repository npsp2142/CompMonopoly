package com.company.model.command;

import com.company.model.component.Player;

/**
 * When player response No, this will set player response to No
 */
public class GiveNoResponseCommand implements Command {
    private final Player player;

    /**
     * @param player means the current player
     */
    public GiveNoResponseCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.setResponse(Player.Response.NO);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
