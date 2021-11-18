package com.company.model.component;

import com.company.model.component.block.Block;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The board of Monopoly contains all available blocks and the path
 */
public class Board {
    private final ArrayList<Block> blocks;
    private final Hashtable<Block, Block> path;

    /**
     * Create instance of Board
     */
    public Board() {
        this.path = new Hashtable<>();
        this.blocks = new ArrayList<>();
    }

    /**
     * Add a edge of the graph
     * @param start the start block
     * @param destination the end block
     */
    public void addPath(Block start, Block destination) {
        if (!blocks.contains(start)) {
            return;
        }
        if (!blocks.contains(destination)) {
            return;
        }
        path.put(start, destination);
    }

    /**
     * @param start the name of the start block
     * @param destination the name of the end block
     */
    public void addPath(String start, String destination) {
        Block startBlock = findBlock(start);
        if (startBlock == null)
            return;
        Block destinationBlock = findBlock(destination);
        if (destinationBlock == null)
            return;
        addPath(startBlock, destinationBlock);
    }

    /**
     * @param newBlock the new block in the board
     */
    public void addBlock(Block newBlock) {
        blocks.add(newBlock);
    }

    /**
     * @param name the name of a block
     * @return the block with the specific name, null if no block is the same name.
     */
    public Block findBlock(String name) {
        for (Block block : blocks) {
            if (block.getName().equals(name))
                return block;
        }
        return null;
    }

    /**
     * @param start the current block
     * @return the next block of the current block
     */
    public Block getNextBlock(Block start) {
        return path.get(start);
    }

    /**
     * @param name the name of the current block
     * @return the next block of the current block if the name exists
     */
    public Block getNextBlock(String name) {
        Block block = findBlock(name);
        if (block == null)
            return null;
        return getNextBlock(block);
    }

    /**
     * @return the available blocks in the board
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
