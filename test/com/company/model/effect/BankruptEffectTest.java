package com.company.model.effect;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test case for BankruptEffect
 */
class BankruptEffectTest {
    Player player;
    @BeforeEach
    void setUp() {
    }

    /**
     * Unit Test case for BankruptEffect.onland()
     */
    @Test
    void onLand() {
        assertEquals(Player.Status.HEALTHY,player.getStatus());//Test if the player is bankrupt
        assertEquals(Player.Status.BANKRUPT,player.getStatus());//Test if the player is bankrupt
    }
}