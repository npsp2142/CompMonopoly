package com.company.model.command;

import com.company.model.GameApplication;

public class QuitCommand implements Command {
    private final GameApplication gameApplication;
    public QuitCommand(GameApplication gameApplication){
        this.gameApplication = gameApplication;
    }
    @Override
    public void execute() {
        System.out.println("Goodbye! See you next time!");
        gameApplication.closeApplication();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
