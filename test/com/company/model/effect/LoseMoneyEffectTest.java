package com.company.model.effect;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoseMoneyEffectTest {

    final int initialAmount = 1050;
    final int amountToBeReduced = 100;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        player.setAmount(initialAmount);
    }

    @Test
    void onLand() {
        // Test that the effect should reduce the player some money
        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Lose Some Money", player, amountToBeReduced);
        loseMoneyEffect.triggerOnLand();
        assertEquals(initialAmount - amountToBeReduced, player.getAmount());
    }
}