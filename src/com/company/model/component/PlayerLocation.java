package com.company.model.component;

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

        currentBlock.notifyBlockSubscribers();
        location.replace(player, location.get(player), currentBlock);
        OnLandEffect effect = currentBlock.createOnLandEffect(player);
        effect.triggerOnLand();
    }

    public Block getCurrentLocation(Player player) {
        return location.get(player);
    }

    public void moveTo(Player player, Block block, boolean isTrigger) {
        block.notifyBlockSubscribers();
        location.replace(player, block);
        if (isTrigger) {
            block.createOnLandEffect(player).triggerOnLand();
        }
    }

    public void moveTo(Player player, String name) {
        Block block = board.findBlock(name);
        if (block == null) {
            return;
        }
        moveTo(player, block);
    }

    public void moveTo(Player player, Block destination) {
        if(!blockExist(destination)) return;
        destination.notifyBlockSubscribers();
        location.replace(player, destination);
    }

    private boolean blockExist(Block destination){
        for(Block block: board.getBlocks()){
            if(block.equals(destination)){
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
