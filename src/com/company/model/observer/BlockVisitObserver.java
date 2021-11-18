package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.block.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * BlockVisitObserver provides the view that counts the times visited by players.
 * The observer informs the locction of a player for each update
 */
public class BlockVisitObserver implements BlockObserver {
    /**
     * DEFAULT_NAME is the default name of the block visit observer
     */
    public static final String DEFAULT_NAME = "Block Visit Observer";
    private final Map<Block, Integer> counter;

    /**
     * Create the instance of BlockVisitObserver
     */
    public BlockVisitObserver() {
        this.counter = new HashMap<>();
    }

    /**
     * Provides the view that counts the times visited by players.
     */
    public void view() {
        for (Block block : counter.keySet()) {
            if (!counter.containsKey(block)) {
                GameDisplay.infoMessage(String.format("%s: 0 times", block.getColoredName()));
                continue;
            }
            GameDisplay.infoMessage(String.format("%s: %d times", block.getColoredName(), counter.get(block)));
        }
    }


    @Override
    public void update(Block block, boolean isVerbose) {
        if (isVerbose) {
            GameDisplay.infoMessage(
                    String.format("You are at %s",
                            GameDisplay.colorString(block.getName(), GameDisplay.ANSI_CYAN)
                    ));
        }
        if (!counter.containsKey(block)) {
            counter.put(block, 1);
            return;
        }
        counter.put(block, counter.get(block) + 1);
    }

    @Override
    public void reset() {
        counter.clear();
    }

    /**
     * @return get the block view counter
     */
    public Map<Block, Integer> getCounter() {
        return counter;
    }
}
