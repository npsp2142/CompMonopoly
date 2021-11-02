package com.company.model;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.GoBlock;
import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests of GameSystem
 */
class GameSystemTest {

    GameSystem gameSystem;
    GoBlock goBlock;
    PlayerLocation playerLocation;
    Player playerA;
    NoEffectBlock noEffectBlockB;
    int customAmount;
    CompMonopolyApplication compMonopolyApplication;

    @BeforeEach
    void setUp() {
    }

    /**
     * Unit test of GameSystem.startGame()
     */
    @Test
    void startGame() {
        gameSystem.startGame();
        for (Player player : gameSystem.getPlayers()) {
            assertEquals(Player.DEFAULT_AMOUNT, player.getAmount()); // Test if players have 1500 at start
            assertEquals(Player.Status.NORMAL, player.getStatus()); // Test if players are healthy at start
            assertEquals(goBlock, playerLocation.getCurrentLocation(player)); // Test if players are at Go
        }
    }

    /**
     * Unit test of GameSystem.endTurn()
     */
    @Test
    void endTurn() {
        int currentRound = gameSystem.getRound();
        Player currentPlayer = gameSystem.getCurrentPlayer();
        gameSystem.endTurn();
        assertNotEquals(currentPlayer, gameSystem.getCurrentPlayer()); // Test if the current player changes
        assertEquals(currentRound + 1, gameSystem.getRound()); // Test if the round counter increases
    }

    /**
     * Unit test of GameSystem.saveGame()
     */
    @Test
    void saveGame() throws IOException {
        FileWriter fileWriter = new FileWriter(GameSystem.DEFAULT_FILE_NAME);
        fileWriter.write(0);
        fileWriter.close();
        playerLocation.moveTo(playerA, noEffectBlockB);
        playerA.setAmount(customAmount);
        playerA.setStatus(Player.Status.BANKRUPT);
        gameSystem.saveGame();
        FileReader fileReader = new FileReader(GameSystem.DEFAULT_FILE_NAME);
        char[] chars = new char[1000];
        // Test if write any characters.
        assertTrue(fileReader.read(chars) > 0);
    }

    /**
     * Unit test of GameSystem.loadGame()
     */
    @Test
    void loadGame() {
        playerLocation.moveTo(playerA, noEffectBlockB);
        playerA.setAmount(customAmount);
        playerA.setStatus(Player.Status.BANKRUPT);
        gameSystem.saveGame();
        gameSystem.loadGame();
        Player loadedPlayer = gameSystem.getPlayers().get(0);
        // Test if the save file loads the correct player data.
        assertEquals(playerA.getAmount(), loadedPlayer.getAmount());
        assertEquals(playerA.getStatus(), loadedPlayer.getStatus());
        assertEquals(playerLocation.getCurrentLocation(playerA), playerLocation.getCurrentLocation(loadedPlayer));
        // Test if the game starts after loading.
        assertEquals(CompMonopolyApplication.Status.PLAYING, compMonopolyApplication.getStatus());
    }
}