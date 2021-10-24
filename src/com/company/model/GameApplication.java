package com.company.model;

import com.company.model.command.Command;
import com.company.model.command.CommandFactory;

import java.io.IOException;
import java.util.ArrayList;

public class GameApplication {
    public static GameApplication instance;
    private final CommandFactory commandFactory;
    private boolean isExitApp;
    private Status status;

    public GameApplication(
            CommandFactory commandFactory,
            Status status) {
        instance = this;
        this.commandFactory = commandFactory;
        this.status = status;
    }

    public void run() {
        try {
            beforeRun();
            while (!isExitApp) {
                Command command = getCommand();
                if (command == null) {
                    GameDisplay.warnMessage("Unknown Command");
                    continue;
                }
                if (!command.isValid()) {
                    GameDisplay.warnMessage("Invalid Command");
                    continue;
                }
                command.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void beforeRun() {
        isExitApp = false;
        GameDisplay.titleBar("Welcome to COMP Monopoly. Type \"start\" to start the game");
    }

    public Command getCommand() {
        ArrayList<String> tokens = GameController.instance.getArguments();
        return commandFactory.make(tokens);
    }

    public void closeApplication() {
        isExitApp = true;
    }

    public GameApplication.Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void quitGame() {
        status = Status.MENU;
    }

    public enum Status {
        MENU, PLAYING,
    }


}
