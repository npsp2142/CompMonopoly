package com.company.model.component;

import java.util.Random;

public class Dice {
    private final Random generator;
    private final int maxValue;

    public Dice(Random generator ,int maxValue){
        this.generator = generator;
        this.maxValue = maxValue;
    }
    public int roll(){
        return generator.nextInt(maxValue) + 1;
    }

}
