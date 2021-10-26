package com.company.model.effect;

import com.company.model.component.Property;

public class AbandonPropertyEffect extends Effect implements OnLandEffect {
    private final Property property;

    public AbandonPropertyEffect(String name, Property property) {
        super(name);
        this.property = property;
    }

    @Override
    public String getDescription() {
        notifyEffectSubscribers();
        return property.getName() + "are now unowned";
    }

    @Override
    public void onLand() {
        property.setOwner(null);
    }
}
