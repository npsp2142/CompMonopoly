package com.company.model.command;

public interface Command {
    void execute();
    boolean isValid();
}
