package com.company.model.command;

import com.company.model.CompMonopolyApplication;

/**
 * When user wants to quit the application, this will quit the application
 */
public class QuitCommand implements Command {
    private final CompMonopolyApplication compMonopolyApplication;

    /**
     * @param compMonopolyApplication this application
     */
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
