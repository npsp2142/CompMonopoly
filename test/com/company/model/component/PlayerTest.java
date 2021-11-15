package com.company.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    Player player;
    int amountToBeAdded;
    int amountToBeReduced;
    int randomAmount;

    @BeforeEach
    void setUp() {
        player = new Player("Player A");
        player.setAmount(Player.DEFAULT_AMOUNT);
        amountToBeAdded = 1020;
        amountToBeReduced = 105;
        randomAmount = 15615;
    }

    @Test
    void addAmount() {
        player.addAmount(amountToBeAdded);
        assertEquals(Player.DEFAULT_AMOUNT + amountToBeAdded, player.getAmount());
    }

    @Test
    void reduceAmount() {
        player.reduceAmount(amountToBeReduced);
        assertEquals(Player.DEFAULT_AMOUNT - amountToBeReduced, player.getAmount());
    }

    @Test
    void reset() {
        player.setAmount(randomAmount);
        player.setStatus(Player.Status.BANKRUPT);
        player.reset();
        assertEquals(Player.DEFAULT_AMOUNT, player.getAmount());
        assertEquals(Player.Status.NORMAL, player.getStatus());
    }
}