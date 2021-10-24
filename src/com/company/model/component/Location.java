package com.company.model.component;

import com.company.model.GameDisplay;
import com.company.model.block.Block;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.effect.OnEnterEffect;
import com.company.model.effect.OnLandEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Location implements Serializable {
    private final Map<Player, Block> location;
    private final Board board;
    private ArrayList<Player> players;

    public Location(Board board, ArrayList<Player> players) {
        location = new Hashtable<>();
        this.board = board;
        this.players = players;
    }

    public void setStartLocation(Block block) {
        for (Player player : players) {
            if(location.get(player) == null){
                location.put(player, block);
                continue;
            }
            location.replace(player, block);
        }

    }


    public void moveStep(Player player, int step) {
        Block currentBlock = location.get(player);

        for (int i = 0; i < step; i++) {
            currentBlock = board.getNextBlock(currentBlock);
            OnEnterEffect onEnterEffect = currentBlock.createOnEnterEffect(player);
            onEnterEffect.onEnter();
        }

        currentBlock.notifySubscribers(player);
        location.replace(player, location.get(player), currentBlock);
        OnLandEffect effect = currentBlock.createOnLandEffect(player);
        effect.onLand();
        GameDisplay.infoMessage(effect.getDescription());
    }

    public Block getCurrentLocation(Player player) {
        return location.get(player);
    }

    public void moveTo(Player player, Block block, boolean isTrigger) {
        block.notifySubscribers(player);
        if (isTrigger) {
            block.createOnLandEffect(player).onLand();
        }
        location.replace(player, block);
    }

    public void moveTo(Player player, String name) {
        Block block= board.findBlock(name);
        if(block== null){
            return;
        }
        moveTo(player,block);
    }

    public void moveTo(Player player, Block block) {
        block.notifySubscribers(player);
        location.replace(player, block);
    }

    public void reload(){
        for (Player player :players
                ) {
            location.replace(player, board.getStartBlock());
        }
    }

}
