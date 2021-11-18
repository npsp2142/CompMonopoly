package com.company.model.command;

/**
 * This command should change the game system
 * @see com.company.model.GameSystem
 */
public interface Command {
    /**
     * execute the command
     */
    void execute();

    /**
     * @return if the command is valid
     */
    boolean isValid();
}
