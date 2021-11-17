package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameSystem;
import com.company.model.component.block.Block;
import com.company.model.effect.LoseMoneyEffect;
import com.company.model.effect.MoveEffect;
import com.company.model.effect.TeleportEffect;
import com.company.model.observer.BlockVisitObserver;
import com.company.model.observer.PathObserver;

import java.util.List;

/**
 *
 */
public class CommandFactory {

    private final GameSystem gameSystem;

    public CommandFactory(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }


    /**
     * Make Command instances based on a list of tokens. Use the Command instance to control the gameSystem.
     *
     * @param tokens the tokens that specify which command to be made
     * @return a command that control the game system
     */
    public Command make(List<String> tokens) {
        if (CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.MENU) {
            switch (tokens.get(0).toLowerCase()) {
                case "start":
                    if (tokens.size() == 1) {
                        return new StartCommand(
                                CompMonopolyApplication.instance,
                                gameSystem
                        );
                    }
                    if (tokens.size() < 4) return null;
                    int playerNumber = Integer.parseInt(tokens.get(1));
                    return new StartCommand(
                            CompMonopolyApplication.instance,
                            playerNumber,
                            tokens.subList(2, tokens.size()),
                            gameSystem
                    );
                case "load":
                    if (tokens.size() == 2) {
                        LoadCommand loadCommand = new LoadCommand(gameSystem);
                        loadCommand.setFileName(tokens.get(1));
                        return loadCommand;
                    }
                    return new LoadCommand(gameSystem);
                case "quit":
                    return new QuitCommand(CompMonopolyApplication.instance);
            }
            return null;
        }

        switch (tokens.get(0).toLowerCase()) {
            case "save":
                if (tokens.size() == 2) {
                    SaveCommand saveCommand = new SaveCommand(gameSystem);
                    saveCommand.setFileName(tokens.get(1));
                    return saveCommand;
                }
                return new SaveCommand(gameSystem);
            case "quit":
                return new QuitCommand(CompMonopolyApplication.instance);

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
            case "yes":
                return new GiveYesRespondCommand(gameSystem.getCurrentPlayer());
            case "n":
            case "no":
                return new GiveNoResponseCommand(gameSystem.getCurrentPlayer());
            case "help":
                return new HelpCommand();
            case "bvc":
                return new ViewBlockVisitCountCommand((BlockVisitObserver)
                        gameSystem.getBlockObservers().get(BlockVisitObserver.DEFAULT_NAME));
            case "path": // path and now location
                PathObserver blockVisitObserver =
                        (PathObserver) gameSystem.getPlayerObservers().get(PathObserver.DEFAULT_NAME);
                if (blockVisitObserver == null) {
                    return null;
                }
                return new ViewPathCommand(blockVisitObserver);
            case "property":
            case "p":
                return new ViewPropertyCommand(gameSystem.getCurrentPlayer(), gameSystem.getBoard(),
                        gameSystem.getLocation());
            case "money":
            case "m":
                return new ViewAmountCommand(gameSystem.getPlayers());
            case "board":
            case "b":
                return new ViewBoardCommand(gameSystem.getBoard(), gameSystem.getLocation());
            case "player":
            case "self":
            case "profile":
                return new ViewPersonalProfileCommand(
                        gameSystem.getPlayers(),
                        gameSystem.getProperties(),
                        gameSystem.getLocation()
                );
            case "cheat":
                if (tokens.size() < 2) return new EmptyCommand("Usage:\n\tcheat [-lm/t]");
                switch (tokens.get(1).toLowerCase()) {
                    case "-lm":
                        if (tokens.size() < 3) return new EmptyCommand("Usage:\n\tcheat -lm [money to lose]");
                        LoseMoneyEffect loseMoneyEffect =
                                new LoseMoneyEffect(
                                        "Cheat",
                                        gameSystem.getCurrentPlayer(),
                                        Integer.parseInt(tokens.get(2))
                                );
                        loseMoneyEffect.setEffectObservers(gameSystem.getEffectObservers());
                        return new ReduceMoneyCommand(loseMoneyEffect);
                    case "-t":
                        if (tokens.size() < 3) return new EmptyCommand(
                                "Usage:\n\tcheat -lm [block name to transport]"
                        );
                        // Join tokens
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String token : tokens.subList(2, tokens.size())) {
                            stringBuilder.append(token).append(" ");
                        }
                        stringBuilder.setLength(stringBuilder.length() - 1);

                        // Find Block
                        Block block = gameSystem.getBoard().findBlock(stringBuilder.toString());
                        if (block == null) {
                            return new EmptyCommand("Block not found");
                        }

                        // Create Effect
                        TeleportEffect teleportEffect = new TeleportEffect(
                                "Cheat",
                                gameSystem.getCurrentPlayer(),
                                gameSystem.getLocation(),
                                block,
                                true
                        );
                        teleportEffect.setEffectObservers(gameSystem.getEffectObservers());
                        return new TeleportCommand(teleportEffect, gameSystem);
                }
        }
        return null;
    }

    public GameSystem getGameSystem() {
        return gameSystem;
    }
}
