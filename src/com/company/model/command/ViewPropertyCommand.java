package com.company.model.command;

import com.company.model.*;
import com.company.model.block.Block;
import com.company.model.block.PropertyBlock;
import com.company.model.component.Board;
import com.company.model.component.Player;

public class ViewPropertyCommand implements Command{
    public enum Mode{
        ALL,SELF
    }
    private final Player player;
    private final Board board;
    private final Mode mode;
    public ViewPropertyCommand(Player player, Board board, Mode mode){
        this.player = player;
        this.board = board;
        this.mode = mode;
    }

    @Override
    public void execute() {
        Block start = player.getCurrentLocation(GameSystem.instance.getPlayerLocation());
        Block currentBlock = start;
        GameDisplay.infoMessage(String.format("%s is at %s", player,start));
        do{
            if(!(currentBlock instanceof PropertyBlock)){
                currentBlock = board.getNextBlock(currentBlock);
                continue;
            }
            PropertyBlock propertyBlock = (PropertyBlock) currentBlock;
            Player owner = propertyBlock.getProperty().getOwner();
            switch (mode){
                case ALL:
                    if (owner == null) {
                        GameDisplay.infoMessage(String.format("%-20s No Owner", propertyBlock));
                        break;
                    }
                    GameDisplay.infoMessage(String.format("%-20s %s",propertyBlock, owner));
                    break;
                case SELF:
                    if (owner == null)break;
                    if (!owner.equals(player)) break;
                    GameDisplay.infoMessage(String.format("%-20s %s", propertyBlock, owner));
                    break;
            }

            currentBlock = board.getNextBlock(currentBlock);

        }while(!currentBlock.equals(start));
    }

    @Override
    public boolean isValid() {
        return GameApplication.instance.getStatus() == GameApplication.Status.PLAYING;
    }
}
