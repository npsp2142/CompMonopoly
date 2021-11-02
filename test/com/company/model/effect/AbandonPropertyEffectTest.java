package com.company.model.effect;

import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.observer.EffectObserver;
import com.sun.xml.internal.bind.v2.TODO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit Test case for AbandonPropertyEffect
 */
class AbandonPropertyEffectTest {

    Player player;
    Property wanChai;
    //Map<String, EffectObserver> effectObservers;

    ArrayList<AbandonPropertyEffect> abandonPropertyEffects = new ArrayList<>();

    @BeforeEach
    void setUp() {
        player = new Player("Player");
        wanChai = new Property("Wan Chai", 700, 65);
        wanChai.setOwner(player);
    }

    /**
     * Unit Test case for AbandonPropertyEffect.onLand()
     */
    @Test
    void onLand() {
        assertEquals(wanChai.getOwner(), player); // Test the owner of the property
        //TODO: 2/11/2021 I dont know how to do abandon by Chloe
        AbandonPropertyEffect abandonPropertyEffect = new AbandonPropertyEffect("Bankrupt", wanChai);
       // abandonPropertyEffect.setEffectObservers(getEffectObservers());
        abandonPropertyEffects.add(abandonPropertyEffect);
        assertNull(wanChai.getOwner()); // Test the owner of the property
    }
    //public Map<String, EffectObserver> getEffectObservers() {
        //return effectObservers;
   // }
}