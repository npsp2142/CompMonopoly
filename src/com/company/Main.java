package com.company;

import com.company.model.*;
import com.company.model.component.block.*;
import com.company.model.command.CommandFactory;
import com.company.model.component.*;
import com.company.model.observer.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main {

    public static CompMonopolyApplication createGameApplication(InputStream inputStream, OutputStream outputStream) {
        Random random = new Random(System.currentTimeMillis());
        ArrayList<String> names = new ArrayList<>();
        names.add("Player A");
        names.add("Player B");
        ArrayList<PlayerObserver> playerObservers = new ArrayList<>();
        PlayerFactory playerFactory = new PlayerFactory(random,playerObservers,Player.Status.HEALTHY,Player.DEFAULT_AMOUNT);
        ArrayList<Player> players = playerFactory.make(names);

        ArrayList<BlockObserver> blockObservers = new ArrayList<>();

        // Add all property
        Property central = new Property("Central", 800, 90);
        Property wanChai = new Property("Wan Chai", 700, 65);
        Property stanley = new Property("Stanley", 600, 60);
        Property shekO = new Property("Shek O", 400, 10);
        Property mongKok = new Property("Mong Kok", 500, 40);
        Property tsingYi = new Property("Tsing Yi", 400, 15);
        Property shatin = new Property("Shatin", 700, 75);
        Property tuenMun = new Property("Tuen Mun", 700, 75);
        Property taiPo = new Property("Tai Po", 500, 25);
        Property saiKung = new Property("Sai Kung", 400, 10);
        Property yuenLong = new Property("Yuen Long", 400, 25);
        Property taiO = new Property("Tai O", 600, 25);

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(central);
        properties.add(wanChai);
        properties.add(stanley);
        properties.add(shekO);
        properties.add(mongKok);
        properties.add(tsingYi);
        properties.add(shatin);
        properties.add(tuenMun);
        properties.add(taiPo);
        properties.add(saiKung);
        properties.add(yuenLong);
        properties.add(taiO);

        ArrayList<EffectObserver> effectObservers = new ArrayList<>();
        effectObservers.add(new EffectDisplay(System.out));

        GoBlock goBlock = new GoBlock("Go", blockObservers, effectObservers);
        PropertyBlock centralBlock = new PropertyBlock(central.getName(), blockObservers,effectObservers,  central);
        PropertyBlock wanChaiBlock = new PropertyBlock(wanChai.getName(), blockObservers, effectObservers, wanChai);
        PropertyBlock stanleyBlock = new PropertyBlock(stanley.getName(), blockObservers, effectObservers, stanley);
        PropertyBlock shekOBlock = new PropertyBlock(shekO.getName(), blockObservers,effectObservers,  shekO);
        PropertyBlock mongKokBlock = new PropertyBlock(mongKok.getName(), blockObservers,effectObservers,  mongKok);
        PropertyBlock tsingYiBlock = new PropertyBlock(tsingYi.getName(), blockObservers,effectObservers,  tsingYi);
        PropertyBlock shatinBlock = new PropertyBlock(shatin.getName(), blockObservers,effectObservers,  shatin);
        PropertyBlock tuenMunBlock = new PropertyBlock(tuenMun.getName(), blockObservers, effectObservers, shatin);
        PropertyBlock taiPoBlock = new PropertyBlock(taiPo.getName(), blockObservers,effectObservers,  taiPo);
        PropertyBlock saiKungBlock = new PropertyBlock(saiKung.getName(), blockObservers,effectObservers,  saiKung);
        PropertyBlock yuenLongBlock = new PropertyBlock(yuenLong.getName(), blockObservers,effectObservers,  yuenLong);
        PropertyBlock taiOBlock = new PropertyBlock(taiO.getName(), blockObservers,effectObservers,  taiO);
        ChanceBlock chanceOneBlock = new ChanceBlock("Chance 1", blockObservers, effectObservers, random);
        ChanceBlock chanceTwoBlock = new ChanceBlock("Chance 2", blockObservers, effectObservers, random);
        ChanceBlock chanceThreeBlock = new ChanceBlock("Chance 3", blockObservers, effectObservers, random);
        NoEffectBlock freeParkingBlock = new NoEffectBlock("Free Parking", blockObservers, effectObservers);
        NoEffectBlock justVisitingBlock = new NoEffectBlock("Just Visiting", blockObservers, effectObservers);
        IncomeTaxBlock incomeTaxBlock = new IncomeTaxBlock("Income Tax", blockObservers, effectObservers);

        Board board = new Board();
        PlayerLocation playerLocation = new PlayerLocation(board, players, goBlock);

        HashMap<Player, Integer> roundCounter = new HashMap<>();
        roundCounter.put(players.get(0), 0);
        roundCounter.put(players.get(1), 0);
        InJailBlock inJailBlock = new InJailBlock("In Jail", blockObservers, effectObservers, playerLocation, roundCounter);

        JustVisitingOrInJailBlock justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(
                blockObservers, effectObservers, justVisitingBlock, inJailBlock);

        GoToJailBlock goToJailBlock = new GoToJailBlock("Go Jail", blockObservers, effectObservers, playerLocation, justVisitingOrInJailBlock);

        board.addBlock(goBlock);
        board.addBlock(centralBlock);
        board.addBlock(wanChaiBlock);
        board.addBlock(stanleyBlock);
        board.addBlock(shekOBlock);
        board.addBlock(mongKokBlock);
        board.addBlock(tsingYiBlock);
        board.addBlock(shatinBlock);
        board.addBlock(tuenMunBlock);
        board.addBlock(taiPoBlock);
        board.addBlock(saiKungBlock);
        board.addBlock(yuenLongBlock);
        board.addBlock(taiOBlock);
        board.addBlock(chanceOneBlock);
        board.addBlock(chanceTwoBlock);
        board.addBlock(chanceThreeBlock);
        board.addBlock(freeParkingBlock);
        board.addBlock(incomeTaxBlock);
        board.addBlock(justVisitingOrInJailBlock);
        board.addBlock(goToJailBlock);


        board.addPath(goBlock, centralBlock);
        board.addPath(centralBlock, wanChaiBlock);
        board.addPath(wanChaiBlock, incomeTaxBlock);
        board.addPath(incomeTaxBlock, stanleyBlock);
        board.addPath(stanleyBlock, justVisitingOrInJailBlock);
        board.addPath(justVisitingOrInJailBlock, shekOBlock);
        board.addPath(shekOBlock, mongKokBlock);
        board.addPath(mongKokBlock, chanceOneBlock);
        board.addPath(chanceOneBlock, tsingYiBlock);
        board.addPath(tsingYiBlock, freeParkingBlock);
        board.addPath(freeParkingBlock, shatinBlock);
        board.addPath(shatinBlock, chanceTwoBlock);
        board.addPath(chanceTwoBlock, tuenMunBlock);
        board.addPath(tuenMunBlock, taiPoBlock);
        board.addPath(taiPoBlock, goToJailBlock);
        board.addPath(goToJailBlock, saiKungBlock);
        board.addPath(saiKungBlock, yuenLongBlock);
        board.addPath(yuenLongBlock, chanceThreeBlock);
        board.addPath(chanceThreeBlock, taiOBlock);
        board.addPath(taiOBlock, goBlock);

        LocationObserver locationObserver = new LocationObserver(new HashMap<>());
        MoneyObserver moneyObserver = new MoneyObserver(new HashMap<>());
        blockObservers.add(locationObserver);
        playerObservers.add(moneyObserver);

        GameSystem gameSystem = new GameSystem(board, players, properties, effectObservers, playerLocation);
        CommandFactory factory = new CommandFactory(gameSystem);
        new GameController(inputStream);
        new GameDisplay(outputStream, gameSystem);

        return new CompMonopolyApplication(factory, CompMonopolyApplication.Status.MENU);
    }

    public static void main(String[] args) {
        Player.NEED_PROMPT = true;
        CompMonopolyApplication compMonopolyApplication = createGameApplication(System.in, System.out);
        compMonopolyApplication.run();
    }
}
