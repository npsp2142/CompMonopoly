package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnEnterEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoBlockTest {

    GoBlock goBlock;
    Player playerA;

    @BeforeEach
    void setUp() {
        goBlock = new GoBlock("GO");
        playerA = new Player("Player A");
    }

    /**
     * Unit test of GoBlockTest.createOnEnterEffect()
     */
    @Test
    void createOnEnterEffect() {
        OnEnterEffect onEnterEffect = goBlock.createOnEnterEffect(playerA);
        onEnterEffect.onEnter();
        assertEquals(GoBlock.SALARY, playerA.getAmount());
    }

}