package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnLandEffect;

public interface OnLandBlock {
    /**
     * This is the place to decide whether the effect would be observed or not.
     *
     * @param player the player steps on the block
     * @return the effect of the block when landed
     */
    OnLandEffect createOnLandEffect(Player player);
}
