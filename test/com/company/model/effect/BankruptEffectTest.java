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
        AbandonPropertyEffect abandonPropertyEffect = new AbandonPropertyEffect("Unowned Property", wanChai);
        ArrayList<AbandonPropertyEffect> abandonPropertyEffects = new ArrayList<>();
        abandonPropertyEffects.add(abandonPropertyEffect);
        BankruptEffect bankruptEffect = new BankruptEffect("Bankrupt", player, abandonPropertyEffects);
        bankruptEffect.onLand();
        assertEquals(Player.Status.NORMAL, player.getStatus()); // Test if the player is bankrupt
        player.setAmount(0);
        bankruptEffect.onLand();
        assertEquals(Player.Status.BANKRUPT, player.getStatus()); // Test if the player is bankrupt
    }
}