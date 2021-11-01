package com.company.model;

import com.company.model.component.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Case for GameSystemTest
 */
class GameSystemTest {
    private final ArrayList<Player> players;
    private Player currentPlayer;

    GameSystemTest(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Test Case for GameSystemTest.startGame()
     */
    @Test
    void startGame() {
        assertEquals(CompMonopolyApplication.Status.PLAYING,CompMonopolyApplication.instance.getStatus());//Test Game status
    }

    /**
     * Test Case for GameSystemTest.endTurn()
     */
    @Test
    void endTurn() {
        assertEquals(CompMonopolyApplication.Status.MENU,CompMonopolyApplication.instance.getStatus());// Test Game status when only 1 player left or 100 rounds ended.
        assertEquals(players.get(0),currentPlayer );//Test if the player is the last player
    }

    @Test
    void saveGame() {
    }

    @Test
    void loadGame() {
    }
}