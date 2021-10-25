package com.company.model.observer;

import com.company.model.effect.Effect;

public interface EffectObserver {
    void update(Effect effect);
}
