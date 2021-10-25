package com.company.model;

import com.company.model.component.Board;
import com.company.model.component.Location;
import com.company.model.component.Player;
import com.company.model.component.Property;
import com.company.model.data.GameData;
import com.company.model.data.GameDataFactory;
import com.company.model.effect.BankruptEffect;
import com.company.model.observer.EffectObserver;

import java.io.*;
import java.util.ArrayList;


public class GameSystem {
    public static final boolean NEED_ASK_END_TURN = false;
    public static final int MAX_TURN = 100;
    public static final String DEFAULT_NAME = "tmp\\save_file.txt";
    public static GameSystem instance;

    private final Board board;
    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final ArrayList<EffectObserver> effectObservers;
    private final Location location;
    private Player currentPlayer;
    private int round;

    public GameSystem(Board board,
                      ArrayList<Player> players,
                      ArrayList<Property> properties,
                      ArrayList<EffectObserver> effectObservers, Location location) {
        this.board = board;
        this.players = players;
        this.properties = properties;
        this.effectObservers = effectObservers;
        this.location = location;
        round = 0;
        if (instance == null) {
            instance = this;
        }

    }

    public Location getPlayerLocation() {
        return location;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getNextPlayer() {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public void onGameStart() {
        CompMonopolyApplication.instance.setStatus(CompMonopolyApplication.Status.PLAYING);
        location.setStartLocation(board.getStartBlock());
        currentPlayer = players.get(0);
        reload();
        onRoundStart();
    }

    private void onRoundStart() {
        round++;
        GameDisplay.titleBar(String.format("ROUND %d", round));
        onTurnStart();
    }

    private void onTurnStart() {
        if (currentPlayer.getStatus().equals(Player.Status.BANKRUPT)) {
            endTurn();
            return;
        }
        GameDisplay.titleBar(String.format("%s TURN", currentPlayer.getName()));
        GameDisplay.infoMessage(String.format("Amount: %d HKD", currentPlayer.getAmount()));
    }

    public void endTurn() {
        for (Player player : players) {
            player.notifySubscribers();
        }
        onEndTurn();
        currentPlayer = getNextPlayer();
        if (checkEndGame()) {
            onGameEnd();
            return;
        }

        if (currentPlayer.equals(players.get(0))) {
            onRoundStart();
            return;
        }


        onTurnStart();

    }

    private void onEndTurn() {
        boolean canEndTurn = false;
        if (currentPlayer.getAmount() < 0) {
            new BankruptEffect("Bankrupt", effectObservers, currentPlayer, properties).onLand();
        }

        if (NEED_ASK_END_TURN) {
            while (!canEndTurn) {
                Player.Response response = null;
                while (response == null) {
                    response = GameController.instance.getResponse("End Turn? [y]");
                }
                if (response == Player.Response.YES) {
                    canEndTurn = true;
                }
            }
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
        for (Player player : players) {
            if (player.getStatus().equals(Player.Status.BANKRUPT)) {
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
                if (player.getStatus().equals(Player.Status.HEALTHY)) {
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

    private void reload() {
        for (Player player :
                players) {
            player.reload();
        }
        for (Property property : properties) {
            property.reload();
        }
        round = 0;
        location.reload();
    }

    public void saveGame() {
        try {
            File file = new File(DEFAULT_NAME);
            if (file.createNewFile()) {
                System.out.println("File created - " + DEFAULT_NAME);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameDataFactory gameDataFactory = new GameDataFactory(players,properties,location,round,currentPlayer);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(DEFAULT_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameDataFactory.make());
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        GameData gameData = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(DEFAULT_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            gameData = (GameData) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (gameData == null) {
            return;
        }
        GameDataFactory gameDataFactory = new GameDataFactory(players,properties,location,round,currentPlayer);
        gameDataFactory.load(this,gameData);
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<EffectObserver> getEffectObservers() {
        return effectObservers;
    }

    public void onGameLoad() {
        GameDisplay.titleBar(String.format("ROUND %d", round));
        onTurnStart();
        CompMonopolyApplication.instance.setStatus(CompMonopolyApplication.Status.PLAYING);
    }
}
