package componentTest;

import com.company.model.component.Board;
import com.company.model.component.Property;
import com.company.model.component.block.Block;
import com.company.model.component.block.PropertyBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompMonopolySystemTest {
    Board board;

    @Test
    void MakeBoardTest() {
        Block block1 = new PropertyBlock("Tai O", new Property("Tai O", 600, 25));
        Block block2 = new PropertyBlock("Yuen Long", new Property("Yuen Long", 400, 25));

        board = new Board();

        board.addBlock(block1);
        board.addBlock(block2);
        board.addPath("Tai O", "Yuen Long");
        Assertions.assertEquals(block2, board.getNextBlock("Tai O"));
    }
}
