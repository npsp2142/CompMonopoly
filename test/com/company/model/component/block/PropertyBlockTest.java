package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests of PropertyBlock
 */
class PropertyBlockTest {
    PropertyBlock propertyBlock;
    Property wanChai;
    Player player;
    int initialAmount;

    @BeforeEach
    void setUp() {
        initialAmount = 100;
        player = new Player("Player");
        player.setAmount(initialAmount);
        wanChai = new Property("Wan Chai", 700, 65);
        propertyBlock = new PropertyBlock("Wan Chai", wanChai);
        Player.NEED_PROMPT = false;
    }

    /**
     * Unit test of PropertyBlock.createOnLandEffect()
     */
    @Test
    void createOnLandEffect() {
        assertNull(wanChai.getOwner());
        // Test if no money buy
        player.setResponse(Player.Response.YES);
        propertyBlock.createOnLandEffect(player).triggerOnLand();
        assertNull(wanChai.getOwner());
        player.setAmount(Player.DEFAULT_AMOUNT);
        int old = player.getAmount();
        player.setResponse(Player.Response.YES);
        propertyBlock.createOnLandEffect(player).triggerOnLand();
        assertEquals(player, wanChai.getOwner());
        int diff = old - player.getAmount();
        assertEquals(wanChai.getPrice(), diff);
    }

}