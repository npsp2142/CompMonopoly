import com.company.model.GameApplication;
import com.company.model.GameController;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;
import com.company.model.block.GoBlock;
import com.company.model.command.CommandFactory;
import com.company.model.command.StartCommand;
import com.company.model.component.Location;
import com.company.model.component.Property;
import com.company.model.block.Block;
import com.company.model.component.Board;
import com.company.model.block.PropertyBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CompMonopolyTest {
    Board board;


    @Test
    void MakeBoardTest() {
        Block block1 = new PropertyBlock("Tai O", new ArrayList<>(), new Property("Tai O", 600, 25));
        Block block2 = new PropertyBlock("Yuen Long", new ArrayList<>(), new Property("Yuen Long", 400, 25));
        board = new Board(block1);

        board.addBlock(block1);
        board.addBlock(block2);
        board.addPath("Tai O", "Yuen Long");
        Assertions.assertEquals(block2, board.getNextBlock("Tai O"));
    }

    @Test
    void CreateStartCommand() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("tmp/input_start.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("tmp/output.txt");
        new GameController(fileInputStream);
        new GameDisplay(fileOutputStream);
        Board board = new Board(new GoBlock("GO",new ArrayList<>()));
        GameSystem gameSystem = new GameSystem(board,
                new ArrayList<>(),
                new ArrayList<>(),
                new Location( board,new ArrayList<>())
        );
        GameApplication gameApplication = new GameApplication(new CommandFactory(gameSystem), GameApplication.Status.MENU);
        assert (gameApplication.getCommand() instanceof StartCommand);
        fileInputStream.close();
        fileOutputStream.close();
    }




}
