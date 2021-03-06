package com.company.model.command;

import com.company.model.CompMonopolyApplication;
import com.company.model.GameDisplay;
import com.company.model.component.Player;
import com.company.model.component.PlayerLocation;
import com.company.model.component.Property;

import java.util.ArrayList;

/**
 * The player wants to see all player information, eg The amount money they own, their current location, their status, their properties and the rent
 */
public class ViewPersonalProfileCommand implements Command {
    private final ArrayList<Player> players;
    private final ArrayList<Property> properties;
    private final PlayerLocation playerLocation;


    /**
     * @param players the players in the game
     * @param properties the properties in the game
     * @param playerLocation the current player location
     */
    public ViewPersonalProfileCommand(ArrayList<Player> players, ArrayList<Property> properties, PlayerLocation playerLocation) {
        this.players = players;
        this.properties = properties;
        this.playerLocation = playerLocation;
    }

    @Override
    public void execute() {
        for (Player player : players) {
            GameDisplay.titleBar(player.getName(), '-');
            GameDisplay.infoMessage(String.format("Amount: %s", player.getAmount()));
            GameDisplay.infoMessage(String.format("Current Location: %s", player.getCurrentLocation(playerLocation)));
            GameDisplay.infoMessage(String.format("Status: %s", player.getStatus()));
            GameDisplay.infoMessage("Properties: ");
            for (Property property : properties) {
                if (property.getOwner() == null) {
                    continue;
                }
                if (!property.getOwner().equals(player)) {
                    continue;
                }
                GameDisplay.infoMessage(String.format("%s - Rent: %s", property.getName(), property.getRent()));
            }
        }
    }

    @Override
    public boolean isValid() {
        return CompMonopolyApplication.instance.getStatus() == CompMonopolyApplication.Status.PLAYING;
    }
}
