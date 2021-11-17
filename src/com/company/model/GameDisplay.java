package com.company.model;

import java.io.OutputStream;
import java.io.PrintStream;

public class GameDisplay {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final int TERMINAL_WIDTH = 80;
    private static final char DIVIDER = '=';
    private static final char SPACE = ' ';
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

    public static void infoMessage(String message) {
        instance.getPrintStream().println("[Info] " + message);
    }

    public static void print(String message) {
        instance.getPrintStream().print(message);
    }

    public static void promptMessage(String message) {
        instance.getPrintStream().print("[prompt] " + message + " [y/n] ");
    }

    public static void warnMessage(String message) {
        instance.getPrintStream().println(ANSI_RED + message + ANSI_RESET);
    }

    public static void HelpMessage(String command, String description) {
        StringBuilder builder = new StringBuilder();
        int space_width = (COMMAND_LENGTH_MAX - command.length());
        builder.append(command);
        for (int i = 0; i < space_width; i++) {
            builder.append(SPACE);
        }
        builder.append(description);
        instance.getPrintStream().println(builder);
    }

    public static void divider() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TERMINAL_WIDTH; i++) {
            builder.append(DIVIDER);
        }
        instance.getPrintStream().println(builder);
    }

    /**
     * ---------------------------------------------------------
     *
     * @param title words is this title Bar
     */
    public static void titleBar(String title) {
        titleBar(title, DIVIDER);
    }

    public static void titleBar(String title, char dividerChar) {
        int star_width = (TERMINAL_WIDTH - title.length()) / 2;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < star_width; i++) {
            builder.append(dividerChar);
        }
        builder.append(SPACE);
        builder.append(title);
        builder.append(SPACE);
        for (int i = 0; i < star_width; i++) {
            builder.append(dividerChar);
        }
        builder.setLength(TERMINAL_WIDTH);
        instance.getPrintStream().println(builder);
    }

    public static void printCommandPrompt() {
        switch (CompMonopolyApplication.instance.getStatus()) {
            case PLAYING:
                if (instance.gameSystem == null) return;
                instance.getPrintStream().print(colorString(instance.gameSystem.getCurrentPlayer().toString(), ANSI_GREEN));
                instance.getPrintStream().print(colorString(" > ", ANSI_BLUE));
                break;
            case MENU:
                instance.getPrintStream().print(colorString(" > ", ANSI_BLUE));
                break;
        }
    }

    public static String colorString(String message, String ansiColor) {
        return ansiColor + message + ANSI_RESET;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

}
