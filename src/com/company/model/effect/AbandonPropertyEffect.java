package com.company.model.effect;

import com.company.model.component.Property;

/**
 * Property set to unowned after the player bankrupt.
 * @see BankruptEffect
 */
public class AbandonPropertyEffect extends Effect implements OnLandEffect {
    private final Property property;

    /**
     * @param name the name of the effect
     * @param property the property that player owned before bankrupt.
     *
     */
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
    public void triggerOnLand() {
        property.setOwner(null);
    }
}
