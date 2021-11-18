package com.company.model;

import com.company.model.command.Command;
import com.company.model.command.CommandFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompMonopolyApplication {
    public static CompMonopolyApplication instance;
    private final CommandFactory commandFactory;
    private boolean isExitApp;
    private Status status;

    public CompMonopolyApplication(
            CommandFactory commandFactory) {
        instance = this;
        this.commandFactory = commandFactory;
        this.status = Status.MENU;
    }

    public void run() {
        beforeRun();
        while (!isExitApp) {
            try {
                Command command = makeCommand();
                if (command == null) {
                    GameDisplay.warnMessage("Unknown Command");
                    continue;
                }
                if (!command.isValid()) {
                    GameDisplay.warnMessage("Invalid Command");
                    continue;
                }
                command.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    private void beforeRun() {
        isExitApp = false;
        GameDisplay.titleBar("Welcome to COMP Monopoly. Type \"start\" to start the game");
    }

    public Command makeCommand() {
        ArrayList<String> tokens = GameController.instance.getArguments();
        return commandFactory.make(tokens);
    }

    public Command makeCommand(String commandInput) {
        List<String> tokens = Arrays.asList(commandInput.split(" "));
        return commandFactory.make(tokens);
    }

    public void closeApplication() {
        isExitApp = true;
    }

    public CompMonopolyApplication.Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void quitGame() {
        status = Status.MENU;
    }

    public enum Status {
        MENU, PLAYING,
    }
}
