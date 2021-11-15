package com.company.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class PropertyTest {
    Property central;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player A");
        central = new Property("Central", 800, 90);
    }

    @Test
    void reset() {
        central.setOwner(player);
        central.reset();
        assertNull(central.getOwner());
    }
}