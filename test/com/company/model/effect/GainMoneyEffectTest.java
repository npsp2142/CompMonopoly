package com.company.model.effect;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GainMoneyEffectTest {

    final int initialAmount = 1050;
    final int amountToBeAdded = 100;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        player.setAmount(initialAmount);
    }

    @Test
    void onLand() {
        // Test that the effect should add the player some money
        GainMoneyEffect gainMoneyEffect = new GainMoneyEffect("Gain Some Money", player, amountToBeAdded);
        gainMoneyEffect.triggerOnLand();
        assertEquals(initialAmount + amountToBeAdded, player.getAmount());
    }

    @Test
    void onEnter() {
        // Test that the effect should add the player some money
        GainMoneyEffect gainMoneyEffect = new GainMoneyEffect("Gain Some Money", player, amountToBeAdded);
        gainMoneyEffect.triggerOnLand();
        assertEquals(initialAmount + amountToBeAdded, player.getAmount());
    }
}