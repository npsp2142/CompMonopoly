package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.component.block.PropertyBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit Test case for AbandonPropertyEffect
 */
class AbandonPropertyEffectTest {
    PropertyBlock propertyBlock;
    Property wanChai;
    Player player;
    int initialAmount;


    @BeforeEach
    void setUp() {

    }

    /**
     * Unit Test case for AbandonPropertyEffect.onLand()
     */
    @Test
    void onLand() {
        assertEquals(wanChai.getOwner(), player); // Test the owner of the property
        assertNull(wanChai.getOwner()); // Test the owner of the property
    }
}