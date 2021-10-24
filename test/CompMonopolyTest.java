import com.company.model.ANSI;
import com.company.model.component.Property;
import com.company.model.block.Block;
import com.company.model.component.Board;
import com.company.model.GameApplication;
import com.company.model.block.PropertyBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CompMonopolyTest {
    Board board;
    GameApplication gameApplication;

    @BeforeEach
    void setUp() {

    }

    @Test
    void MakeBoardTest() {
        Block block1 = new PropertyBlock("Tai O",new ArrayList<>(),new Property("Tai O",600 ,25));
        Block block2 = new PropertyBlock("Yuen Long",new ArrayList<>(),new Property("Yuen Long",400 ,25));
        board = new Board(block1);

        board.addBlock(block1);
        board.addBlock(block2);
        board.addPath("Tai O", "Yuen Long");
        Assertions.assertEquals(block2,board.getNextBlock("Tai O"));
    }

    @Test
    void CommandLineTest(){
        String data2 = "quit\n";
        String output = feedDataToConsole(data2);
        Assertions.assertEquals(
                "Welcome to COMP Monopoly. Type \"start\" to start the game\r\n"+
                        ANSI.ANSI_GREEN+" > " + ANSI.ANSI_RESET+ "Goodbye! See you next time!\r\n",output);
    }

    String feedDataToConsole(String data){
        InputStream oldInput = System.in;
        PrintStream oldOutput = System.out;
        String output;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
            ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(consoleOutput));
            output = consoleOutput.toString();
            gameApplication.run();

        }finally {
            System.setIn(oldInput);
            System.setOut(oldOutput);
        }
        return output;
    }

    /*@Test
    void MoveBoardTest() {
        String data = "start\nquit\n";
        InputStream oldInput = System.in;
        PrintStream oldOutput = System.out;
        String output;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
            ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(consoleOutput));
            game.run();
            output = consoleOutput.toString();
        }finally {
            System.setIn(oldInput);
            System.setOut(oldOutput);
        }

        Assertions.assertEquals(
                "Welcome to COMP Monopoly. Type \"start\" to start the game\r\n"+
                        "GAME START!\r\n" +
                        "Goodbye! See you next time!\r\n",output);
    }*/
}
