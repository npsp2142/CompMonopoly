package com.company.model;

import com.company.model.block.*;
import com.company.model.command.Command;
import com.company.model.command.CommandFactory;
import com.company.model.observer.BlockObserver;
import com.company.model.observer.LocationBlockObserver;
import com.company.model.observer.MoneyObserver;
import com.company.model.observer.PlayerObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class GameApplication {
    public static GameApplication instance;
    private GameSystem gameSystem;
    private CommandFactory commandFactory;
    private GameController gameController;
    private Command command;
    private boolean isExitApp;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        MENU, PLAYING,
    }

    public GameApplication(GameSystem gameSystem,
                           CommandFactory commandFactory,
                           GameController gameController,
                           Status status) {
        instance = this;
        this.gameSystem = gameSystem;
        this.commandFactory = commandFactory;
        this.gameController = gameController;
        this.status = status;
    }


    public void run() {
        beforeRun();
        while (!isExitApp) {
            command = getCommand();
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

    public void setCommand(Command command) {
        this.command = command;
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
