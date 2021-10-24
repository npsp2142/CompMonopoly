package com.company.model.command;

public class EmptyCommand implements Command{
    @Override
    public void execute() {

    }

    @Override
    public boolean isValid() {
        return true;
    }
}
