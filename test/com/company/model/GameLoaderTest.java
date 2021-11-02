package com.company.model;

import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for GameLoader
 */
class GameLoaderTest {

    GameSystem gameSystem;
    PlayerLocation playerLocation;
    Player playerA;
    NoEffectBlock noEffectBlockB;
    int customAmount;

    @BeforeEach
    void setUp() {
    }

    /**
     * Unit tests for GameLoader.load()
     */
    @Test
    void load() {
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
    }
}