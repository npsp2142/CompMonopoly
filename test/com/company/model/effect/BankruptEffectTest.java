package com.company.model.effect;

import com.company.model.command.HelpCommand;
import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.component.block.PropertyBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Test case for BankruptEffect
 */
class BankruptEffectTest {

    Player player;
    int initialAmount;
    BankruptEffect bankruptEffect;
    ArrayList<AbandonPropertyEffect> abandonPropertyEffects = new ArrayList<>();


    @BeforeEach
    void setUp() {
        initialAmount = 1000;
        player = new Player("Player");
        player.setAmount(initialAmount);
        player.setStatus(Player.Status.HEALTHY);
        bankruptEffect=new BankruptEffect("Bankrupt",player,abandonPropertyEffects);

    }

    /**
     * Unit Test case for BankruptEffect.onLand()
     */
    @Test
    void onLand() {
        assertEquals(Player.Status.HEALTHY, player.getStatus()); // Test if the player is bankrupt
        player.addAmount(-1001);
        bankruptEffect.onLand();
        assert(player.getStatus().equals(Player.Status.BANKRUPT));// Test if the player is bankrupt
    }
}