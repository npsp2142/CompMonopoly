package com.company.model.effect;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of Cure Effect
 */
class CureEffectTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
    }

    /**
     * Unit test of CureEffect.onLand()
     */
    @Test
    void onLand() {
        // Test that the effect should change the player back to normal.
        player.setStatus(Player.Status.GROUNDED);
        CureEffect cureEffect = new CureEffect("back to normal", player);
        cureEffect.triggerOnLand();
        assertEquals(Player.Status.NORMAL, player.getStatus());
    }
}