package com.company.model.component;

import com.company.model.component.block.Block;

import java.util.ArrayList;
import java.util.Hashtable;

public class Board {
    private final ArrayList<Block> blocks;
    private final Hashtable<Block, Block> board;

    public Board() {
        this.board = new Hashtable<>();
        this.blocks = new ArrayList<>();
    }

    public void addPath(Block start, Block destination) {
        board.put(start, destination);
    }

    public void addPath(String start, String destination) {
        Block startBlock = findBlock(start);
        if (startBlock == null)
            return;
        Block destinationBlock = findBlock(destination);
        if (destinationBlock == null)
            return;

        addPath(startBlock, destinationBlock);
    }

    public void addBlock(Block newBlock) {
        blocks.add(newBlock);
    }

    public Block findBlock(String name) {
        for (Block block : blocks) {
            if (block.getName().equals(name))
                return block;
        }
        return null;
    }

    public Block getNextBlock(Block start) {
        return board.get(start);
    }

    public Block getNextBlock(String name) {
        Block block = findBlock(name);
        if (block == null)
            return null;
        return getNextBlock(block);
    }

}
