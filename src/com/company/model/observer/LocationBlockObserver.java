package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.Player;
import com.company.model.block.Block;

import java.util.Map;

public class LocationBlockObserver implements BlockObserver {
    public Map<Player,Block> location;
    public LocationBlockObserver(Map<Player,Block>location){
        this.location =location;
    }

    @Override
    public void update(Block block, Player player) {
        if(!location.containsKey(player)){
            GameDisplay.infoMessage(String.format("%s are at %s",player,block));
            location.put(player,block);
            return;
        }

        if(location.get(player).equals(block)){
            GameDisplay.infoMessage(String.format("%s are at %s",player,block));
            return;
        }
        GameDisplay.infoMessage(String.format("%s move from %s to %s",player,location.get(player),block));
        location.replace(player,block);
    }
}
