package com.company.model;

import com.company.model.command.Command;
import com.company.model.command.CommandFactory;

import java.util.ArrayList;

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

// --Commented out by Inspection START (26/10/2021 17:53):
//    public Command getCommand(String string) {
//        ArrayList<String> tokens = GameController.instance.getArguments(string);
//        return commandFactory.make(tokens);
//    }
// --Commented out by Inspection STOP (26/10/2021 17:53)

    public void closeApplication() {
        isExitApp = true;
    }

    public CompMonopolyApplication.Status getStatus() {
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
