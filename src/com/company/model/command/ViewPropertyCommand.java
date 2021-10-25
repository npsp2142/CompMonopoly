package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.block.Block;
import com.company.model.component.block.PropertyBlock;

public class ViewPropertyCommand implements Command {
    private final Player player;
    private final Board board;
    private final Mode mode;

    public ViewPropertyCommand(Player player, Board board, Mode mode) {
        this.player = player;
        this.board = board;
        this.mode = mode;
    }

    @Override
    public void execute() {
        Block start = player.getCurrentLocation(GameSystem.instance.getPlayerLocation());
        Block currentBlock = start;
        GameDisplay.infoMessage(String.format("%s is at %s", player, start));
        do {
            if (!(currentBlock instanceof PropertyBlock)) {
                currentBlock = board.getNextBlock(currentBlock);
                continue;
            }
            PropertyBlock propertyBlock = (PropertyBlock) currentBlock;
            Player owner = propertyBlock.getProperty().getOwner();
            switch (mode) {
                case ALL:
                    if (owner == null) {
                        GameDisplay.infoMessage(String.format("%-20s No Owner", propertyBlock));
                        break;
                    }
                    GameDisplay.infoMessage(String.format("%-20s %s", propertyBlock, owner));
                    break;
                case SELF:
                    if (owner == null) break;
                    if (!owner.equals(player)) break;
                    GameDisplay.infoMessage(String.format("%-20s %s", propertyBlock, owner));
                    break;
            }

            currentBlock = board.getNextBlock(currentBlock);

        } while (!currentBlock.equals(start));
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }

    public enum Mode {
        ALL, SELF
    }
}
