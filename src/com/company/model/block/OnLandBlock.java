package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.effect.OnLandEffect;

public interface OnLandBlock {
    OnLandEffect createOnLandEffect(Player player);
}
