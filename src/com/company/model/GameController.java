package com.company.model;

import com.company.model.component.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class GameController {
    public static GameController instance;
    private final BufferedReader bufferedReader;

    public GameController(InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        if (instance == null) {
            instance = this;
        }
    }

    public Player.Response getResponse(String promptMessage) {
        ArrayList<String> tokens = new ArrayList<>();
        try {
            GameDisplay.promptMessage(promptMessage);
            String token = bufferedReader.readLine();
            tokens.add(token);
            switch (tokens.get(0).toLowerCase()) {
                case "y":
                    return Player.Response.YES;
                case "n":
                    return Player.Response.NO;
                default:
                    GameDisplay.warnMessage("Unknown Response");
                    return null;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> getArguments() {
        ArrayList<String> tokens = new ArrayList<>();
        GameDisplay.printCommandPrompt();

        try {
            String[] token = bufferedReader.readLine().split("\\s");
            Collections.addAll(tokens, token);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return tokens;
    }

    public ArrayList<String> getArguments(String string) {
        ArrayList<String> tokens = new ArrayList<>();
        String[] token = string.split("\\s");
        Collections.addAll(tokens, token);
        return tokens;
    }
}
