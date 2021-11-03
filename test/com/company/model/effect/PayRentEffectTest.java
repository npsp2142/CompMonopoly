package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayRentEffectTest {

    final int initialAmount = 1888;
    Player playerA;
    Player playerB;
    Property wanChai;

    @BeforeEach
    void setUp() {
        wanChai = new Property("Wan Chai", 700, 65);
        playerA = new Player("Player A");
        playerA.setAmount(initialAmount);
        playerB = new Player("Player B");

        playerB.setAmount(initialAmount);

    }

    @Test
    void onLand() {
        // Test that the effect should reduce one player money, and increase one player money.
        GainMoneyEffect gainMoneyEffect = new GainMoneyEffect("Get Rent", playerA, wanChai.getRent());
        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Get Rent", playerB, wanChai.getRent());
        PayRentEffect payRentEffect = new PayRentEffect("Pay Rent", gainMoneyEffect, loseMoneyEffect);
        payRentEffect.triggerOnLand();
        assertEquals(initialAmount + wanChai.getRent(), playerA.getAmount());
        assertEquals(initialAmount - wanChai.getRent(), playerB.getAmount());
    }
}