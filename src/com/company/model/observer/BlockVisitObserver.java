package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockVisitObserver implements BlockObserver {
    public static final String DEFAULT_NAME = "Block Visit Observer";
    private final Map<Block, Integer> counter;

    public BlockVisitObserver() {
        this.counter = new HashMap<>();
    }

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
    public void update(Block block) {
        GameDisplay.infoMessage(String.format("You are at %s", block.getColoredName()));
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

    public Map<Block, Integer> getCounter() {
        return counter;
    }
}
