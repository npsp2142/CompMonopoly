package com.company.model.component.block;

import com.company.model.component.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncomeTaxBlockTest {

    Player player;
    IncomeTaxBlock incomeTaxBlock;
    int initialAmount;

    @BeforeEach
    void setUp() {
        incomeTaxBlock = new IncomeTaxBlock("Income Tax");
        player = new Player("Player");
        initialAmount = 1500;
        player.setAmount(initialAmount);
    }

    /**
     * Unit test of IncomeTaxBlock.createOnLandEffect()
     */
    @Test
    void createOnLandEffect() {
        int oldAmount = player.getAmount();
        incomeTaxBlock.createOnLandEffect(player).onLand();
        int diff = player.getAmount() - oldAmount;
        assertEquals(-initialAmount * incomeTaxBlock.INCOME_TAX_RATE, diff);
    }
}