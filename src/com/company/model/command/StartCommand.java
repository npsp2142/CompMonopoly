package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.GameSystem;
import com.company.model.PlayerFactory;
import com.company.model.component.Player;

import java.util.Arrays;
import java.util.List;

public class StartCommand implements Command {
    public static final String[] DEFAULT_NAMES = {
            "Player A", "Player B", "Player C",
    };
    private final CompMonopolyApplication compMonopolyApplication;
    private final GameSystem gameSystem;
    private final int playerNumber;
    private final List<String> names;
    private final PlayerFactory playerFactory;

    public StartCommand(
            CompMonopolyApplication compMonopolyApplication,
            int playerNumber,
            List<String> names,
            GameSystem gameSystem
    ) {
        this.compMonopolyApplication = compMonopolyApplication;
        this.gameSystem = gameSystem;
        this.playerNumber = playerNumber;
        this.names = names;
        this.playerFactory = new PlayerFactory(gameSystem.getRandom(), Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
    }

    public StartCommand(CompMonopolyApplication compMonopolyApplication, GameSystem gameSystem) {
        this.compMonopolyApplication = compMonopolyApplication;
        this.gameSystem = gameSystem;
        this.playerNumber = DEFAULT_NAMES.length;
        this.names = Arrays.asList(DEFAULT_NAMES);
        this.playerFactory = new PlayerFactory(gameSystem.getRandom(), Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
    }

    @Override
    public void execute() {
        gameSystem.getPlayers().clear();
        GameDisplay.titleBar("GAME START!");
        for (int i = 0; i < playerNumber; i++) {
            gameSystem.getPlayers().add(playerFactory.make(names.get(i)));
        }
        gameSystem.startGame();
    }

    @Override
    public boolean isValid() {
        if (playerNumber != names.size()) return false;
        return compMonopolyApplication.getStatus() == CompMonopolyApplication.Status.MENU;
    }


}
