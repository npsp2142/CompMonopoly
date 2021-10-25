package com.company.model.command;

import com.company.model.CompMonopolyApplication;

public class QuitCommand implements Command {
    private final CompMonopolyApplication compMonopolyApplication;

    public QuitCommand(CompMonopolyApplication compMonopolyApplication) {
        this.compMonopolyApplication = compMonopolyApplication;
    }

    @Override
    public void execute() {
        System.out.println("Goodbye! See you next time!");
        compMonopolyApplication.closeApplication();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
