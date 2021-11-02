package componentTest;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class JailTest {
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
    HashMap<Player, Integer> inJailRoundCounter;

    @BeforeEach
    void setUp() {

        new GameDisplay(System.out);

        players = new ArrayList<>();
        Random random = new Random(4);
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
        players = playerFactory.make(names);

        goBlock = new GoBlock("Go");
        Player playerA = players.get(0);
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
    public void RollToLeaveJailAtFirstRound() {
        new GameDisplay(System.out);
        MoveEffect effect;
        Player.NEED_PROMPT = false;
        Player playerA = players.get(0);
        playerA.setResponse(Player.Response.NO);
        playerLocation.moveStep(playerA, 1);
        assert (inJailRoundCounter.get(playerA) == 0);

        effect = new MoveEffect("", playerA, playerA.roll(2), playerLocation);
        effect.onLand();
        assert (inJailRoundCounter.get(playerA) == 0);
    }


}
