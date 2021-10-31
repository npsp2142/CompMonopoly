package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnLandEffect;

public interface OnLandBlock {
    /**
     * Create instance of OnLandEffect which is related to the block that the player lands.
     *
     * @param player the player steps on the block
     * @return the effect of the block when landed
     */
    OnLandEffect createOnLandEffect(Player player);
}
