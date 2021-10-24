package com.company.model.command;

import java.util.ArrayList;

public interface Command {
     int COMMAND_LENGTH_MAX = 20;
     void execute();
     boolean isValid();
}
