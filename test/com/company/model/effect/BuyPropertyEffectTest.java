package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuyPropertyEffectTest {

    final int customAmount = 1100;
    Player player;
    Property wanChai;
    LoseMoneyEffect loseMoneyEffect;
    BuyPropertyEffect buyPropertyEffect;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        wanChai = new Property("Wan Chai", 700, 65);

    }

    @Test
    void onLand() {
        // Test that the effect should change the property ownership and reduce player amount.
        player.setAmount(customAmount);
        loseMoneyEffect = new LoseMoneyEffect("Pay ownership fee", player, wanChai.getPrice());
        buyPropertyEffect = new BuyPropertyEffect("Buy Property", player, wanChai, loseMoneyEffect);
        buyPropertyEffect.onLand();
        assertEquals(player, wanChai.getOwner());
        assertEquals(customAmount - wanChai.getPrice(), player.getAmount());
    }
}