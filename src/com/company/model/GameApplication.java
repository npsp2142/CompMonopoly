package com.company.model;

import com.company.model.command.Command;
import com.company.model.command.CommandFactory;

import java.util.ArrayList;

public class GameApplication {
    public static GameApplication instance;
    private final CommandFactory commandFactory;
    private boolean isExitApp;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        MENU, PLAYING,
    }

    public GameApplication(
                           CommandFactory commandFactory,
                           Status status) {
        instance = this;
        this.commandFactory = commandFactory;
        this.status = status;
    }


    public void run() {
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
    }

    private void beforeRun() {
        isExitApp = false;
        System.out.println("Welcome to COMP Monopoly. Type \"start\" to start the game");
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

    public void quitGame(){
        status = Status.MENU;
    }


}
