package com.company.model;

import com.company.model.command.CommandFactory;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests of GameSystem
 */
class GameSystemTest {

    private GameSystem gameSystem;
    private PlayerLocation playerLocation;
    private Player playerA;
    private Player playerB;
    private NoEffectBlock noEffectBlockA;
    private NoEffectBlock noEffectBlockB;
    private NoEffectBlock noEffectBlockC;
    private NoEffectBlock noEffectBlockD;
    private int customAmount;

    @BeforeEach
    void setUp() {
        customAmount = 1050;
        playerA = new Player("Player A");
        playerB = new Player("Player B");
        playerA.setAmount(Player.DEFAULT_AMOUNT);
        playerB.setAmount(Player.DEFAULT_AMOUNT);
        playerA.setStatus(Player.Status.NORMAL);
        playerB.setStatus(Player.Status.NORMAL);
        noEffectBlockA = new NoEffectBlock("No Effect A");
        noEffectBlockB = new NoEffectBlock("No Effect B");
        noEffectBlockC = new NoEffectBlock("No Effect C");
        noEffectBlockD = new NoEffectBlock("No Effect D");
        ArrayList<Property> properties = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        Board board = new Board();
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addBlock(noEffectBlockD);
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
        Map<Player, Integer> roundCounter = new HashMap<>();
        for(Player player: players){
            roundCounter.put(player,0);
        }
        gameSystem.setJailRoundCounter(roundCounter);


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
            assertEquals(noEffectBlockA, playerLocation.getCurrentLocation(player)); // Test if players are at Go
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
        gameSystem.endTurn();
        assertEquals(currentRound + 1, gameSystem.getRound()); // Test if the round counter increases
    }

    /**
     * Unit test of GameSystem.saveGame()
     */
    @Test
    void saveGame() throws IOException {
        FileWriter fileWriter = new FileWriter(GameSystem.DEFAULT_FOLDER + "\\" + GameSystem.DEFAULT_FILE_NAME);
        fileWriter.write(0);
        fileWriter.close();
        playerLocation.moveTo(playerA, noEffectBlockB);
        playerA.setAmount(customAmount);
        playerA.setStatus(Player.Status.BANKRUPT);
        gameSystem.saveGame();
        FileReader fileReader = new FileReader(GameSystem.DEFAULT_FOLDER + "\\" + GameSystem.DEFAULT_FILE_NAME);
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
        playerA.setStatus(Player.Status.GROUNDED);
        int savedAmount = playerA.getAmount();
        Player.Status savedStatus = playerA.getStatus();
        gameSystem.saveGame();
        gameSystem.loadGame();
        Player loadedPlayer = null;
        for (Player player : gameSystem.getPlayers()) {
            if (player.getName().equals("Player A")) {
                loadedPlayer = player;
                break;
            }
        }
        // Test if the save file loads the correct player data.
        assert (loadedPlayer != null);
        assertEquals(savedAmount, loadedPlayer.getAmount());
        assertEquals(savedStatus, loadedPlayer.getStatus());
        assertEquals(noEffectBlockB, playerLocation.getCurrentLocation(loadedPlayer));
        // Test if the game starts after loading.
        assertEquals(CompMonopolyApplication.Status.PLAYING, CompMonopolyApplication.instance.getStatus());
    }
}