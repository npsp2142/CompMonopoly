package com.company.model;

import com.company.model.component.Board;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;
import com.company.model.component.block.Block;
import com.company.model.effect.AbandonPropertyEffect;
import com.company.model.effect.BankruptEffect;
import com.company.model.observer.*;
import com.company.model.save.GameSave;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class GameSystem {
    public static final int MAX_TURN = 100;
    public static final String DEFAULT_FILE_NAME = "save_file.txt";
    public static final String DEFAULT_FOLDER = "tmp";
    public static final String[] DEFAULT_NAMES = {
            "Player A", "Player B", "Player C",
    };
    private final Board board;
    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final PlayerLocation playerLocation;
    private Player currentPlayer;
    private int round;
    private Random random;

    private Map<Player, Integer> jailRoundCounter;

    private Map<String, EffectObserver> effectObservers;
    private Map<String, BlockObserver> blockObservers;
    private Map<String, PlayerObserver> playerObservers;

    public GameSystem(Board board,
                      ArrayList<Player> players,
                      ArrayList<Property> properties,
                      PlayerLocation playerLocation, Map<Player, Integer> jailRoundCounter) {
        this.board = board;
        this.players = players;
        this.properties = properties;
        this.playerLocation = playerLocation;
        this.jailRoundCounter = jailRoundCounter;
        round = 0;
    }

    public GameSystem(Board board,
                      ArrayList<Player> players,
                      ArrayList<Property> properties,
                      PlayerLocation playerLocation) {
        this.board = board;
        this.players = players;
        this.properties = properties;
        this.playerLocation = playerLocation;
        round = 0;
    }

    public void startGame() {
        CompMonopolyApplication.instance.setStatus(CompMonopolyApplication.Status.PLAYING);
        resetObservers();
        setObservers();
        playerLocation.setStartLocation();
        currentPlayer = players.get(0);
        reset();
        onRoundStart();
    }

    private void setObservers() {
        for (Block block : board.getBlocks()) {
            block.setBlockObservers(blockObservers);
            block.setEffectObservers(effectObservers);
        }
        for (Player player : players) {
            player.setPlayerObservers(playerObservers);
        }
    }

    private void resetObservers() {
        if (blockObservers != null) {
            for (String observerName : blockObservers.keySet()) {
                blockObservers.get(observerName).reset();
            }
        }
        if (playerObservers != null) {
            for (String observerName : playerObservers.keySet()) {
                playerObservers.get(observerName).reset();
            }
        }
    }

    private void onRoundStart() {
        round++;
        GameDisplay.titleBar(String.format("ROUND %d", round));
        onTurnStart();
    }

    private void onTurnStart() {
        if (currentPlayer.getStatus() == Player.Status.BANKRUPT) {
            endTurn();
            return;
        }
        GameDisplay.titleBar(String.format("%s TURN", currentPlayer.getName()));
        GameDisplay.infoMessage(String.format("Amount: %d HKD", currentPlayer.getAmount()));
        GameDisplay.infoMessage(
                String.format("You are at %s",
                        GameDisplay.colorString(currentPlayer.getCurrentLocation(playerLocation).getName(), GameDisplay.ANSI_PURPLE)
                )
        );
    }

    public void endTurn() {
        onEndTurn();
        if (checkEndGame()) { // If game end
            onGameEnd();
            return;
        }
        currentPlayer = getNextPlayer();
        if (currentPlayer.equals(players.get(0))) { // If round end
            onRoundStart();
            return;
        }
        onTurnStart();
    }

    private void onEndTurn() {
        if (currentPlayer.getAmount() < 0) { // If player no money
            ArrayList<AbandonPropertyEffect> abandonPropertyEffects = new ArrayList<>();
            for (Property property : properties) { // Set the properties owned by the bankrupted to null
                if (property.getOwner() == null) {
                    continue;
                }
                if (property.getOwner().equals(currentPlayer)) {
                    AbandonPropertyEffect abandonPropertyEffect = new AbandonPropertyEffect("Bankrupt", property);
                    abandonPropertyEffect.setEffectObservers(getEffectObservers());
                    abandonPropertyEffects.add(abandonPropertyEffect);
                }
            }
            BankruptEffect bankruptEffect = new BankruptEffect("Bankrupt", currentPlayer, abandonPropertyEffects);
            bankruptEffect.setEffectObservers(getEffectObservers());
            bankruptEffect.triggerOnLand();
        }
    }

    private boolean checkEndGame() {
        if (isAllOtherPlayerBankrupt()) {
            return true;
        }

        return isGameTooLong();
    }

    private boolean isAllOtherPlayerBankrupt() {
        int count = 0;
        for (Player player : players) { // Count the number of player bankrupt
            if (player.getStatus() == Player.Status.BANKRUPT) {
                count += 1;
            }
        }
        return count >= players.size() - 1;
    }

    private boolean isGameTooLong() {
        return round >= MAX_TURN;
    }

    private void onGameEnd() {
        if (isAllOtherPlayerBankrupt()) {
            for (Player player : players) {
                if (player.getStatus() == Player.Status.NORMAL) {
                    GameDisplay.titleBar(String.format("%s is the winner", player.getName()));
                    break;
                }
            }
            GameDisplay.titleBar("Game Over!");
            CompMonopolyApplication.instance.setStatus(CompMonopolyApplication.Status.MENU);
            return;
        }
        if (isGameTooLong()) {
            GameDisplay.titleBar("Game Over!");
            GameDisplay.titleBar("Tie!");
            CompMonopolyApplication.instance.setStatus(CompMonopolyApplication.Status.MENU);
        }

    }

    private void reset() {
        for (Player player : players) {
            player.reset();
        }
        for (Property property : properties) {
            property.reset();
        }
        round = 0;
        playerLocation.reload();
    }

    public void saveGame() {
        saveGame(DEFAULT_FILE_NAME);
    }

    public void saveGame(String fileName) {
        try {
            if (new File(DEFAULT_FOLDER).mkdir()) {
                GameDisplay.infoMessage("Directory created - " + DEFAULT_FOLDER);
            }
            File file = new File(DEFAULT_FOLDER + "/" + fileName);
            if (file.createNewFile()) {
                GameDisplay.infoMessage("File created - " + DEFAULT_FOLDER + "/" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<Integer> roundCounterSave = new ArrayList<>();
        for(Player player:players){
            if(!jailRoundCounter.containsKey(player)) continue;
            roundCounterSave.add(jailRoundCounter.get(player));
        }
        GameSaveFactory gameSaveFactory = new GameSaveFactory(
                players, properties, playerLocation, round, currentPlayer, random,roundCounterSave);

        if (blockObservers != null) {
            gameSaveFactory.setBlockVisitObserver((BlockVisitObserver) blockObservers.get(BlockVisitObserver.DEFAULT_NAME));
        }
        if (playerObservers != null) {
            gameSaveFactory.setPathObserver((PathObserver) playerObservers.get(PathObserver.DEFAULT_NAME));
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(DEFAULT_FOLDER + "\\" + fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameSaveFactory.make());
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        loadGame(DEFAULT_FILE_NAME);
    }

    public void loadGame(String fileName) {
        GameSave save = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(DEFAULT_FOLDER + "\\" + fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            save = (GameSave) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (save == null) {
            return;
        }
        GameLoader gameLoader = new GameLoader(this, save);
        gameLoader.load();
        onGameLoad();
    }

    private void onGameLoad() {
        CompMonopolyApplication.instance.setStatus(CompMonopolyApplication.Status.PLAYING);
        GameDisplay.titleBar(String.format("ROUND %d", round));
        setObservers();
        onTurnStart();
    }

    public PlayerLocation getLocation() {
        return playerLocation;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getNextPlayer() {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public Map<String, EffectObserver> getEffectObservers() {
        return effectObservers;
    }

    public Map<Player, Integer> getJailRoundCounter() {
        return jailRoundCounter;
    }

    public void setEffectObservers(Map<String, EffectObserver> effectObservers) {
        this.effectObservers = effectObservers;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public Map<String, PlayerObserver> getPlayerObservers() {
        return playerObservers;
    }

    public void setPlayerObservers(Map<String, PlayerObserver> playerObservers) {
        this.playerObservers = playerObservers;
    }

    public void setJailRoundCounter(Map<Player, Integer> jailRoundCounter) {
        this.jailRoundCounter = jailRoundCounter;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Map<String, BlockObserver> getBlockObservers() {
        return blockObservers;
    }

    public void setBlockObservers(Map<String, BlockObserver> blockObservers) {
        this.blockObservers = blockObservers;
    }
}
