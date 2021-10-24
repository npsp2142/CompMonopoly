package com.company.model;

import com.company.model.component.*;
import com.company.model.data.BlockDatum;
import com.company.model.data.LocationDatum;
import com.company.model.data.PlayerDatum;
import com.company.model.data.PropertyDatum;
import com.company.model.effect.BankruptEffect;
import com.company.model.observer.PlayerObserver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class GameSystem {
    private final Board board;
    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final Location location;

    private Player currentPlayer;
    private int round;
    public static GameSystem instance;

    public GameSystem(Board board,
                      ArrayList<Player> players,
                      ArrayList<Property> properties,
                      Location location)
    {
        this.board = board;
        this.players = players;
        this.properties = properties;
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
    public Player getNextPlayer(){
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    public void onGameStart() {
        GameApplication.instance.setStatus(GameApplication.Status.PLAYING);
        location.setStartLocation(board.getStartBlock());
        currentPlayer = players.get(0);
        reload();
        onRoundStart();
    }
    private void onRoundStart(){
        round++;
        GameDisplay.titleBar(String.format("ROUND %d", round));
        onTurnStart();
    }

    private void onTurnStart() {
        if(currentPlayer.getStatus().equals(Player.Status.BANKRUPT)){
            endTurn();
            return;
        }
        GameDisplay.titleBar(String.format("%s TURN",  currentPlayer.getName()));
        GameDisplay.infoMessage(String.format("Amount: %d HKD", currentPlayer.getAmount()));
    }

    public void endTurn() {
        for (Player player:players) {
            player.notifySubscribers();
        }
        onEndTurn();
        currentPlayer = getNextPlayer();
        if( checkEndGame()){
            onGameEnd();
            return;
        }

        if(currentPlayer.equals(players.get(0))){
            onRoundStart();
            return;
        }


        onTurnStart();

    }

    private void onEndTurn(){
        boolean canEndTurn = false;
        if(currentPlayer.getAmount() < 0){
           new BankruptEffect("Bankrupt",currentPlayer, properties).onLand();
        }

        if(NEED_ASK_END_TURN){
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

    private boolean checkEndGame(){
        if(isAllOtherPlayerBankrupt()){
            return true;
        }

        return isGameTooLong();
    }

    private boolean isAllOtherPlayerBankrupt(){
        int count = 0;
        for (Player player:players) {
            if(player.getStatus().equals(Player.Status.BANKRUPT)){
                count += 1;
            }
        }
        return count >= players.size() - 1;
    }

    private boolean isGameTooLong(){
        return round >= MAX_TURN;
    }

    private void onGameEnd(){
        if(isAllOtherPlayerBankrupt()){
            for (Player player:players) {
                if(player.getStatus().equals(Player.Status.HEALTHY)){
                    GameDisplay.titleBar(String.format("%s is the winner",player.getName()));
                    break;
                }
            }
            GameDisplay.titleBar("Game Over!");
            GameApplication.instance.setStatus(GameApplication.Status.MENU);
            return;
        }
        if(isGameTooLong()){
            GameDisplay.titleBar("Game Over!");
            GameDisplay.titleBar("Tie!");
            GameApplication.instance.setStatus(GameApplication.Status.MENU);
        }

    }

    private void reload(){
        for (Player player:
             players) {
            player.reload();
        }
        for (Property property:properties) {
            property.reload();
        }
        round = 0;
        location.reload();
    }



    public void saveGame(){
        try {
            File file = new File(DEFAULT_NAME);
            if(file.createNewFile()){
                System.out.println("File created - "+DEFAULT_NAME);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        ArrayList<PlayerDatum> playerData = new ArrayList<>();
        HashMap<Player,PlayerDatum> playerDatumHashMap = new HashMap<>();
        for (Player player: players) {
            PlayerDatum saveData = new PlayerDatum(player.getName(),player.getStatus(),player.getAmount());
            playerData.add(saveData);
            playerDatumHashMap.put(player,saveData);
        }

        ArrayList<PropertyDatum> propertyData = new ArrayList<>();
        for (Property property:properties) {
            if(property.getOwner()==null){
                continue;
            }
            PropertyDatum propertyDatum = new PropertyDatum(property.getName(),playerDatumHashMap.get(property.getOwner()));
            propertyData.add(propertyDatum);
        }

        HashMap<PlayerDatum, BlockDatum> playerDatumBlockDatumHashMap = new HashMap<>();
        LocationDatum locationDatum = new LocationDatum(playerDatumBlockDatumHashMap);
        for (Player player: players) {
            BlockDatum blockDatum = new BlockDatum(location.getCurrentLocation(player).getName());
            playerDatumBlockDatumHashMap.put(playerDatumHashMap.get(player),blockDatum);
        }
        SaveSystem saveSystem = new SaveSystem(playerData,propertyData,locationDatum, round,playerDatumHashMap.get(currentPlayer));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(DEFAULT_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveSystem);
            objectOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadGame(){
        SaveSystem saveSystem = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(DEFAULT_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            saveSystem = (SaveSystem) objectInputStream.readObject();


        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        if(saveSystem == null){
            return;
        }

        ArrayList<PlayerDatum> playerData = saveSystem.getPlayerData();
        ArrayList<PropertyDatum>  propertyData = saveSystem.getPropertyData();
        LocationDatum locationDatum = saveSystem.getLocationDatum();

        HashMap<PlayerDatum,Player> map = new HashMap<>();
        Random random = new Random(System.currentTimeMillis());
        Dice dice = new Dice(random,4);
        ArrayList<PlayerObserver> observers= new ArrayList<>();
        players.clear();
        // player
        for (PlayerDatum playerDatum : playerData){
            Player player = new Player(playerDatum.getName(),playerDatum.getStatus(),playerDatum.getAmount(),
                    dice,
                    observers);
            players.add(player);
            map.put(playerDatum,player);
        }

        // properties
        for (Property property : properties){
            property.reload();
        }
        for (PropertyDatum propertyDatum:propertyData){
            for (Property property : properties){
                if(property.getName().equals(propertyDatum.getName())){
                    property.setOwner(map.get(propertyDatum.getOwner()));
                }
            }
        }

        // location
        for (PlayerDatum datum: locationDatum.getLocation().keySet()){
            location.setStartLocation(board.getStartBlock());
            location.moveTo(map.get(datum),locationDatum.getLocation().get(datum).getName());
        }

        round = saveSystem.getRound();
        currentPlayer = map.get(saveSystem.getCurrentPlayer());
    }

    public void onGameLoad(){
        GameDisplay.titleBar(String.format("ROUND %d", round));
        onTurnStart();
        GameApplication.instance.setStatus(GameApplication.Status.PLAYING);
    }

    public static final boolean NEED_ASK_END_TURN = false;
    public static final int MAX_TURN = 100;
    public static final String DEFAULT_NAME = "tmp\\save_file.txt";
}
