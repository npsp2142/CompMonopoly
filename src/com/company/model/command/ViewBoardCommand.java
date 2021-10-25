package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.block.Block;
import com.company.model.component.Board;

public class ViewBoardCommand implements Command {
    private final Board board;

    public ViewBoardCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        StringBuilder stringBuilder = new StringBuilder();
        Block currentBlock = board.getStartBlock();
        while (!board.getNextBlock(currentBlock).equals(board.getStartBlock())) {
            stringBuilder.append("\n");

            currentBlock = board.getNextBlock(currentBlock);
        }
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
