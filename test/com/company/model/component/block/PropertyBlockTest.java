package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyBlockTest {
    PropertyBlock propertyBlock;
    Property wanChai;
    Player player;
    int initialAmount;

    @BeforeEach
    void setUp() {
        initialAmount = 1500;
        player = new Player("Player");
        player.setAmount(initialAmount);
        wanChai = new Property("Wan Chai", 700, 65);
        propertyBlock = new PropertyBlock("Wan Chai", wanChai);
    }

    @Test
    void createOnLandEffect() {
        int old = player.getAmount();
        player.setResponse(Player.Response.YES);
        propertyBlock.createOnLandEffect(player).onLand();
        assertEquals(player, wanChai.getOwner());
        int diff = old - player.getAmount();
        assertEquals(wanChai.getPrice(), diff);
    }

}