package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.effect.OnEnterEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests of GoBlock
 */
class GoBlockTest {

    private GoBlock goBlock;
    private Player playerA;

    @BeforeEach
    void setUp() {
        goBlock = new GoBlock("GO");
        playerA = new Player("Player A");
    }

    /**
     * Unit test of GoBlock.createOnEnterEffect()
     */
    @Test
    void createOnEnterEffect() {
        OnEnterEffect onEnterEffect = goBlock.createOnEnterEffect(playerA);
        onEnterEffect.triggerOnEnter();
        assertEquals(GoBlock.SALARY, playerA.getAmount());
    }

}