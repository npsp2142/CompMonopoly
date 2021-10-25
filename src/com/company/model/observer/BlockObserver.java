package com.company.model.observer;

import com.company.model.component.block.Block;
import com.company.model.component.Player;

public interface BlockObserver {
    void update(Block block, Player player);
}
