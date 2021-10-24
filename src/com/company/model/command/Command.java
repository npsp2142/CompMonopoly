package com.company.model.command;

public interface Command {
    int COMMAND_LENGTH_MAX = 20;

    void execute();

    boolean isValid();
}
