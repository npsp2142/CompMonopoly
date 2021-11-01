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

    @Test
    void startGame() {
        gameSystem.startGame();
        for (Player player : gameSystem.getPlayers()) {
            assertEquals(Player.DEFAULT_AMOUNT, player.getAmount());
            assertEquals(Player.Status.HEALTHY, player.getStatus());
            assertEquals(goBlock, playerLocation.getCurrentLocation(player));
        }
    }

    @Test
    void endTurn() {
        int currentRound = gameSystem.getRound();
        Player currentPlayer = gameSystem.getCurrentPlayer();
        gameSystem.endTurn();
        assertNotEquals(currentPlayer, gameSystem.getCurrentPlayer());
        assertNotEquals(currentPlayer, gameSystem.getCurrentPlayer());
        assertEquals(currentRound + 1, gameSystem.getRound());
    }

    @Test
    void saveGame() throws IOException {
        FileWriter fileWriter = new FileWriter(GameSystem.DEFAULT_FILE_NAME);
        fileWriter.write(0);
        playerLocation.moveTo(playerA, noEffectBlockB);
        playerA.setAmount(customAmount);
        playerA.setStatus(Player.Status.BANKRUPT);
        gameSystem.saveGame();
        FileReader fileReader = new FileReader(GameSystem.DEFAULT_FILE_NAME);
        char[] chars = new char[1000];
        assertTrue(fileReader.read(chars) <= 0); // test if write any
        assertTrue(fileReader.read(chars) != 0);
    }

    @Test
    void loadGame() throws IOException {
        playerLocation.moveTo(playerA, noEffectBlockB);
        playerA.setAmount(customAmount);
        playerA.setStatus(Player.Status.BANKRUPT);
        gameSystem.saveGame();
        FileReader fileReader = new FileReader(GameSystem.DEFAULT_FILE_NAME);
        char[] chars = new char[1000];
        assertTrue(fileReader.read(chars) != 0);
        gameSystem.loadGame();
        Player loadedPlayer = gameSystem.getPlayers().get(0);
        assertEquals(playerA.getAmount(), loadedPlayer.getAmount());
        assertEquals(playerA.getStatus(), loadedPlayer.getStatus());
        assertEquals(playerLocation.getCurrentLocation(playerA), playerLocation.getCurrentLocation(loadedPlayer));
        assertEquals(CompMonopolyApplication.Status.PLAYING, compMonopolyApplication.getStatus());
    }
}