package com.company.model.component;

import com.company.model.component.block.NoEffectBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Unit Tests of board.
 */
class BoardTest {

    final String nameA = "No Effect Block A";
    final String nameB = "No Effect Block B";
    final String nameC = "No Effect Block C";
    final String nameD = "No Effect Block D";
    Board board;
    NoEffectBlock noEffectBlockA;
    NoEffectBlock noEffectBlockB;
    NoEffectBlock noEffectBlockC;
    NoEffectBlock noEffectBlockD;

    @BeforeEach
    void setUp() {
        board = new Board();

        noEffectBlockA = new NoEffectBlock(nameA);
        noEffectBlockB = new NoEffectBlock(nameB);
        noEffectBlockC = new NoEffectBlock(nameC);
        noEffectBlockD = new NoEffectBlock(nameD);
    }

    /**
     * Unit test of Board.addPath(Block start,Block destination)
     */
    @Test
    void addPathByBlock() {
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, noEffectBlockA);
        // Test that Board.addPath() method will not add path,when the node is not found.
        assertNull(board.getNextBlock(noEffectBlockC));
        assertNull(board.getNextBlock(noEffectBlockA));
        assertNull(board.getNextBlock(noEffectBlockB));
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, noEffectBlockA);
        // Test that Board.addPath() method can make a circular path.
        assertSame(noEffectBlockA, board.getNextBlock(noEffectBlockC));
        assertSame(noEffectBlockB, board.getNextBlock(noEffectBlockA));
        assertSame(noEffectBlockC, board.getNextBlock(noEffectBlockB));
    }

    /**
     * Unit test of Board.addPath(String start, String destination)
     */
    @Test
    void addPathByString() {
        board.addPath(nameA, nameB);
        board.addPath(nameB, nameC);
        board.addPath(nameC, nameA);
        // Test that Board.addPath() method will not add path,when the node is not found.
        assertNull(board.getNextBlock(noEffectBlockC));
        assertNull(board.getNextBlock(noEffectBlockA));
        assertNull(board.getNextBlock(noEffectBlockB));
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addPath(nameA, nameB);
        board.addPath(nameB, nameC);
        board.addPath(nameC, nameA);
        // Test that Board.addPath() method can make a circular path.
        assertSame(noEffectBlockA, board.getNextBlock(nameC));
        assertSame(noEffectBlockB, board.getNextBlock(nameA));
        assertSame(noEffectBlockC, board.getNextBlock(nameB));
    }

    /**
     * Unit test of Board.addBlock()
     */
    @Test
    void addBlock() {
        board.addBlock(noEffectBlockA);
        // Test if the block is added to the board.
        assertSame(noEffectBlockA, board.getBlocks().get(0));
    }

    /**
     * Unit test of Board.findBlock()
     */
    @Test
    void findBlock() {
        board.addBlock(noEffectBlockA);
        // Test if the method can use a name to return a block with the same name.
        assertSame(noEffectBlockA, board.findBlock(nameA));
    }

    /**
     * Unit test of Board.getNextBlock(Block block)
     */
    @Test
    void getNextBlock() {
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, noEffectBlockA);
        // Test if the method can return the next block.
        assertSame(noEffectBlockA, board.getNextBlock(noEffectBlockC));
        assertSame(noEffectBlockB, board.getNextBlock(noEffectBlockA));
        assertSame(noEffectBlockC, board.getNextBlock(noEffectBlockB));
        // Test if the method should return null, when the input block does not exist.
        assertNull(board.getNextBlock(noEffectBlockD));
    }

    /**
     * Unit test of Board.getNextBlock(String name)
     */
    @Test
    void getNextBlockByName() {
        board.addBlock(noEffectBlockA);
        board.addBlock(noEffectBlockB);
        board.addBlock(noEffectBlockC);
        board.addPath(noEffectBlockA, noEffectBlockB);
        board.addPath(noEffectBlockB, noEffectBlockC);
        board.addPath(noEffectBlockC, noEffectBlockA);
        // Test if the method can return the next block by name.
        assertSame(noEffectBlockA, board.getNextBlock(nameC));
        assertSame(noEffectBlockB, board.getNextBlock(nameA));
        assertSame(noEffectBlockC, board.getNextBlock(nameB));
        // Test if the method should return null, when the name of the corresponding block does not exist.
        assertNull(board.getNextBlock(nameD));
    }

}