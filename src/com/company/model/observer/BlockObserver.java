package com.company.model.observer;

import com.company.model.component.block.Block;

/**
 * BlockObserver should display the block in certain ways in the GameDisplay CLI
 * @see com.company.model.GameDisplay
 */
public interface BlockObserver {
    /**
     * @param block the block to be viewed
     * @param isVerbose the view should talk more or not
     */
    void update(Block block, boolean isVerbose);

    /**
     * Reset the observer when loading
     */
    void reset();
}
