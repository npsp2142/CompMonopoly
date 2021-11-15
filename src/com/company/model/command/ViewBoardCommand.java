package com.company.model.command;

import com.company.model.BoardDisplay;
import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Board;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;

public class ViewBoardCommand implements Command {
    private final Board board;
    private final PlayerLocation playerLocation;

    public ViewBoardCommand(Board board, PlayerLocation playerLocation) {
        this.board = board;
        this.playerLocation = playerLocation;
    }

    @Override
    public void execute() {
        Block currentBlock = playerLocation.getStartBlock();
        GameDisplay.print(BoardDisplay.VISUAL);
        GameDisplay.infoMessage(currentBlock.getColoredName() + " - " + currentBlock.getDescription());
        while (!board.getNextBlock(currentBlock).equals(playerLocation.getStartBlock())) {
            currentBlock = board.getNextBlock(currentBlock);
            GameDisplay.infoMessage(currentBlock.getColoredName() + " - " + currentBlock.getDescription());
        }
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
