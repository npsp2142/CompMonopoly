package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;
import com.company.model.component.block.PropertyBlock;

public class ViewPropertyCommand implements Command {
    private final Player player;
    private final Board board;
    private final PlayerLocation playerLocation;

    public ViewPropertyCommand(Player player, Board board, com.company.model.component.PlayerLocation playerLocation) {
        this.player = player;
        this.board = board;
        this.playerLocation = playerLocation;
    }

    @Override
    public void execute() {
        Block start = player.getCurrentLocation(playerLocation);
        Block currentBlock = start;
        GameDisplay.infoMessage(String.format("%s is at %s", player, start));
        do {
            if (!(currentBlock instanceof PropertyBlock)) {
                currentBlock = board.getNextBlock(currentBlock);
                continue;
            }

            PropertyBlock propertyBlock = (PropertyBlock) currentBlock;
            Player owner = propertyBlock.getProperty().getOwner();
            if (owner == null) {
                GameDisplay.infoMessage(String.format("%-20s No Owner", propertyBlock.getColoredName()));
            }
            GameDisplay.infoMessage(String.format("%-20s %s", propertyBlock.getColoredName(), owner));

            currentBlock = board.getNextBlock(currentBlock);

        } while (!currentBlock.equals(start));
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }

}

