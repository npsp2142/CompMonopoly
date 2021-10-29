package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnLandEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ChanceBlockTest {

    final int initialAmount = 2000;
    ChanceBlock chanceBlock;
    Random randomA;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player A");
        randomA = new Random(1);
        chanceBlock = new ChanceBlock("Testing", randomA);
        player.setAmount(initialAmount);
    }

    @Test
    void createOnLandEffect() {
        OnLandEffect onLandEffect = chanceBlock.createOnLandEffect(player);
        onLandEffect.onLand();
        assertNotEquals(initialAmount, player.getAmount());
    }

}