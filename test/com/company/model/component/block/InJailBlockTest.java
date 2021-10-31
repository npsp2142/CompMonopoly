package com.company.model.component.block;

import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        player.setRandom(new Random(1));
        Player.NEED_PROMPT = false;
        Board board = new Board();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        NoEffectBlock noEffectBlock = new NoEffectBlock("No Effect");
        playerLocation = new PlayerLocation(board, players, noEffectBlock);
        playerLocation.setStartLocation();
        inJailBlock = new InJailBlock("In Jail", playerLocation, new HashMap<>());
        board.addBlock(noEffectBlock);
        board.addBlock(inJailBlock);
        board.addPath(noEffectBlock, inJailBlock);
        board.addPath(inJailBlock, noEffectBlock);
    }

    /**
     * Unit test of InJailBlock.createOnLandEffect()
     */
    @Test
    void createOnLandEffect() {
        player.setResponse(Player.Response.YES);
        inJailBlock.createOnLandEffect(player).onLand();
        assertEquals(initialAmount - 150, player.getAmount());
        assertEquals(Player.Status.HEALTHY, player.getStatus());
    }
}