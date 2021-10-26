package component;

import com.company.Main;
import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.PlayerFactory;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.*;
import com.company.model.effect.MoveEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BlockTest {
    Board board;
    PlayerLocation playerLocation;
    ArrayList<Player> players;
    GoBlock goBlock;
    PropertyBlock centralBlock;
    PropertyBlock wanChaiBlock;
    NoEffectBlock justVisitingBlock;
    InJailBlock inJailBlock;
    GoToJailBlock goToJailBlock;
    JustVisitingOrInJailBlock justVisitingOrInJailBlock;
    Player playerA;
    HashMap<Player, Integer> inJailRoundCounter;

    @BeforeEach
    void setUp() {

        new GameDisplay(System.out);


        Random random = new Random(3);
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.HEALTHY, Player.DEFAULT_AMOUNT);
        players = playerFactory.make(names);
        playerA = players.get(0);

        goBlock = new GoBlock("Go");

        Block startBlock = goBlock;
        board = new Board();
        playerLocation = new PlayerLocation(board, players, startBlock);

        centralBlock = new PropertyBlock("Central",
                new Property("Central", 800, 90));
        wanChaiBlock = new PropertyBlock("Wan Chai",
                new Property("Wan Chai", 700, 65));
        justVisitingBlock = new NoEffectBlock("Just Visiting");
        inJailRoundCounter = new HashMap<>();
        inJailRoundCounter.put(playerA, 0);
        inJailBlock = new InJailBlock("In Jail", playerLocation, inJailRoundCounter);
        justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(
                justVisitingBlock, inJailBlock
        );
        goToJailBlock = new GoToJailBlock("Go To Jail", playerLocation, justVisitingOrInJailBlock);

        board.addBlock(goBlock);
        board.addBlock(centralBlock);
        board.addBlock(wanChaiBlock);
        board.addBlock(justVisitingOrInJailBlock);
        board.addBlock(goToJailBlock);

        board.addPath(goBlock, goToJailBlock);
        board.addPath(goToJailBlock, centralBlock);
        board.addPath(centralBlock, wanChaiBlock);
        board.addPath(wanChaiBlock, justVisitingOrInJailBlock);
        board.addPath(justVisitingOrInJailBlock, goBlock);

        playerLocation.setStartLocation();
    }

    @Test
    public void MoveStepTest() {
        playerLocation.moveStep(playerA, 3);
        assert (playerA.getCurrentLocation(playerLocation).equals(wanChaiBlock));
    }

    @Test
    public void MoveToBlockTest() {
        playerLocation.moveTo(playerA, justVisitingOrInJailBlock, true);
        assert (playerA.getCurrentLocation(playerLocation).equals(justVisitingOrInJailBlock));
    }


    @Test
    public void GoToJail() {
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.YES);
        playerLocation.moveStep(playerA, 1);
        Block block = playerA.getCurrentLocation(playerLocation);
        assert (block.equals(justVisitingOrInJailBlock));

    }

    @Test
    public void SetInJail() {
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.YES);
        playerLocation.moveStep(playerA, 1);
        assert (playerA.getStatus().equals(Player.Status.GROUNDED));

    }

    @Test
    public void InJail() {
        new GameDisplay(System.out);
        MoveEffect effect;
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.NO);
        playerLocation.moveStep(playerA, 1);
        assert (inJailRoundCounter.get(playerA) == 0);

        effect = new MoveEffect("", playerA, playerA.roll(2), playerLocation);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 1);

        effect = new MoveEffect("", playerA, playerA.roll(2), playerLocation);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 2);

        effect = new MoveEffect("", playerA, playerA.roll(2), playerLocation);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 0);
    }

    @Test
    public void Test() throws IOException {
        byte[] data = "123,456,789,123,456,789\n".getBytes();

        InputStream input = new ByteArrayInputStream(data);

        FileOutputStream fileOutputStream = new FileOutputStream("tmp/output.txt");
        CompMonopolyApplication compMonopolyApplication = Main.createGameApplication(input, fileOutputStream);
        compMonopolyApplication.run();
        GameDisplay.flush();
    }


}
