package com.company.model.effect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test case for AbandonPropertyEffect
 */
class AbandonPropertyEffectTest {

    @BeforeEach
    void setUp() {
    }

    /**
     * Unit Test case for AbandonPropertyEffect.onland()
     */
    @Test
    void onLand() {
        assertEquals(wanChai.getOwner(),player);// Test the owner of the property
        assertNull(wanChai.getOwner());// Test the owner of the property
    }
}