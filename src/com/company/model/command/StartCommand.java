package com.company.model.command;

import com.company.model.*;
import com.company.model.component.Player;
import com.company.model.component.block.Block;
import com.company.model.component.block.JailBlock;
import com.company.model.component.block.JustVisitingOrInJailBlock;

import java.util.Arrays;
import java.util.List;

public class StartCommand implements Command {

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
        this.playerNumber = GameSystem.DEFAULT_NAMES.length;
        this.names = Arrays.asList(GameSystem.DEFAULT_NAMES);
        this.playerFactory = new PlayerFactory(gameSystem.getRandom(), Player.Status.NORMAL, Player.DEFAULT_AMOUNT);
    }

    @Override
    public void execute() {
        gameSystem.getPlayers().clear();
        GameDisplay.titleBar("GAME START!");
        GameDisplay.print("                  Try \"help\" when you do not know what to type!\n");
        GameDisplay.print(BoardDisplay.VISUAL);
        for (int i = 0; i < playerNumber; i++) {
            gameSystem.getPlayers().add(playerFactory.make(names.get(i)));
        }

        for (Block block : gameSystem.getBoard().getBlocks()) {
            if (!(block instanceof JustVisitingOrInJailBlock)) continue;
            JustVisitingOrInJailBlock justVisitingOrInJailBlock = (JustVisitingOrInJailBlock) block;
            JailBlock jailBlock = justVisitingOrInJailBlock.getJailBlock();
            jailBlock.getRoundCounter().clear();
            for (int i = 0; i < playerNumber; i++) {
                jailBlock.getRoundCounter().put(gameSystem.getPlayers().get(i), 0);
            }
        }
        gameSystem.startGame();
    }

    @Override
    public boolean isValid() {
        if (playerNumber != names.size()) return false;
        return compMonopolyApplication.getStatus() == CompMonopolyApplication.Status.MENU;
    }


}
