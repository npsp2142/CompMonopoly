package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Player;
import com.company.model.component.block.Block;
import com.company.model.observer.PathObserver;

import java.util.List;

public class ViewPathCommand implements Command {
    private final PathObserver pathObserver;

    public ViewPathCommand(PathObserver pathObserver) {
        this.pathObserver = pathObserver;
    }


    @Override
    public void execute() {
        for (Player player : pathObserver.getPaths().keySet()) {
            GameDisplay.titleBar(player.getName(), '-');
            List<Block> path = pathObserver.getPaths().get(player);
            for (int i = 0; i < path.size(); i++) {
                GameDisplay.infoMessage(String.format("Round %d: %s", i, path.get(i).getColoredName()));
            }
        }
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
