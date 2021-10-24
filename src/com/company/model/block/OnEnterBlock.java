package com.company.model.block;

import com.company.model.component.Player;
import com.company.model.effect.OnEnterEffect;

public interface OnEnterBlock {
    OnEnterEffect createOnEnterEffect(Player player);
}
