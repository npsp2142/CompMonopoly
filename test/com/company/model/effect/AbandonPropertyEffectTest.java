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
public final class AbandonPropertyEffectTest {


    private Player player;
    private Property wanChai;

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
        // Test that the effect should change the property ownership to no one.
        wanChai.setOwner(player);
        assertEquals(player, wanChai.getOwner());
        AbandonPropertyEffect abandonPropertyEffect = new AbandonPropertyEffect("Bankrupt", wanChai);
        abandonPropertyEffect.triggerOnLand();
        assertNull(wanChai.getOwner());
    }
}