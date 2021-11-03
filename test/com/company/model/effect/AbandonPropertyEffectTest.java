package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit Test case for AbandonPropertyEffect
 */
class AbandonPropertyEffectTest {

    Player player;
    Property wanChai;
    AbandonPropertyEffect abandonPropertyEffect;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        wanChai = new Property("Wan Chai", 700, 65);
    }

    /**
     * Unit Test case for AbandonPropertyEffect.onLand()
     */
    @Test
    void onLand() {
        // Test that someone own the property
        wanChai.setOwner(player);
        assertEquals(player, wanChai.getOwner());
        abandonPropertyEffect = new AbandonPropertyEffect("Bankrupt", wanChai);
        abandonPropertyEffect.onLand();

        // Test that no one own the property
        assertNull(wanChai.getOwner());
    }
}