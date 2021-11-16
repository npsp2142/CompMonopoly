package com.company.model.component;

import com.company.model.component.block.ChanceBlock;
import com.company.model.component.block.GoBlock;
import com.company.model.component.block.JailBlock;
import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PlayerLocationTest {

    Player playerA;
    Player playerB;
    PlayerLocation playerLocation;
    int initialAmount;
    NoEffectBlock noEffectBlockA;
    NoEffectBlock noEffectBlockB;
    NoEffectBlock noEffectBlockC;
    NoEffectBlock noEffectBlockD;
    NoEffectBlock noEffectBlockE;
    NoEffectBlock noEffectBlockF;
    NoEffectBlock noEffectBlockG;
    GoBlock goBlock;
    ChanceBlock chanceBlock;

    @BeforeEach
    void setUp() {
        initialAmount = 1500;
        playerA = new Player("Player A");
        playerB = new Player("Player B");
        playerA.setAmount(initialAmount);
        Player.NEED_PROMPT = false;
        Board board = new Board();
        ArrayList<Player> players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        noEffectBlockA = new NoEffectBlock("No Effect A");
        noEffectBlockB = new NoEffectBlock("No Effect B");
        noEffectBlockC = new NoEffectBlock("No Effect C");
        noEffectBlockD = new NoEffectBlock("No Effect D");
        noEffectBlockE = new NoEffectBlock("No Effect E");
        noEffectBlockF = new NoEffectBlock("No Effect F");
        noEffectBlockG = new NoEffectBlock("No Effect G");
        goBlock = new GoBlock("Go");
        chanceBlock = new ChanceBlock("Change", new Random(1));
        playerLocation = new PlayerLocation(board, players, noEffectBlockA);
        playerLocation.setStartLocation();
        JailBlock.IS_RANDOM = false;
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addBlock(noEffectBlockD);
        board.addBlock(noEffectBlockE);
        board.addBlock(noEffectBlockF);
        board.addBlock(goBlock);
        board.addBlock(chanceBlock);
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, goBlock);
        board.addPath(goBlock, noEffectBlockD);
        board.addPath(noEffectBlockD, noEffectBlockE);
        board.addPath(noEffectBlockE, noEffectBlockF);
        board.addPath(noEffectBlockF, chanceBlock);
        board.addPath(chanceBlock, noEffectBlockA);
    }

    @Test
    void setStartLocation() {
        playerLocation.moveTo(playerA, noEffectBlockF);
        playerLocation.moveTo(playerB, noEffectBlockE);
        playerLocation.setStartLocation();
        assertEquals(noEffectBlockA, playerA.getCurrentLocation(playerLocation));
        assertEquals(noEffectBlockA, playerB.getCurrentLocation(playerLocation));
    }

    @Test
    void moveStep() {
        playerLocation.moveStep(playerA, 4);
        assertEquals(noEffectBlockD, playerA.getCurrentLocation(playerLocation));
        assertEquals(initialAmount + GoBlock.SALARY, playerA.getAmount()); // player pass the "GO" once.
        playerLocation.moveStep(playerA, 3);
        assertEquals(chanceBlock, playerA.getCurrentLocation(playerLocation));
        assertNotEquals(initialAmount + GoBlock.SALARY, playerA.getAmount()); // player amount change by chance block.
    }

    @Test
    void getCurrentLocation() {
        playerLocation.moveTo(playerA, goBlock);
        assertEquals(goBlock, playerA.getCurrentLocation(playerLocation));
        playerLocation.moveStep(playerA, 3);
        assertEquals(noEffectBlockF, playerA.getCurrentLocation(playerLocation));
    }

    @Test
    void moveTo() {
        playerLocation.moveTo(playerA, goBlock);
        assertEquals(goBlock, playerA.getCurrentLocation(playerLocation));
        playerLocation.moveTo(playerA, noEffectBlockG); // noEffectBlockG do not appear in board
        assertNotEquals(noEffectBlockG, playerA.getCurrentLocation(playerLocation));
    }

    @Test
    void testMoveTo() {
        playerLocation.moveTo(playerA, goBlock.getName(), true);
        assertEquals(goBlock, playerA.getCurrentLocation(playerLocation));
        playerLocation.moveTo(playerA, noEffectBlockG.getName(), true);
        assertNotEquals(noEffectBlockG, playerA.getCurrentLocation(playerLocation));
    }
}