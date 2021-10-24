package com.company.model.command;

import com.company.model.*;
import com.company.model.component.Location;
import com.company.model.component.Player;
import com.company.model.effect.MoveEffect;

public class RollCommand implements Command {
    private MoveEffect moveEffect;
    private Location location;
    private Player player;

    public RollCommand(Player player,
                       Location location,
                       MoveEffect moveEffect){
        this.player = player;
        this.location = location;
        this.moveEffect = moveEffect;
    }
    @Override
    public void execute() {
        moveEffect.onLand();
        GameSystem.instance.endTurn();
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.PLAYING;
    }
}
