package com.company.model.component.block;

import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests of InJailBlock
 */
class InJailBlockTest {

    InJailBlock inJailBlock;
    Player player;
    PlayerLocation playerLocation;
    int initialAmount;

    @BeforeEach
    void setUp() {
        initialAmount = 1500;
        player = new Player("Player");
        player.setAmount(initialAmount);
        player.setRandom(new Random(2));
        Player.NEED_PROMPT = false;
        Board board = new Board();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        NoEffectBlock noEffectBlock = new NoEffectBlock("No Effect");
        NoEffectBlock noEffectBlockA = new NoEffectBlock("No Effect");
        NoEffectBlock noEffectBlockB = new NoEffectBlock("No Effect");
        NoEffectBlock noEffectBlockC = new NoEffectBlock("No Effect");
        NoEffectBlock noEffectBlockD = new NoEffectBlock("No Effect");
        NoEffectBlock noEffectBlockE = new NoEffectBlock("No Effect");
        NoEffectBlock noEffectBlockF = new NoEffectBlock("No Effect");
        playerLocation = new PlayerLocation(board, players, noEffectBlock);
        playerLocation.setStartLocation();
        Map<Player, Integer> roundCounter = new HashMap<>();
        inJailBlock = new InJailBlock("In Jail", playerLocation, roundCounter);
        roundCounter.put(player, 0);
        InJailBlock.IS_RANDOM = false;
        board.addBlock(noEffectBlock);
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addBlock(noEffectBlockD);
        board.addBlock(noEffectBlockE);
        board.addBlock(noEffectBlockF);
        board.addBlock(inJailBlock);
        board.addPath(noEffectBlock, inJailBlock);
        board.addPath(inJailBlock, noEffectBlockA);
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, noEffectBlockD);
        board.addPath(noEffectBlockD, noEffectBlockE);
        board.addPath(noEffectBlockE, noEffectBlockF);
        board.addPath(noEffectBlockF, noEffectBlock);
    }

    /**
     * Unit test of InJailBlock.createOnLandEffect()
     */
    @Test
    void createOnLandEffect() {
        int[] rolls;

        // Pay to leave
        player.setResponse(Player.Response.YES); // Pay to lave
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(initialAmount - InJailBlock.FINE, player.getAmount());
        assertEquals(Player.Status.NORMAL, player.getStatus());

        // Roll to leave successful
        player.setStatus(Player.Status.GROUNDED);
        player.setResponse(Player.Response.NO);
        rolls = new int[]{2, 2};
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(Player.Status.NORMAL, player.getStatus());

        // Roll to leave failed 3 times
        player.setAmount(initialAmount);
        player.setStatus(Player.Status.GROUNDED);
        player.setResponse(Player.Response.NO);
        rolls = new int[]{2, 3};
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(Player.Status.GROUNDED, player.getStatus());
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(Player.Status.GROUNDED, player.getStatus());
        rolls = new int[]{2, 3};
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(initialAmount - InJailBlock.FINE, player.getAmount());
        assertEquals(Player.Status.NORMAL, player.getStatus());

        // Roll to leave success at the 3rd time
        player.setAmount(initialAmount);
        player.setStatus(Player.Status.GROUNDED);
        player.setResponse(Player.Response.NO);
        rolls = new int[]{2, 3};
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(Player.Status.GROUNDED, player.getStatus());
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(Player.Status.GROUNDED, player.getStatus());
        rolls = new int[]{2, 2};
        inJailBlock.setDiceRolls(rolls);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(initialAmount, player.getAmount());
        assertEquals(Player.Status.NORMAL, player.getStatus());
    }
}