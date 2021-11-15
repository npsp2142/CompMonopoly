package com.company.model;

import java.io.OutputStream;
import java.io.PrintStream;

public class GameDisplay {
    private static final int TERMINAL_WIDTH = 80;
    private static final char DIVIDER_SYMBOL = '-';
    private static final char SPACE_SYMBOL = ' ';
    private static final int COMMAND_LENGTH_MAX = 20;
    private static GameDisplay instance;
    private final PrintStream printStream;
    private GameSystem gameSystem;

    public GameDisplay(OutputStream outputStream) {
        printStream = new PrintStream(outputStream);
        if (instance == null) {
            instance = this;
        }
    }

    public GameDisplay(OutputStream outputStream, GameSystem gameSystem) {
        printStream = new PrintStream(outputStream);
        this.gameSystem = gameSystem;
        if (instance == null) {
            instance = this;
        }
    }

    public static void flush() {
        instance.getPrintStream().flush();
    }

    public static void infoMessage(String message) {
        instance.getPrintStream().println("[Info] " + message);
    }
    public static void print(String message) {
        instance.getPrintStream().print(message);
    }


    public static void promptMessage(String message) {
        instance.getPrintStream().print(message);
    }

    public static void warnMessage(String message) {
        instance.getPrintStream().println(ANSI.ANSI_RED + message + ANSI.ANSI_RESET);
    }

    public static void commandHelpMessage(String command, String description) {
        StringBuilder builder = new StringBuilder();
        int space_width = (COMMAND_LENGTH_MAX - command.length());
        builder.append(command);
        for (int i = 0; i < space_width; i++) {
            builder.append(SPACE_SYMBOL);
        }
        builder.append(description);
        instance.getPrintStream().println(builder);
    }

    public static void divider() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TERMINAL_WIDTH; i++) {
            builder.append(DIVIDER_SYMBOL);
        }
        instance.getPrintStream().println(builder);
    }

    /**
     * ---------------------------------------------------------
     *
     * @param title words is this title Bar
     */
    public static void titleBar(String title) {
        int star_width = (TERMINAL_WIDTH - title.length()) / 2;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < star_width; i++) {
            builder.append(DIVIDER_SYMBOL);
        }
        builder.append(SPACE_SYMBOL);
        builder.append(title);
        builder.append(SPACE_SYMBOL);
        for (int i = 0; i < star_width; i++) {
            builder.append(DIVIDER_SYMBOL);
        }
        builder.setLength(TERMINAL_WIDTH);
        instance.getPrintStream().println(builder);
    }

    public static void printCommandPrompt() {
        switch (CompMonopolyApplication.instance.getStatus()) {
            case PLAYING:
                if (instance.gameSystem == null) return;
                instance.getPrintStream().print(ANSI.ANSI_GREEN + instance.gameSystem.getCurrentPlayer() + ANSI.ANSI_RESET);
                instance.getPrintStream().print(ANSI.ANSI_BLUE + " > " + ANSI.ANSI_RESET);
                break;
            case MENU:
                instance.getPrintStream().print(ANSI.ANSI_BLUE + " > " + ANSI.ANSI_RESET);
                break;
        }
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

}
