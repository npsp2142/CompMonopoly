import com.company.Main;
import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.PlayerFactory;
import com.company.model.component.block.*;
import com.company.model.component.*;
import com.company.model.effect.MoveEffect;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.EffectObserver;
import com.company.model.observer.PlayerObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BlockTest {
    Board board;
    Location location;
    ArrayList<Player> players;
    GoBlock goBlock;
    PropertyBlock centralBlock;
    PropertyBlock wanChaiBlock;
    NoEffectBlock justVisitingBlock;
    InJailBlock inJailBlock;
    GoToJailBlock goToJailBlock;
    JustVisitingOrInJailBlock justVisitingOrInJailBlock;
    Player playerA;
    HashMap<Player,Integer> inJailRoundCounter;
    ArrayList<BlockObserver> blockObservers;
    ArrayList<EffectObserver> effectObservers;

    @BeforeEach
    void setUp() {

        new GameDisplay(System.out);

        blockObservers = new ArrayList<>();

        Random random = new Random(3);
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        ArrayList<PlayerObserver> playerObservers = new ArrayList<>();
        PlayerFactory playerFactory = new PlayerFactory(names,random,playerObservers,Player.Status.HEALTHY,Player.DEFAULT_AMOUNT);
        players = playerFactory.make();

        goBlock = new GoBlock("Go", blockObservers, effectObservers);

        board = new Board(goBlock);
        location = new Location(board, players);

        centralBlock = new PropertyBlock("Central", blockObservers, effectObservers,
                new Property("Central", 800, 90));
        wanChaiBlock = new PropertyBlock("Wan Chai", blockObservers, effectObservers,
                new Property("Wan Chai", 700, 65));
        justVisitingBlock = new NoEffectBlock("Just Visiting", blockObservers, effectObservers);
        inJailRoundCounter = new HashMap<>();
        inJailRoundCounter.put(playerA,0);
        inJailBlock = new InJailBlock("In Jail", blockObservers, effectObservers, location,inJailRoundCounter);
        justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(blockObservers, effectObservers,
                justVisitingBlock, inJailBlock
        );
        goToJailBlock = new GoToJailBlock("Go To Jail",blockObservers, effectObservers, location,justVisitingOrInJailBlock);



        board.addBlock(goBlock);
        board.addBlock(centralBlock);
        board.addBlock(wanChaiBlock);
        board.addBlock(justVisitingOrInJailBlock);
        board.addBlock(goToJailBlock);

        board.addPath(goBlock,goToJailBlock);
        board.addPath(goToJailBlock,centralBlock);
        board.addPath(centralBlock,wanChaiBlock);
        board.addPath(wanChaiBlock,justVisitingOrInJailBlock);
        board.addPath(justVisitingOrInJailBlock,goBlock);

        location.setStartLocation(goBlock);

    }
    @Test
    public void MoveStepTest() {
        location.moveStep(playerA,3);
        assert (playerA.getCurrentLocation(location).equals(wanChaiBlock));
    }
    @Test
    public void MoveToBlockTest() {
        location.moveTo(playerA, justVisitingOrInJailBlock,true);
        assert (playerA.getCurrentLocation(location).equals(justVisitingOrInJailBlock));
    }


    @Test
    public void GoToJail() {
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.YES);
        location.moveStep(playerA, 1);
        Block block = playerA.getCurrentLocation(location);
        assert (block.equals(justVisitingOrInJailBlock));

    }

    @Test
    public void SetInJail() {
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.YES);
        location.moveStep(playerA, 1);
        assert (playerA.getStatus().equals(Player.Status.GROUNDED));

    }

    @Test
    public void InJail() {
        new GameDisplay(System.out);
        MoveEffect effect;
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.NO);
        location.moveStep(playerA, 1);
        assert (inJailRoundCounter.get(playerA) == 0);

        effect = new MoveEffect("",effectObservers,playerA,playerA.roll(2), location);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 1);

        effect = new MoveEffect("",effectObservers,playerA, playerA.roll(2), location);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 2);

        effect = new MoveEffect("",effectObservers,playerA, playerA.roll(2), location);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 0);
    }

    @Test
    public void Test() throws IOException {
        byte[] data = "123,456,789,123,456,789\n".getBytes();

        InputStream input = new ByteArrayInputStream(data);

        FileOutputStream fileOutputStream = new FileOutputStream("tmp/output.txt");
        CompMonopolyApplication compMonopolyApplication = Main.createGameApplication(input,fileOutputStream);
        compMonopolyApplication.run();
        GameDisplay.flush();
    }


}
