package com.company.model.component;

import java.util.Random;

public class Dice {
    private Random generator;
    private int maxValue;

    public Dice(Random generator ,int maxValue){
        this.generator = generator;
        this.maxValue = maxValue;
    }
    public int roll(){
        return generator.nextInt(maxValue) + 1;
    }
    public int getRandomInt(int max){
        return generator.nextInt(max) / 10 * 10;
    }
}
