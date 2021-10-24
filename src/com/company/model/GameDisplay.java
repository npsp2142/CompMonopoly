package com.company.model;

import com.company.model.command.Command;

import java.io.PrintStream;

public class GameDisplay {
    private final PrintStream printStream;
    private static GameDisplay instance;

    private static final int TERMINAL_WIDTH = 80;
    private static final char DIVIDER_SYMBOL = '-';
    private static final char SPACE_SYMBOL = ' ';

    public GameDisplay(PrintStream printStream) {
        this.printStream = printStream;
        if(instance == null){
            instance = this;
        }
    }

    public static void infoMessage(String message) {
        instance.getPrintStream().println("[Info] " + message);
    }

    public static void usageMessage(String message) {
        instance.getPrintStream().println("[Usage] " + message);
    }

    public static void promptMessage(String message) {
        instance.getPrintStream().print("[Prompt] " + message + " [y/n] ");
    }

    public static void warnMessage(String message) {
        instance.getPrintStream().println(ANSI.ANSI_RED + message + ANSI.ANSI_RESET);
    }

    public static void commandHelpMessage(String command, String description) {
        StringBuilder builder = new StringBuilder();
        int space_width = (Command.COMMAND_LENGTH_MAX - command.length());
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
        builder.append('\n');
        instance.getPrintStream().print(builder);
    }

    public static void titleBar(String title) {
        int star_width = (TERMINAL_WIDTH - title.length() - 2) / 2;
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
        instance.getPrintStream().println(builder);
    }

    public static void printCommandPrompt() {
        switch (GameApplication.instance.getStatus()) {
            case PLAYING:
                instance.getPrintStream().print(ANSI.ANSI_GREEN + GameSystem.instance.getCurrentPlayer() + ANSI.ANSI_RESET);
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
