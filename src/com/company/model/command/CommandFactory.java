package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.MoveEffect;
import com.company.model.effect.TeleportEffect;
import com.company.model.observer.BlockVisitObserver;
import com.company.model.observer.PathObserver;

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
                PathObserver blockVisitObserver =
                        (PathObserver) gameSystem.getPlayerObservers().get(PathObserver.DEFAULT_NAME);
                if (blockVisitObserver == null) {
                    return null;
                }
                return new ViewPathCommand(blockVisitObserver);
            case "roll":
            case "r":
                MoveEffect moveEffect =
                        new MoveEffect("Roll To Move",
                                gameSystem.getCurrentPlayer(),
                                gameSystem.getCurrentPlayer().roll(2),
                                gameSystem.getLocation());
                moveEffect.setEffectObservers(gameSystem.getEffectObservers());
                return new RollCommand(moveEffect, gameSystem);
            case "y":
                return new GiveYesRespondCommand(gameSystem.getCurrentPlayer());
            case "n":
                return new GiveNoResponseCommand(gameSystem.getCurrentPlayer());
            case "help":
                return new HelpCommand();
            case "view":
                if (tokens.size() != 2) {
                    return new EmptyCommand();
                }
                switch (tokens.get(1).toLowerCase()) {
                    case "-vc": // visit count
                        return new ViewBlockVisitCountCommand((BlockVisitObserver)
                                gameSystem.getBlockObservers().get(BlockVisitObserver.DEFAULT_NAME));
                    case "-path":
                        return new ViewPathCommand(
                                (PathObserver) gameSystem.getPlayerObservers().get(PathObserver.DEFAULT_NAME));
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
                        LoseMoneyEffect loseMoneyEffect =
                                new LoseMoneyEffect(
                                        "Cheat",
                                        gameSystem.getCurrentPlayer(),
                                        Integer.parseInt(tokens.get(2))
                                );
                        loseMoneyEffect.setEffectObservers(gameSystem.getEffectObservers());
                        return new ReduceMoneyCommand(loseMoneyEffect);
                    case "-t":
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String token : tokens.subList(2, tokens.size())) {
                            stringBuilder.append(token).append(" ");
                        }
                        stringBuilder.setLength(stringBuilder.length() - 1);
                        TeleportEffect teleportEffect = new TeleportEffect(
                                "Cheat",
                                gameSystem.getCurrentPlayer(),
                                gameSystem.getLocation(),
                                gameSystem.getBoard().findBlock(stringBuilder.toString()),
                                true
                        );
                        teleportEffect.setEffectObservers(gameSystem.getEffectObservers());
                        return new TeleportCommand(teleportEffect);
                }

        }
        return null;
    }
}
