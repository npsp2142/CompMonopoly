package com.company.model;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for GameControllerTest
 */
class GameControllerTest {
    Player player;

    @BeforeEach
    void setUp() {
    }

    /**
     * Unit test for GameControllerTest.getResponse()
     */
    @Test
    void getResponse() {
        assertEquals(Player.Response.YES, player.getResponse("Yes")); // Test response is yes
        assertEquals(Player.Response.NO, player.getResponse("No")); // Test response is no
        assertNull(player.getResponse("Unknown Response")); // Test response is error
    }
}