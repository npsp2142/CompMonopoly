package com.company.model.component.block;

import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.effect.OnLandEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit test of GoToJailBlock
 */
class GoToJailBlockTest {
    PlayerLocation playerLocation;
    Player player;
    GoToJailBlock goToJailBlock;
    JustVisitingOrInJailBlock justVisitingOrInJailBlock;

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        Board board = new Board();
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        GoBlock goBlock = new GoBlock("Go");
        playerLocation = new PlayerLocation(board, players, goBlock);
        playerLocation.setStartLocation();
        JailBlock jailBlock = new JailBlock("In Jail", playerLocation, new HashMap<>());
        NoEffectBlock justVisitingBlock = new NoEffectBlock("Just Visiting");
        justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(justVisitingBlock, jailBlock);
        goToJailBlock = new GoToJailBlock("Go Jail", playerLocation, justVisitingOrInJailBlock);
        board.getBlocks().add(justVisitingOrInJailBlock);
    }

    /**
     * Unit test of GoToJailBlock.createOnLandEffect()
     */
    @Test
    void createOnLandEffect() {
        OnLandEffect onLandEffect = goToJailBlock.createOnLandEffect(player);
        onLandEffect.triggerOnLand();
        assertEquals(justVisitingOrInJailBlock, playerLocation.getCurrentLocation(player));
        assertEquals(Player.Status.GROUNDED, player.getStatus());
    }
}