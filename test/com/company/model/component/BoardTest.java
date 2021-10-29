package com.company.model.component;

import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    NoEffectBlock noEffectBlockA;
    NoEffectBlock noEffectBlockB;
    NoEffectBlock noEffectBlockC;
    final String nameA = "No Effect Block A";
    final String nameB = "No Effect Block B";
    final String nameC = "No Effect Block C";

    @BeforeEach
    void setUp() {
        board = new Board();

        noEffectBlockA = new NoEffectBlock(nameA);
        noEffectBlockB = new NoEffectBlock(nameB);
        noEffectBlockC = new NoEffectBlock(nameC);
    }

    @Test
    void addPath() {
        board.addPath(noEffectBlockA,noEffectBlockB);
        board.addPath(noEffectBlockB,noEffectBlockC);
        board.addPath(noEffectBlockC,noEffectBlockA);
        assertSame(noEffectBlockA,board.getNextBlock(noEffectBlockC));
        assertSame(noEffectBlockB,board.getNextBlock(noEffectBlockA));
        assertSame(noEffectBlockC,board.getNextBlock(noEffectBlockB));
    }

    @Test
    void testAddPath() {
        board.addPath(nameA,nameB);
        board.addPath(nameB,nameC);
        board.addPath(nameC,nameA);
        assertNull(board.getNextBlock(noEffectBlockC));
        assertNull(board.getNextBlock(noEffectBlockA));
        assertNull(board.getNextBlock(noEffectBlockB));
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addPath(nameA,nameB);
        board.addPath(nameB,nameC);
        board.addPath(nameC,nameA);
        assertSame(noEffectBlockA,board.getNextBlock(nameC));
        assertSame(noEffectBlockB,board.getNextBlock(nameA));
        assertSame(noEffectBlockC,board.getNextBlock(nameB));
    }

    @Test
    void addBlock() {
        board.addBlock(noEffectBlockA);
        assertSame(noEffectBlockA,board.getBlocks().get(0));
    }

    @Test
    void findBlock() {
        board.addBlock(noEffectBlockA);
        assertSame(noEffectBlockA,board.findBlock(nameA));
    }

    @Test
    void getNextBlock() {
        board.addPath(noEffectBlockA,noEffectBlockB);
        board.addPath(noEffectBlockB,noEffectBlockC);
        board.addPath(noEffectBlockC,noEffectBlockA);
        assertSame(noEffectBlockA,board.getNextBlock(noEffectBlockC));
        assertSame(noEffectBlockB,board.getNextBlock(noEffectBlockA));
        assertSame(noEffectBlockC,board.getNextBlock(noEffectBlockB));
    }

    @Test
    void testGetNextBlock() {
        board.addPath(noEffectBlockA,noEffectBlockB);
        board.addPath(noEffectBlockB,noEffectBlockC);
        board.addPath(noEffectBlockC,noEffectBlockA);
        assertSame(noEffectBlockA,board.getNextBlock(nameC));
        assertSame(noEffectBlockB,board.getNextBlock(nameA));
        assertSame(noEffectBlockC,board.getNextBlock(nameB));
    }

}