package com.company.model.effect;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetGroundedEffectTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        player.setStatus(Player.Status.NORMAL);

    }

    @Test
    void onLand() {
        SetGroundedEffect setGroundedEffect = new SetGroundedEffect("You are Grounded", player);
        setGroundedEffect.triggerOnLand();
        assertEquals(Player.Status.GROUNDED, player.getStatus());
    }
}