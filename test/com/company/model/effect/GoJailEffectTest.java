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

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoJailEffectTest {

    private Board board;
    private PlayerLocation playerLocation;
    private ArrayList<Player> players;
    private GoBlock goBlock;
    private PropertyBlock centralBlock;
    private PropertyBlock wanChaiBlock;
    private NoEffectBlock justVisitingBlock;
    private JailBlock jailBlock;
    private GoToJailBlock goToJailBlock;
    private JustVisitingOrInJailBlock justVisitingOrInJailBlock;
    private Player playerA;
    private HashMap<Player, Integer> inJailRoundCounter;

    @BeforeEach
    void setUp() {
        new GameDisplay(System.out);
        Random random = new Random(3);
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
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
        jailBlock = new JailBlock("In Jail", playerLocation, inJailRoundCounter);
        justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(
                JustVisitingOrInJailBlock.DEFAULT_NAME, justVisitingBlock, jailBlock
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
    void onLand() {
        // Test that the effect should move the player to jail, and make the player no move.
        playerLocation.moveTo(playerA, centralBlock);
        SetGroundedEffect setGroundedEffect = new SetGroundedEffect("No move", playerA);
        TeleportEffect teleportEffect = new TeleportEffect("Go Jail",
                playerA, playerLocation, justVisitingOrInJailBlock, false);
        GoJailEffect goJailEffect = new GoJailEffect("Move to jail", setGroundedEffect, teleportEffect);
        goJailEffect.triggerOnLand();
        assertEquals(justVisitingOrInJailBlock, playerA.getCurrentLocation(playerLocation));
    }
}