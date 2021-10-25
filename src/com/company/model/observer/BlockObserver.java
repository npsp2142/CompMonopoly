package com.company.model.observer;

import com.company.model.component.Player;
import com.company.model.component.block.Block;

public interface BlockObserver {
    void update(Block block, Player player);
}
