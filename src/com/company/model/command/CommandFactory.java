package com.company.model.command;

import com.company.model.*;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.MoveEffect;

import java.util.ArrayList;

public class CommandFactory {

    private final GameSystem gameSystem;
    public CommandFactory(GameSystem gameSystem){
        this.gameSystem = gameSystem;
    }

    public Command make(ArrayList<String> tokens){
        switch (tokens.get(0).toLowerCase()){
            case "start":
                return new StartCommand(GameApplication.instance);
            case "quit":
                return new QuitCommand(GameApplication.instance);
            case "location":
            case "loc":
                return new ViewLocationCommand(gameSystem.getPlayerLocation(), gameSystem.getPlayers());
            case "roll":
            case "r":
                return new RollCommand(
                        gameSystem.getCurrentPlayer(),
                        gameSystem.getPlayerLocation(),
                        new MoveEffect("Roll",
                                gameSystem.getCurrentPlayer(),
                                gameSystem.getCurrentPlayer().roll(2),
                                gameSystem.getPlayerLocation()));
            case "y":
                return  new GiveYesRespondCommand(gameSystem.getCurrentPlayer());
            case "n":
                return  new GiveNoResponseCommand(gameSystem.getCurrentPlayer());
            case "help":
                return new HelpCommand();
            case "info":
                if (tokens.size() != 2){
                    GameDisplay.usageMessage("info [-location/-property]");
                    return new EmptyCommand();
                }
                switch (tokens.get(1).toLowerCase()){
                    case "-location":
                    case "-l":
                        return new ViewLocationCommand(gameSystem.getPlayerLocation(), gameSystem.getPlayers());
                    case "-property":
                    case "-p":
                        return new ViewPropertyCommand(gameSystem.getCurrentPlayer(), gameSystem.getBoard(),
                                ViewPropertyCommand.Mode.ALL);
                }
                break;
            case "property":
            case "p":
                return new ViewPropertyCommand(gameSystem.getCurrentPlayer(), gameSystem.getBoard(),
                        ViewPropertyCommand.Mode.ALL);
            case "money":
            case "m":
                return new ViewAmountCommand(gameSystem.getPlayers());
            case "board":
            case "b":
                return new ViewBoardCommand(gameSystem.getBoard());
            case "save":
                return new SaveCommand(gameSystem);
            case "load":
                return new LoadCommand(gameSystem);
            case "cheat":
                if ("-lm".equalsIgnoreCase(tokens.get(1))) {
                    return new ReduceMoneyCommand(new LoseMoneyEffect("Cheat", gameSystem.getCurrentPlayer(),
                            Integer.parseInt(tokens.get(2))));
                }
        }
        return null;
    }
}
