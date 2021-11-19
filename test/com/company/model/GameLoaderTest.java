package com.company.model;

import com.company.model.command.CommandFactory;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.Block;
import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for GameLoader
 */
class GameLoaderTest {

    private GameSystem gameSystem;
    private PlayerLocation playerLocation;
    private Player playerA;
    private NoEffectBlock noEffectBlockA;
    private NoEffectBlock noEffectBlockB;
    private NoEffectBlock noEffectBlockC;
    private NoEffectBlock noEffectBlockD;
    private int customAmount;

    @BeforeEach
    void setUp() {
        customAmount = 1050;
        playerA = new Player("Player A");
        playerA.setAmount(Player.DEFAULT_AMOUNT);
        noEffectBlockA = new NoEffectBlock("No Effect");
        noEffectBlockB = new NoEffectBlock("No Effect");
        noEffectBlockC = new NoEffectBlock("No Effect");
        noEffectBlockD = new NoEffectBlock("No Effect");
        ArrayList<Property> properties = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        players.add(playerA);
        Board board = new Board();
        board.addBlock(noEffectBlockB);
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, noEffectBlockD);
        board.addPath(noEffectBlockD, noEffectBlockA);
        playerLocation = new PlayerLocation(board, players, noEffectBlockA);
        playerLocation.setStartLocation();
        gameSystem = new GameSystem(board, players, properties, playerLocation);
        new CompMonopolyApplication(new CommandFactory(gameSystem));
        new GameDisplay(System.out);
        gameSystem.setCurrentPlayer(playerA);

        playerLocation.moveTo(playerA, noEffectBlockB);
        playerA.setAmount(customAmount);
        playerA.setStatus(Player.Status.BANKRUPT);
        Map<Player, Integer> playerIntegerMap = new HashMap<>();
        for(Player player: players){
            playerIntegerMap.put(player,0);
        }
        gameSystem.setJailRoundCounter(playerIntegerMap);
        gameSystem.saveGame();
    }

    /**
     * Unit tests for GameLoader.load()
     */
    @Test
    void load() {
        Block savedLocation = playerLocation.getCurrentLocation(playerA);
        gameSystem.loadGame();
        Player loadedPlayer = gameSystem.getPlayers().get(0);
        // Test if the save file loads the correct player data.
        assertEquals(playerA.getAmount(), loadedPlayer.getAmount());
        assertEquals(playerA.getStatus(), loadedPlayer.getStatus());
        assertEquals(savedLocation, playerLocation.getCurrentLocation(loadedPlayer));
    }
}