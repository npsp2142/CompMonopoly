package com.company.model.command;

import com.company.model.GameApplication;
import com.company.model.GameDisplay;
import com.company.model.block.Block;
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
            stringBuilder.append(currentBlock.getDescription());
            stringBuilder.append("\n");

            currentBlock = board.getNextBlock(currentBlock);
        }
        stringBuilder.append(currentBlock.getDescription());

        GameDisplay.infoMessage(stringBuilder.toString());
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.PLAYING;
    }
}
