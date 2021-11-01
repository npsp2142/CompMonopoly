package com.company.model;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.GoBlock;
import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameLoaderTest {

    GameSystem gameSystem;
    GoBlock goBlock;
    PlayerLocation playerLocation;
    Player playerA;
    NoEffectBlock noEffectBlockB;
    int customAmount;

    @BeforeEach
    void setUp() {
    }

    @Test
    void load() throws IOException {
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
    }
}