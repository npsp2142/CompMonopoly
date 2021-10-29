package com.company.model.component.block;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.effect.OnLandEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalBlockTest {

    ConditionalBlock conditionalBlock;
    Player playerA;
    Property central;
    Property wanChai;
    private static class TwoPropertyConditionalBlock extends ConditionalBlock{

        private TwoPropertyConditionalBlock(PropertyBlock central, PropertyBlock wanChai) {
            super("Two Property", central,wanChai);
        }

        @Override
        public boolean GoTo(Player player) {
            return player.getStatus() == Player.Status.HEALTHY;
        }
    }

    @BeforeEach
    void setUp() {
        playerA = new Player("Player A");
        Player playerB = new Player("Player B");
        central = new Property("Central", 800, 90);
        wanChai = new Property("Wan Chai", 700, 65);
        central.setOwner(playerB);
        wanChai.setOwner(playerB);
        PropertyBlock centralBlock = new PropertyBlock("Central",central);
        PropertyBlock wanChaiBlock = new PropertyBlock("Central",wanChai);
        conditionalBlock = new TwoPropertyConditionalBlock(centralBlock, wanChaiBlock);
    }

    /**
     * Unit test of ConditionalBlock.goto() when true
     */
    @Test
    void goToTestTrue() {
        playerA.setStatus(Player.Status.HEALTHY);
        OnLandEffect onLandEffect = conditionalBlock.createOnLandEffect(playerA);
        onLandEffect.onLand();
        assertEquals( central.getRent(),playerA.getAmount());
    }

    /**
     * Unit test of ConditionalBlock.goto() when false
     */
    @Test
    void goToTestFalse() {
        playerA.setStatus(Player.Status.GROUNDED);
        OnLandEffect onLandEffect = conditionalBlock.createOnLandEffect(playerA);
        onLandEffect.onLand();
        assertEquals(wanChai.getRent(),playerA.getAmount());
    }

}