package com.company.model.component;

import com.company.model.GameDisplay;
import com.company.model.component.block.Block;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class PlayerLocation implements Serializable {
    private final Map<Player, Block> location;
    private final Board board;
    private final ArrayList<Player> players;
    private final Block startBlock;

    public PlayerLocation(Board board, ArrayList<Player> players, Block startBlock) {
        this.startBlock = startBlock;
        location = new Hashtable<>();
        this.board = board;
        this.players = players;
    }

    public void setStartLocation() {
        for (Player player : players) {
            if (location.get(player) == null) {
                location.put(player, startBlock);
                continue;
            }
            location.replace(player, startBlock);
        }
    }

    public void moveStep(Player player, int step) {
        Block currentBlock = location.get(player);

        for (int i = 0; i < step; i++) {
            currentBlock = board.getNextBlock(currentBlock);
            OnEnterEffect onEnterEffect = currentBlock.createOnEnterEffect(player);
            onEnterEffect.triggerOnEnter();
        }

        currentBlock.notifyBlockSubscribers(true);
        location.replace(player, location.get(player), currentBlock);
        OnLandEffect effect = currentBlock.createOnLandEffect(player);
        effect.triggerOnLand();
    }

    public Block getCurrentLocation(Player player) {
        return location.get(player);
    }


    public void moveTo(Player player, String name,boolean isTrigger, boolean isVerbose) {
        Block block = board.findBlock(name);
        if (block == null) {
            GameDisplay.warnMessage("Block not found");
            return;
        }
        moveTo(player, block, isTrigger, isVerbose);
    }

    public void moveTo(Player player, Block destination) {
        moveTo(player, destination, true, true);
    }

    public void moveTo(Player player, Block destination, boolean isTrigger, boolean isVerbose) {
        if (!blockExist(destination)) return;
        destination.notifyBlockSubscribers(isVerbose);
        location.replace(player, destination);
        if (isTrigger) {
            destination.createOnLandEffect(player).triggerOnLand();
        }
    }

    private boolean blockExist(Block block) {
        for (Block existingBlock : board.getBlocks()) {
            if (block.equals(existingBlock)) {
                return true;
            }
        }
        return false;
    }

    public void reload() {
        location.clear();
        setStartLocation();
    }

    public Block getStartBlock() {
        return startBlock;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
