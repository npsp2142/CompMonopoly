package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.MoveEffect;
import com.company.model.effect.TeleportEffect;

import java.util.ArrayList;

public class CommandFactory {

    private final GameSystem gameSystem;

    public CommandFactory(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public Command make(ArrayList<String> tokens) {
        if (CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.MENU) {
            switch (tokens.get(0).toLowerCase()) {
                case "start":
                    return new StartCommand(CompMonopolyApplication.instance, gameSystem);
                case "load":
                    return new LoadCommand(gameSystem);
                case "quit":
                    return new QuitCommand(CompMonopolyApplication.instance);
            }
            return null;
        }

        switch (tokens.get(0).toLowerCase()) {
            case "start":
                return new StartCommand(CompMonopolyApplication.instance, gameSystem);
            case "save":
                return new SaveCommand(gameSystem);
            case "load":
                return new LoadCommand(gameSystem);
            case "quit":
                return new QuitCommand(CompMonopolyApplication.instance);
            case "location":
            case "loc":
                return new ViewLocationCommand(gameSystem.getLocation(), gameSystem.getPlayers());
            case "roll":
            case "r":
                return new RollCommand(
                        new MoveEffect("Roll To Move", gameSystem.getEffectObservers(),
                                gameSystem.getCurrentPlayer(),
                                gameSystem.getCurrentPlayer().roll(2),
                                gameSystem.getLocation()), gameSystem);
            case "y":
                return new GiveYesRespondCommand(gameSystem.getCurrentPlayer());
            case "n":
                return new GiveNoResponseCommand(gameSystem.getCurrentPlayer());
            case "help":
                return new HelpCommand();
            case "info":
                if (tokens.size() != 2) {
                    GameDisplay.usageMessage("info [-location/-property]");
                    return new EmptyCommand();
                }
                switch (tokens.get(1).toLowerCase()) {
                    case "-location":
                    case "-l":
                        return new ViewLocationCommand(gameSystem.getLocation(), gameSystem.getPlayers());
                    case "-property":
                    case "-p":
                        return new ViewPropertyCommand(gameSystem.getCurrentPlayer(), gameSystem.getBoard(),
                                gameSystem.getLocation(), ViewPropertyCommand.Mode.ALL);
                }
                break;
            case "property":
            case "p":
                return new ViewPropertyCommand(gameSystem.getCurrentPlayer(), gameSystem.getBoard(),
                        gameSystem.getLocation(), ViewPropertyCommand.Mode.ALL);
            case "money":
            case "m":
                return new ViewAmountCommand(gameSystem.getPlayers());
            case "board":
            case "b":
                return new ViewBoardCommand(gameSystem.getBoard(), gameSystem.getLocation());
            case "cheat":
                switch (tokens.get(1).toLowerCase()) {
                    case "-lm":
                        return new ReduceMoneyCommand(
                                new LoseMoneyEffect("Cheat",
                                        gameSystem.getEffectObservers(), gameSystem.getCurrentPlayer(),
                                        Integer.parseInt(tokens.get(2))));
                    case "-t":
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String token : tokens.subList(2, tokens.size())) {
                            stringBuilder.append(token).append(" ");
                        }
                        stringBuilder.setLength(stringBuilder.length() - 1);
                        return new TeleportCommand(
                                new TeleportEffect(
                                        "Cheat",
                                        gameSystem.getEffectObservers(),
                                        gameSystem.getCurrentPlayer(),
                                        gameSystem.getLocation(),
                                        gameSystem.getBoard().findBlock(stringBuilder.toString()),
                                        true
                                )
                        );
                }

        }
        return null;
    }
}
