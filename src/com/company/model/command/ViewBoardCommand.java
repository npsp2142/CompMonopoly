package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.component.Board;
import com.company.model.component.block.Block;

public class ViewBoardCommand implements Command {
    private final Board board;

    public ViewBoardCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        Block currentBlock = board.getStartBlock();
        while (!board.getNextBlock(currentBlock).equals(board.getStartBlock())) {
            currentBlock = board.getNextBlock(currentBlock);
        }
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
