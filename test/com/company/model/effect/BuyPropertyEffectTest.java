package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuyPropertyEffectTest {

    private Player player;
    private Property wanChai;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        wanChai = new Property("Wan Chai", 700, 65);

    }

    @Test
    void onLand() {
        // Test that the effect should change the property ownership and reduce player amount.
        int customAmount = 1100;
        player.setAmount(customAmount);
        LoseMoneyEffect loseMoneyEffect = new LoseMoneyEffect("Pay ownership fee", player, wanChai.getPrice());
        BuyPropertyEffect buyPropertyEffect = new BuyPropertyEffect("Buy Property", player, wanChai, loseMoneyEffect);
        buyPropertyEffect.triggerOnLand();
        assertEquals(player, wanChai.getOwner());
        assertEquals(customAmount - wanChai.getPrice(), player.getAmount());
    }
}