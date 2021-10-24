package com.company.model;

import com.company.model.component.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameController {
    public static GameController instance;
    public final Scanner scanner;

    public GameController(Scanner scanner) {
        this.scanner = scanner;

        if (instance == null) {
            instance = this;
        }
    }

    public Player.Response getResponse(String promptMessage) {

        ArrayList<String> tokens = new ArrayList<>();
        GameDisplay.promptMessage(promptMessage);
        if (scanner.hasNext()) {
            String token = scanner.next();
            tokens.add(token);
        }

        switch (tokens.get(0).toLowerCase()) {
            case "y":
                return Player.Response.YES;
            case "n":
                return Player.Response.NO;
            default:
                GameDisplay.warnMessage("Unknown Response");
                return null;
        }
    }


    public ArrayList<String> getArguments() {
        ArrayList<String> tokens = new ArrayList<>();
        GameDisplay.printCommandPrompt();
        if (scanner.hasNextLine()) {
            String[] token = scanner.nextLine().split("\\s");
            Collections.addAll(tokens, token);
        }
        return tokens;
    }
}
