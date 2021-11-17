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

class MoveEffectTest {
    Board board;
    PlayerLocation playerLocation;
    GoBlock goBlock;
    PropertyBlock centralBlock;
    PropertyBlock wanChaiBlock;
    NoEffectBlock justVisitingBlock;
    JailBlock jailBlock;
    GoToJailBlock goToJailBlock;
    JustVisitingOrInJailBlock justVisitingOrInJailBlock;
    Player playerA;
    HashMap<Player, Integer> inJailRoundCounter;
    Property central;

    @BeforeEach
    void setUp() {
        new GameDisplay(System.out);
        Random random = new Random(3);
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        PlayerFactory playerFactory = new PlayerFactory(random, Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
        ArrayList<Player> players = playerFactory.make(names);
        playerA = players.get(0);
        playerA.setAmount(Player.DEFAULT_AMOUNT);

        goBlock = new GoBlock("Go");

        Block startBlock = goBlock;
        board = new Board();
        playerLocation = new PlayerLocation(board, players, startBlock);
        central = new Property("Central", 800, 90);
        centralBlock = new PropertyBlock("Central", central);
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
        int[] steps;
        MoveEffect moveEffect;
        // Test that the effect should move the player, and
        // trigger the effects when the player lands on some block.
        playerLocation.moveTo(playerA, goBlock);
        steps = new int[]{2}; // move 2 step
        moveEffect = new MoveEffect("Move steps", playerA, steps, playerLocation);
        playerA.setResponse(Player.Response.YES); // player choose to buy property.
        moveEffect.triggerOnLand();
        assertEquals(centralBlock, playerA.getCurrentLocation(playerLocation));
        assertEquals(playerA, central.getOwner());
        assertEquals(Player.DEFAULT_AMOUNT - central.getPrice(), playerA.getAmount());

        // Test that the effect should move the player, and
        // trigger the effects when the player passes some block.
        playerLocation.moveTo(playerA, justVisitingOrInJailBlock);
        steps = new int[]{3}; // move 2 step
        moveEffect = new MoveEffect("Move steps", playerA, steps, playerLocation);
        moveEffect.triggerOnLand();
        assertEquals(centralBlock, playerA.getCurrentLocation(playerLocation));
        assertEquals(Player.DEFAULT_AMOUNT - central.getPrice() + GoBlock.SALARY, playerA.getAmount());
    }
}