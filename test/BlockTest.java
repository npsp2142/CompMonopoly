import com.company.model.GameDisplay;
import com.company.model.block.*;
import com.company.model.component.*;
import com.company.model.effect.MoveEffect;
import com.company.model.observer.BlockObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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

    @BeforeEach
    void setUp() {

        new GameDisplay(System.out);

        blockObservers = new ArrayList<>();

        players = new ArrayList<>();

        playerA = new Player(
                "Player A",
                Player.Status.HEALTHY,
                Player.DEFAULT_AMOUNT,
                new Dice(new Random(3),4),
                new ArrayList<>()
        );
        players.add(playerA);

        goBlock = new GoBlock("Go", blockObservers);

        board = new Board(goBlock);
        location = new Location(board, players);

        centralBlock = new PropertyBlock("Central", blockObservers,
                new Property("Central", 800, 90));
        wanChaiBlock = new PropertyBlock("Wan Chai", blockObservers,
                new Property("Wan Chai", 700, 65));
        justVisitingBlock = new NoEffectBlock("Just Visiting", blockObservers);
        inJailRoundCounter = new HashMap<>();
        inJailRoundCounter.put(playerA,0);
        inJailBlock = new InJailBlock("In Jail", blockObservers, location,inJailRoundCounter);
        justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(blockObservers,
                justVisitingBlock, inJailBlock
        );
        goToJailBlock = new GoToJailBlock("Go To Jail",blockObservers, location,justVisitingOrInJailBlock);



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
        assert (playerA.getStatus().equals(Player.Status.IN_JAIL));

    }

    @Test
    public void InJailRoundOne() {
        MoveEffect effect;
        Player.NEED_PROMPT = false;
        playerA.setResponse(Player.Response.NO);
        location.moveStep(playerA, 1);
        assert (inJailRoundCounter.get(playerA) == 0);
        effect = new MoveEffect("",playerA, playerA.roll(2), location);
        effect.onLand();
        System.out.println(effect.getDescription());
        assert (inJailRoundCounter.get(playerA) == 1);
        effect = new MoveEffect("",playerA, playerA.roll(2), location);
        effect.onLand();
        System.out.println(effect.getDescription());
        assert (inJailRoundCounter.get(playerA) == 2);
        effect = new MoveEffect("",playerA, playerA.roll(2), location);
        effect.onLand();
        System.out.println(effect.getDescription());
        assert (inJailRoundCounter.get(playerA) == 0);

    }


}
