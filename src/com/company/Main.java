package com.company;

import com.company.model.*;
import com.company.model.block.*;
import com.company.model.command.CommandFactory;
import com.company.model.component.*;
import com.company.model.observer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main {
    public static GameApplication createGameApplication(){
            Random random = new Random(System.currentTimeMillis());

            ArrayList<Player> players = new ArrayList<>();
            Dice dice = new Dice(random, 4);
            ArrayList<PlayerObserver> playerObservers = new ArrayList<>();
            Player playerA = new Player(
                    "Player A",
                    Player.Status.HEALTHY,
                    Player.DEFAULT_AMOUNT,
                    dice,
                    playerObservers
            );
            Player playerB = new Player(
                    "Player B",
                    Player.Status.HEALTHY,
                    Player.DEFAULT_AMOUNT,
                    dice,
                    playerObservers
            );
            players.add(playerA);
            players.add(playerB);


            ArrayList<BlockObserver> blockObservers = new ArrayList<>();
            // Add all property
            Property central = new Property("Central", 800, 90);
            Property wanChai =  new Property("Wan Chai", 700, 65);
            Property stanley = new Property("Stanley", 600, 60);
            Property shekO = new Property("Shek O", 400, 10);
            Property mongKok = new Property("Mong Kok", 500, 40);
            Property tsingYi =new Property("Tsing Yi", 400, 15);
            Property shatin =new Property("Shatin", 700, 75);
            Property tuenMun =new Property("TuenMun", 700, 75);
            Property taiPo = new Property("Tai Po", 500, 25);
            Property saiKung =new Property("Sai Kung", 400, 10);
            Property yuenLong =new Property("Yuen Long", 400, 25);
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

            GoBlock goBlock =  new GoBlock("Go",blockObservers);
            PropertyBlock centralBlock = new PropertyBlock(central.getName(),blockObservers,central);
            PropertyBlock wanChaiBlock = new PropertyBlock(wanChai.getName(),blockObservers,wanChai);
            PropertyBlock stanleyBlock = new PropertyBlock(stanley.getName(),blockObservers,stanley);
            PropertyBlock shekOBlock = new PropertyBlock(shekO.getName(),blockObservers,shekO);
            PropertyBlock mongKokBlock = new PropertyBlock(mongKok.getName(),blockObservers,mongKok);
            PropertyBlock tsingYiBlock = new PropertyBlock(tsingYi.getName(),blockObservers,tsingYi);
            PropertyBlock shatinBlock = new PropertyBlock(shatin.getName(),blockObservers,shatin);
            PropertyBlock tuenMunBlock = new PropertyBlock(tuenMun.getName(),blockObservers,shatin);
            PropertyBlock taiPoBlock = new PropertyBlock(tuenMun.getName(),blockObservers,taiPo);
            PropertyBlock saiKungBlock = new PropertyBlock(saiKung.getName(),blockObservers,saiKung);
            PropertyBlock yuenLongBlock = new PropertyBlock(yuenLong.getName(),blockObservers,yuenLong);
            PropertyBlock taiOBlock = new PropertyBlock(taiO.getName(),blockObservers,taiO);
            ChanceBlock chanceOneBlock = new ChanceBlock("Chance 1", blockObservers, random);
            ChanceBlock chanceTwoBlock = new ChanceBlock("Chance 2", blockObservers, random);
            ChanceBlock chanceThreeBlock = new ChanceBlock("Chance 3", blockObservers, random);
            NoEffectBlock freeParkingBlock = new NoEffectBlock("Free Parking", blockObservers);
            NoEffectBlock justVisitingBlock = new NoEffectBlock("Just Visiting", blockObservers);
            IncomeTaxBlock incomeTaxBlock = new IncomeTaxBlock("Income Tax",blockObservers);


            Board board = new Board(goBlock);

            Location location = new Location(board,players);

            HashMap<Player,Integer> roundCounter = new HashMap<>();
            roundCounter.put(playerA,0);
            roundCounter.put(playerB,0);
            InJailBlock inJailBlock = new InJailBlock("In Jail",blockObservers, location,roundCounter);

            JustVisitingOrInJailBlock justVisitingOrInJailBlock = new JustVisitingOrInJailBlock(
                    blockObservers,justVisitingBlock,inJailBlock);

            GoToJailBlock goToJailBlock = new GoToJailBlock("Go Jail",blockObservers, location,justVisitingOrInJailBlock);


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

            LocationBlockObserver locationBlockObserver = new LocationBlockObserver(new HashMap<>());
            MoneyObserver moneyObserver = new MoneyObserver(new HashMap<>());
            blockObservers.add(locationBlockObserver);
            playerObservers.add(moneyObserver);

            GameSystem gameSystem = new GameSystem(board,players, properties, location);
            CommandFactory factory = new CommandFactory(gameSystem);
            new GameController(System.in);
            new GameDisplay(System.out);

            return new GameApplication(factory,GameApplication.Status.MENU);
    }
    public static void main(String[] args) {
        Player.NEED_PROMPT = true;
        GameApplication gameApplication = createGameApplication();
        gameApplication.run();
    }
}
