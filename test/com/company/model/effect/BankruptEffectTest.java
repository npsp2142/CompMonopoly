package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for BankruptEffect
 */
class BankruptEffectTest {

    Player player;
    Property wanChai;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        player.setAmount(Player.DEFAULT_AMOUNT);
        player.setStatus(Player.Status.NORMAL);
        wanChai = new Property("Wan Chai", 700, 65);
    }

    /**
     * Unit test for BankruptEffect.onLand()
     */
    @Test
    void onLand() {
        // Test that the effect should change player status to BANKRUPT
        AbandonPropertyEffect abandonPropertyEffect = new AbandonPropertyEffect("Unowned Property", wanChai);
        ArrayList<AbandonPropertyEffect> abandonPropertyEffects = new ArrayList<>();
        abandonPropertyEffects.add(abandonPropertyEffect);
        BankruptEffect bankruptEffect = new BankruptEffect("Bankrupt", player, abandonPropertyEffects);
        bankruptEffect.triggerOnLand();
        assertEquals(Player.Status.BANKRUPT, player.getStatus()); // Test if the player is bankrupt
    }
}