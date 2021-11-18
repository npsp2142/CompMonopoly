package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.Block;
import com.company.model.component.block.PropertyBlock;



/**
 * When player needs to see their destiny, this will show the player destination
 */

public class SeeMyDestinyCommand implements Command {
    private final Player player;
    private final Board board;
    private final PlayerLocation playerLocation;

    /**
     * @param player The current player
     * @param board The monopoly board
     * @param playerLocation The current location of the player
     */
    public SeeMyDestinyCommand(Player player, Board board, com.company.model.component.PlayerLocation playerLocation) {
        this.player = player;
        this.board = board;
        this.playerLocation = playerLocation;
    }

    @Override
    public void execute() {
        int counter = 0;
        Block start = player.getCurrentLocation(playerLocation);
        Block currentBlock = start;
        GameDisplay.infoMessage(String.format("%s is at %s", player, start));
        do {
            if (!(currentBlock instanceof PropertyBlock)) {
                GameDisplay.infoMessage(String.format("%-2d : %-20s - %s", counter, currentBlock.getColoredName(),currentBlock.getDescription()));
                currentBlock = board.getNextBlock(currentBlock);
                counter++;
                continue;
            }

            PropertyBlock propertyBlock = (PropertyBlock) currentBlock;
            Player owner = propertyBlock.getProperty().getOwner();
            if (owner == null) {
                GameDisplay.infoMessage(String.format("%-2d : %-20s - No Owner", counter, propertyBlock.getColoredName()));
                currentBlock = board.getNextBlock(currentBlock);
                counter++;
                continue;
            }
            GameDisplay.infoMessage(
                    String.format("%-2d : %-20s - Owner: %s, Rent: %d",
                           counter, propertyBlock.getColoredName(), owner,propertyBlock.getProperty().getRent()
                    )
            );

            currentBlock = board.getNextBlock(currentBlock);
            counter++;
        } while (!currentBlock.equals(start));
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }

}

