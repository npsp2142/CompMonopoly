package com.company.model.observer;

import com.company.model.effect.Effect;

import java.io.OutputStream;
import java.io.PrintStream;

public class EffectDisplay implements EffectObserver {
    private static EffectDisplay instance;
    private final PrintStream printStream;

    public EffectDisplay(OutputStream outputStream) {
        printStream = new PrintStream(outputStream);
        if (instance == null) {
            instance = this;
        }
    }

    /**
     * Print effect description.
     * @param effect the effect to print something
     */
    @Override
    public void update(Effect effect) {
        instance.getPrintStream().println("[Info] " + effect.getDescription());
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
