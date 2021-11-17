package com.company.model.effect;

import com.company.model.GameDisplay;
import com.company.model.PlayerFactory;
import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class TeleportEffectTest {

    Board board;
    PlayerLocation playerLocation;
    ArrayList<Player> players;
    PropertyBlock centralBlock;
    PropertyBlock wanChaiBlock;
    NoEffectBlock justVisitingBlock;
    JailBlock jailBlock;
    GoToJailBlock goToJailBlock;
    JustVisitingOrInJailBlock justVisitingOrInJailBlock;
    Player playerA;
    HashMap<Player, Integer> jailRoundCounter;
    NoEffectBlock noEffectBlock;

    @BeforeEach
    void setUp() {
        new GameDisplay(System.out);
        Random random = new Random(3);
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
        players = playerFactory.make(names);
        playerA = players.get(0);
        playerA.setAmount(Player.DEFAULT_AMOUNT);
        noEffectBlock = new NoEffectBlock("No Effect");
        Block startBlock = noEffectBlock;
        board = new Board();
        playerLocation = new PlayerLocation(board, players, startBlock);

        centralBlock = new PropertyBlock("Central",
                new Property("Central", 800, 90));
        wanChaiBlock = new PropertyBlock("Wan Chai",
                new Property("Wan Chai", 700, 65));
        justVisitingBlock = new NoEffectBlock("Just Visiting");
        jailRoundCounter = new HashMap<>();
        jailRoundCounter.put(playerA, 0);
        jailBlock = new JailBlock("In Jail", playerLocation, jailRoundCounter);
        justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(
                JustVisitingOrInJailBlock.DEFAULT_NAME, justVisitingBlock, jailBlock
        );
        goToJailBlock = new GoToJailBlock("Go To Jail", playerLocation, justVisitingOrInJailBlock);

        board.addBlock(noEffectBlock);
        board.addBlock(centralBlock);
        board.addBlock(wanChaiBlock);
        board.addBlock(justVisitingOrInJailBlock);
        board.addBlock(goToJailBlock);

        board.addPath(noEffectBlock, goToJailBlock);
        board.addPath(goToJailBlock, centralBlock);
        board.addPath(centralBlock, wanChaiBlock);
        board.addPath(wanChaiBlock, justVisitingOrInJailBlock);
        board.addPath(justVisitingOrInJailBlock, noEffectBlock);

        playerLocation.setStartLocation();
    }

    @Test
    void onLand() {
        playerLocation.moveTo(playerA, centralBlock);
        TeleportEffect teleportEffect = new TeleportEffect("teleport", playerA, playerLocation, wanChaiBlock);
        teleportEffect.triggerOnLand();
    }
}